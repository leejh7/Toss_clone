package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // TODO: 페이징 적용하기 / 필요한 DAO 작성

    List<Transaction> findTransactionBySenderAccountOrderByTransferTimeDesc(Account senderAccount);

    // join 사용
    @Query(value = "select t from Transaction t inner join t.senderAccount sa where sa.accountCode =:accountCode order by t.transferTime desc ")
    List<Transaction> findTransactionBySenderAccountCodeOrderByTransferTimeDesc(@Param("accountCode") String accountCode);

    List<Transaction> findTransactionByReceiverAccountOrderByTransferTimeDesc(Account receiverAccount);

    // join 사용
    @Query(value = "select t from Transaction t inner join t.receiverAccount ra where ra.accountCode =:accountCode order by t.transferTime desc ")
    List<Transaction> findTransactionByReceiverAccountCodeOrderByTransferTimeDesc(@Param("accountCode") String accountCode);

    List<Transaction> findTransactionBySenderAccountOrReceiverAccountOrderByTransferTimeDesc(Account senderAccount, Account receiverAccount);

    // join 사용
    @Query(value = "select t from Transaction t inner join t.senderAccount sa inner join t.receiverAccount ra where sa.accountCode =:accountCode or ra.accountCode =: accountCode order by t.transferTime desc ")
    List<Transaction> findTransactionByAccountCodeOrderByTransferTimeDesc(@Param("accountCode") String accountCode);

    @Query(value = "select sum(t.amount) from Transaction t where t.senderAccount =:senderAccount group by day(t.transferTime)")
    List<Long> findDailyTransaction(@Param("senderAccount") Account senderAccount);

}
