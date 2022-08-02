package com.toss.tossclone.entity;

import com.toss.tossclone.entity.constant.MemberRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "member_key")
    private Long id;

    @Column(nullable = false)
    private String name;

    // TODO: 핸드폰 번호는 String type이 아닌 Number type으로 만들어주기
    @Column(name = "member_phone")
    private String phoneNum;

    @Temporal(TemporalType.DATE)
//    @Column(nullable = false)
    private Date birthday;

    @Embedded
    private Address address;

    @Column(name = "transaction_count")
    private int transactionCount;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    private Member(String name, String phoneNum, Date birthday, Address address, int transactionCount, MemberRole role) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.birthday = birthday;
        this.address = address;
        this.transactionCount = transactionCount;
        this.role = role;
    }


    //==비지니스 로직==//
    void initTransactionCount() {
        transactionCount = 0;
    }
}
