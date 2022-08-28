package com.toss.tossclone.repository;

import com.toss.tossclone.dao.RecentTransactionAccountDao;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryCustom {
    // TODO: / 필요한 DAO 작성

//    // 출금 내역 조회
//    @Query(value = "select t from Transaction t inner join t.senderAccount sa " +
//            "where sa.accountCode =:accountCode order by t.transferTime desc ")
//    List<Transaction> findTransactionBySenderAccountCodeOrderByTransferTimeDesc(@Param("accountCode") String accountCode);
//
//    // 입금 받은 내역 조회
//    @Query(value = "select t from Transaction t inner join t.receiverAccount ra " +
//            "where ra.accountCode =:accountCode order by t.transferTime desc ")
//    List<Transaction> findTransactionByReceiverAccountCodeOrderByTransferTimeDesc(@Param("accountCode") String accountCode);
//
//    // 입금, 출금 내역 모두 조회
//    @Query(value = "select t from Transaction t inner join fetch t.senderAccount " +
//            "inner join fetch t.receiverAccount " +
//            "where t.senderAccount.accountCode =:accountCode or t.receiverAccount.accountCode =: accountCode " +
//            "order by t.transferTime desc ")
//    List<Transaction> findTransactionByAccountCodeOrderByTransferTimeDesc(@Param("accountCode") String accountCode);

    // 최근 보낸 계좌 조회
    // TODO: 페이징 적용하기 + 프론트단 ajax로 페이징 받아오기
    @Query(value = "select new com.toss.tossclone.dao.RecentTransactionAccountDao(b.name, t.receiverName, acc.accountCode, acc.name) " +
            "from Transaction t " +
            "join t.receiverAccount acc " +
            "join t.receiverAccount.bank b  " +
            "where t.senderAccount.accountCode =:accountCode " +
            "group by acc, t.receiverName " +
            "order by max (t.transferTime) desc")
    List<RecentTransactionAccountDao> findRecentTransactionReceiverAccountByAccountCode(@Param("accountCode") String accountCode);

    // 일별 소비 총 금액 쿼리
    @Query(value = "select sum(t.amount) from Transaction t where t.senderAccount =:senderAccount group by day(t.transferTime)")
    List<Long> findDailyTransaction(@Param("senderAccount") Account senderAccount);

}
