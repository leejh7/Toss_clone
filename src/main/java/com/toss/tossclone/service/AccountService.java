package com.toss.tossclone.service;

import com.toss.tossclone.dto.AccountFormDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Bank;
import com.toss.tossclone.entity.Member;
import com.toss.tossclone.repository.AccountRepository;
import com.toss.tossclone.repository.BankRepository;
import com.toss.tossclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final BankRepository bankRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;


    public void saveAccount(AccountFormDto accountFormDto, String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        Bank bank = bankRepository.findById(accountFormDto.getBankId()).orElseThrow(EntityNotFoundException::new);

        Account account = Account.createAccount(accountFormDto, member, bank, passwordEncoder);
        accountRepository.save(account);
    }
}
