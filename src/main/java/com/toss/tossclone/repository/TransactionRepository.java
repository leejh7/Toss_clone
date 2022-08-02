package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // TODO: transferTime으로 정렬


    List<Transaction> findTransactionBySenderAccount(Account senderAccount);

    List<Transaction> findTransactionByReceiverAccount(Account receiverAccount);

    List<Transaction> findTransactionBySenderAccountOrReceiverAccount(Account senderAccount, Account receiverAccount);

}
