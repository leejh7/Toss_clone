package com.toss.tossclone.entity;

import com.toss.tossclone.dto.AccountFormDto;
import com.toss.tossclone.entity.constant.AccountRole;
import com.toss.tossclone.exception.NotEnoughMoneyException;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {
    // TODO: Account를 상속 받는 입출금 계좌, 적금 계좌 등등 만들어보기

    @Id @GeneratedValue
    @Column(name = "account_key")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_key")
    private Bank bank;

    @Column(name = "account_code", nullable = false, unique = true)
    private String accountCode;

    @Column
    private Long balance;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Builder
    private Account(String name, Member member, Bank bank, String accountCode, Long balance, String password) {
        this.name = name;
        this.member = member;
        this.bank = bank;
        this.accountCode = accountCode;
        this.balance = balance;
        this.password = password;
    }

    public static Account createAccount(AccountFormDto accountFormDto, String accountCode, Member member, Bank bank, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(accountFormDto.getPassword());
        Account account = Account.builder()
                .name(accountFormDto.getName())
                .accountCode(accountCode)
                .member(member)
                .bank(bank)
                .balance(accountFormDto.getBalance())
                .password(password)
                .build();

        return account;
    }

    //==비지니스 로직==//
    public void addBalance(Long money) {this.balance += money;}

    public Long deductBalance(Long money) {
        Long restBalance = this.balance - money;
        if(restBalance < 0) {
            throw new NotEnoughMoneyException("잔액이 부족합니다");
        }

        return this.balance = restBalance;
    }

}
