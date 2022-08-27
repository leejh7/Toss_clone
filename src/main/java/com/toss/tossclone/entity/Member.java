package com.toss.tossclone.entity;

import com.toss.tossclone.dto.MemberFormDto;
import com.toss.tossclone.entity.constant.MemberRole;
import com.toss.tossclone.exception.NotEnoughTransactionCountException;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;


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

    private LocalDate birthday;

    @Embedded
    private Address address;

    @Column(name = "transaction_count")
    private int transactionCount;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    private Member(String name, String phoneNum, LocalDate birthday,
                   Address address, int transactionCount,
                   String email, String password, MemberRole role) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.birthday = birthday;
        this.address = address;
        this.transactionCount = transactionCount;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //==생성 메서드==//
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        Address address = new Address(memberFormDto.getCity(), memberFormDto.getStreet(), memberFormDto.getZipcode());
        Member member = Member.builder()
                .name(memberFormDto.getName())
                .phoneNum(memberFormDto.getPhoneNum())
                .birthday(memberFormDto.getBirthday())
                .address(address)
                .transactionCount(0)
                .email(memberFormDto.getEmail())
                .password(password)
                .role(MemberRole.USER)
                .build();
        return member;
    }

    //==비지니스 로직==//
    void initTransactionCount() {
        this.transactionCount = 0;
    }

    void countingTransactionCount() {
        transactionCount++;
        if(transactionCount >= 9) {
            throw new NotEnoughTransactionCountException("수수료 무료 거래 횟수가 끝났습니다.");
        }
    }
}
