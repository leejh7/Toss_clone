package com.toss.tossclone.service;

import com.toss.tossclone.dao.RecentTransactionAccountDao;
import com.toss.tossclone.dto.ReceiverAccountDto;
import com.toss.tossclone.dto.TransactionFormDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.exception.NotEnoughMoneyException;
import com.toss.tossclone.repository.AccountRepository;
import com.toss.tossclone.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public void saveTransaction(TransactionFormDto transactionFormDto) throws NotEnoughMoneyException {
        Account senderAccount = accountRepository.findByAccountCodeFetchJoinMember(transactionFormDto.getSenderAccountCode());
        Account receiverAccount = accountRepository.findByAccountCodeFetchJoinMember(transactionFormDto.getReceiverAccountCode());

        Transaction transaction = Transaction.createTransaction(
                transactionFormDto.getSenderName(), transactionFormDto.getReceiverName(),
                senderAccount, receiverAccount,
                transactionFormDto.getAmount(), LocalDateTime.now(), ""
        );

        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<ReceiverAccountDto> findRecentTransactionAccounts(String email,String accountCode) {
        List<Account> myAccounts = accountRepository.findByMemberEmail(email);

        List<RecentTransactionAccountDao> recentAccounts = transactionRepository.findRecentTransactionReceiverAccountByAccountCode(accountCode);

        List<ReceiverAccountDto> result = new ArrayList<>();
        for (RecentTransactionAccountDao recentAccount : recentAccounts) {
            // TODO: RecentTransactionAccountDao -> ReceiverAccountDto 맵퍼 메서드 만들어주기
            // TODO: ReceiverAccountDto에 receiverName 필드 만들고 난 후 setter 해주기
            ReceiverAccountDto receiverAccountDto = new ReceiverAccountDto();
            receiverAccountDto.setReceiverAccountName(recentAccount.getReceiverAccountName());
            receiverAccountDto.setReceiverAccountCode(recentAccount.getReceiverAccountCode());
            receiverAccountDto.setBankName(recentAccount.getBankName());
            receiverAccountDto.setMine(isMine(myAccounts, receiverAccountDto.getReceiverAccountCode()));

            result.add(receiverAccountDto);
        }

        return result;
    }

    private boolean isMine(List<Account> myAccounts, String receiverAccountCode) {
        return myAccounts.stream().anyMatch(acc -> acc.getAccountCode().equals(receiverAccountCode));
    }

}
