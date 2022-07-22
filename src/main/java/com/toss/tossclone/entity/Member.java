package com.toss.tossclone.entity;

import com.toss.tossclone.entity.constant.MemberRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNum;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Embedded
    private Address address;

    @Column(name = "transaction_count")
    private int transactionCount;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    public Member(String name, String phoneNum, LocalDateTime birthday, Address address, int transactionCount, MemberRole role) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.birthday = birthday;
        this.address = address;
        this.transactionCount = transactionCount;
        this.role = role;
    }

    /**
     * 한달마다 거래 횟수 초기화
     */
    void initTransactionCount() {
        transactionCount = 0;
    }
}
