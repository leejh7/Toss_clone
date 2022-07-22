package com.toss.tossclone.entity;

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

    @Builder
    public Account(Member member, Bank bank, String accountCode, Long balance, String password) {
        this.member = member;
        this.bank = bank;
        this.accountCode = accountCode;
        this.balance = balance;
        this.password = password;
    }


}
