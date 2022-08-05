package com.toss.tossclone.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bank extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "bank_key")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "prefix_account_code")
    private String prefixAccountCode;

    @Builder
    public Bank(String name, Double interestRate, String prefixAccountCode) {
        this.name = name;
        this.interestRate = interestRate;
        this.prefixAccountCode = prefixAccountCode;
    }

    // TODO: 은행 별 icon 이미지 추가 해줄것
}
