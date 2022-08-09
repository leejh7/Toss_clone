package com.toss.tossclone.service;

import com.toss.tossclone.dto.ReceiverAccountDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;

    public List<ReceiverAccountDto> findMyAccounts(String email, String accountCode) {
        List<Account> myAccounts = accountRepository.findByMemberEmailExceptMe(email, accountCode);

        List<ReceiverAccountDto> result = new ArrayList<>();
        for (Account myAccount : myAccounts) {
            ReceiverAccountDto receiverAccountDto = new ReceiverAccountDto();
            receiverAccountDto.setReceiverAccountName(myAccount.getName());
            receiverAccountDto.setReceiverAccountCode(myAccount.getAccountCode());
            receiverAccountDto.setBankName(myAccount.getBank().getName());
            result.add(receiverAccountDto);
        }

        return result;
    }
}
