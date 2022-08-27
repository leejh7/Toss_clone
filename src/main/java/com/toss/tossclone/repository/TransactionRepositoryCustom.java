package com.toss.tossclone.repository;

import com.toss.tossclone.dto.TransactionSearchDto;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.vo.TransactionVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepositoryCustom {

    Page<TransactionVo> getTransactionPage(String targetAccountCode, TransactionSearchDto transactionSearchDto, Pageable pageable);
}
