package com.toss.tossclone.service;

import com.toss.tossclone.dto.AccountFormDto;
import com.toss.tossclone.dto.ReceiverAccountDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Bank;
import com.toss.tossclone.entity.Member;
import com.toss.tossclone.repository.AccountRepository;
import com.toss.tossclone.repository.BankRepository;
import com.toss.tossclone.repository.MemberRepository;
import com.toss.tossclone.vo.AccountVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        String accountCode = bank.getPrefixAccountCode() + generateUniqueId();
        validateDuplicateAccountCode(accountCode);

        Account account = Account.createAccount(accountFormDto, accountCode, member, bank, passwordEncoder);
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> findMyAccounts(String memberEmail) {
        return accountRepository.findByMemberEmail(memberEmail);
    }

    @Transactional(readOnly = true)
    public List<ReceiverAccountDto> findMyAccountsExceptMe(String email, String accountCode) {
        List<Account> myAccountsExceptMe = accountRepository.findByMemberEmailExceptMe(email, accountCode);

        List<ReceiverAccountDto> result = new ArrayList<>();
        for (Account myAccount : myAccountsExceptMe) {
            // TODO: Account -> ReceiverAccountDto 맵퍼 메서드 만들어주기
            ReceiverAccountDto receiverAccountDto = new ReceiverAccountDto();
            receiverAccountDto.setReceiverAccountName(myAccount.getName());
            receiverAccountDto.setReceiverAccountCode(myAccount.getAccountCode());
            receiverAccountDto.setReceiverName("나");
            receiverAccountDto.setBankName(myAccount.getBank().getName());
            receiverAccountDto.setMine(true);
            result.add(receiverAccountDto);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public AccountVo findMyAccount(String accountCode) {
        Account account = accountRepository.findFetchJoinByAccountCode(accountCode);

        AccountVo accountVo = new AccountVo();
        accountVo.setAccountName(account.getName());
        accountVo.setAccountCode(account.getAccountCode());
        accountVo.setBankName(account.getBank().getName());
        accountVo.setBalance(account.getBalance());

        return accountVo;
    }

    private void validateDuplicateAccountCode(String accountCode) {
        Account findAccounts = accountRepository.findAccountByAccountCode(accountCode);
        if(findAccounts != null) {
            throw new IllegalStateException("이미 존재하는 계좌번호입니다.");
        }
    }

    private String generateUniqueId() {
        String uid = UUID.randomUUID().toString();
        uid = uid.replaceAll("-","");
        BigInteger bigInt = new BigInteger(uid, 16);
        return bigInt.toString().substring(0, 10);
    }
}
