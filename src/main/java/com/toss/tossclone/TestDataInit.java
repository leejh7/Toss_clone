//package com.toss.tossclone;
//
//import com.toss.tossclone.dto.AccountFormDto;
//import com.toss.tossclone.dto.MemberFormDto;
//import com.toss.tossclone.entity.Member;
//import com.toss.tossclone.repository.BankRepository;
//import com.toss.tossclone.service.AccountService;
//import com.toss.tossclone.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataInit {
//
//    private final MemberService memberService;
//    private final AccountService accountService;
//    private final BankRepository bankRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void init() {
//        createTestMember();
//        createTestAccount();
//    }
//
//    private void createTestMember() {
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setName("테스터1");
//        memberFormDto.setPhoneNum("01012345678");
//        memberFormDto.setBirthday(LocalDate.now());
//        memberFormDto.setCity("서울");
//        memberFormDto.setStreet("양천구");
//        memberFormDto.setZipcode("123-123");
//        memberFormDto.setEmail("test1@email.com");
//        memberFormDto.setPassword("12345678");
//        Member member = Member.createMember(memberFormDto, passwordEncoder);
//        memberService.saveMember(member);
//    }
//
//    private void createTestAccount() {
//        AccountFormDto accountFormDto1 = new AccountFormDto();
//        accountFormDto1.setName("국민 입출금 계좌");
//        accountFormDto1.setBankId(bankRepository.findBankByName("국민"));
//        accountFormDto1.setPassword("1234");
//        accountFormDto1.setBalance(10000L);
//
//
//        AccountFormDto accountFormDto2 = new AccountFormDto();
//        accountFormDto2.setName("우리 입출금 계좌");
//        accountFormDto2.setBankId(bankRepository.findBankByName("우리"));
//        accountFormDto2.setPassword("1234");
//        accountFormDto2.setBalance(20000L);
//
//
//        AccountFormDto accountFormDto3 = new AccountFormDto();
//        accountFormDto3.setName("카카오 입출금 계좌");
//        accountFormDto3.setBankId(bankRepository.findBankByName("카카오"));
//        accountFormDto3.setPassword("1234");
//        accountFormDto3.setBalance(30000L);
//
//        accountService.saveAccount(accountFormDto1, "test1@email.com");
//        accountService.saveAccount(accountFormDto2, "test1@email.com");
//        accountService.saveAccount(accountFormDto3, "test1@email.com");
//    }
//}
