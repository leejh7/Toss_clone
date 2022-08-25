package com.toss.tossclone.service;

import com.toss.tossclone.dao.RecentTransactionAccountDao;
import com.toss.tossclone.dto.ReceiverAccountDto;
import com.toss.tossclone.dto.TransactionFormDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.exception.NotEnoughMoneyException;
import com.toss.tossclone.repository.AccountRepository;
import com.toss.tossclone.repository.TransactionRepository;
import com.toss.tossclone.vo.TransactionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
                transactionFormDto.getAmount(), LocalDateTime.now(), transactionFormDto.getMemo()
        );

        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<ReceiverAccountDto> findRecentTransactionAccounts(String email, String accountCode) {
        List<Account> myAccounts = accountRepository.findByMemberEmail(email);

        List<RecentTransactionAccountDao> recentAccounts = transactionRepository.findRecentTransactionReceiverAccountByAccountCode(accountCode);

        List<ReceiverAccountDto> result = new ArrayList<>();
        for (RecentTransactionAccountDao recentAccount : recentAccounts) {
            // TODO: RecentTransactionAccountDao -> ReceiverAccountDto 맵퍼 메서드 만들어주기

            ReceiverAccountDto receiverAccountDto = new ReceiverAccountDto();
            receiverAccountDto.setReceiverAccountName(recentAccount.getReceiverAccountName());
            receiverAccountDto.setReceiverAccountCode(recentAccount.getReceiverAccountCode());
            receiverAccountDto.setBankName(recentAccount.getBankName());

            // isMine: 거래한 계좌가 내 계좌인지 알아내는 메서드
            boolean isMine = isMine(myAccounts, receiverAccountDto.getReceiverAccountCode());
            receiverAccountDto.setMine(isMine);

            // getReceiverName: 받을 사람의 이름을 알아내는 메서드
            receiverAccountDto.setReceiverName(getReceiverName(isMine, recentAccount.getReceiverAccountCode()));
            result.add(receiverAccountDto);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<TransactionVo> findAllHistoryByAccountCode(String accountCode) {
        List<Transaction> transactions = transactionRepository.findTransactionByAccountCodeOrderByTransferTimeDesc(accountCode);

        List<TransactionVo> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionVo transactionVo = new TransactionVo();
            transactionVo.setSenderName(transaction.getSenderName());
            transactionVo.setReceiverName(transaction.getReceiverName());
            transactionVo.setSenderAccountCode(transaction.getSenderAccount().getAccountCode());
            transactionVo.setReceiverAccountCode(transaction.getReceiverAccount().getAccountCode());
            transactionVo.setTransferTime(transaction.getTransferTime());
            transactionVo.setTransferDate(transaction.getTransferTime());
            transactionVo.setAmount(transaction.getAmount());
            transactionVo.setSenderAccHisBal(transaction.getSenderAccHisBal());
            transactionVo.setReceiverAccHisBal(transaction.getReceiverAccHisBal());

            transactionVo.setMemo(transaction.getMemo());
            result.add(transactionVo);
        }
        return result;
    }

    private boolean isMine(List<Account> myAccounts, String receiverAccountCode) {
        return myAccounts.stream().anyMatch(acc -> acc.getAccountCode().equals(receiverAccountCode));
    }

    private String getReceiverName(boolean isMine, String accountCode) {
        if(isMine) return "나";
        else return accountRepository.findMemberNameByAccountCode(accountCode).orElseThrow(EntityNotFoundException::new);
    }

}
