package com.toss.tossclone.repository;

import com.toss.tossclone.dto.TransactionSearchDto;
import com.toss.tossclone.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepositoryCustom {

    Page<Transaction> getTransactionPage(String targetAccountCode, TransactionSearchDto transactionSearchDto, Pageable pageable);
}
