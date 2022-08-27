package com.toss.tossclone.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toss.tossclone.dto.TransactionSearchDto;
import com.toss.tossclone.entity.QTransaction;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.vo.QTransactionVo;
import com.toss.tossclone.vo.TransactionVo;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public TransactionRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchTransactionTypeEq(String searchTransactionType, String targetAccountCode) {
        if(StringUtils.equals("all", searchTransactionType) || searchTransactionType == null) {
            return null;
        } else if(StringUtils.equals("deposit", searchTransactionType)) {
            return QTransaction.transaction.receiverAccount.accountCode.eq(targetAccountCode);
        } else if(StringUtils.equals("withdraw", searchTransactionType)) {
            return QTransaction.transaction.senderAccount.accountCode.eq(targetAccountCode);
        }
        return null;
    }

    private BooleanExpression transferTimeAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("1m", searchDateType) || searchDateType == null) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("3m", searchDateType)) {
            dateTime = dateTime.minusMonths(3);
        }

        return QTransaction.transaction.transferTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("receiverName", searchBy)) {
            return QTransaction.transaction.receiverName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("senderName", searchBy)) {
            return QTransaction.transaction.senderName.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<TransactionVo> getTransactionPage(String targetAccountCode, TransactionSearchDto transactionSearchDto, Pageable pageable) {
        QTransaction transaction = QTransaction.transaction;

        QueryResults<TransactionVo> results = queryFactory
                .select(
                        new QTransactionVo(
                                transaction.senderName,
                                transaction.receiverName,
                                transaction.senderAccount.accountCode,
                                transaction.receiverAccount.accountCode,
                                transaction.memo,
                                transaction.transferTime,
                                transaction.transferTime,
                                transaction.amount,
                                transaction.senderAccHisBal,
                                transaction.receiverAccHisBal
                        )
                )
                .from(transaction)
                .where(transferTimeAfter(transactionSearchDto.getSearchDateType()),
                        searchTransactionTypeEq(transactionSearchDto.getSearchTransactionType(), targetAccountCode),
                        searchByLike(transactionSearchDto.getSearchBy(), transactionSearchDto.getSearchQuery()))
                .orderBy(QTransaction.transaction.transferTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<TransactionVo> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }
}
