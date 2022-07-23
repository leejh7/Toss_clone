package com.toss.tossclone.entity;

import com.toss.tossclone.entity.constant.AccountRole;
import com.toss.tossclone.exception.NotEnoughMoneyException;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(name = "account_code", nullable = false)
    private String accountCode;

    @Column
    private Long balance;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Builder
    public Account(Member member, Bank bank, String accountCode, Long balance, String password) {
        this.member = member;
        this.bank = bank;
        this.accountCode = accountCode;
        this.balance = balance;
        this.password = password;
    }

    //==비지니스 로직==//
    public void addBalance(Long money) {this.balance += money;}

    public void deductBalance(Long money) {
        Long restBalance = this.balance - money;
        if(restBalance < 0) {
            throw new NotEnoughMoneyException("잔액이 부족합니다");
        }
        this.balance = restBalance;
    }

}
