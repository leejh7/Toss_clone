package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccountCode(String accountCode);

    @Query("select acc from Account acc join fetch acc.member where acc.accountCode =:accountCode")
    Account findByAccountCodeFetchJoinMember(@Param("accountCode") String accountCode);

    @Query("select acc from Account acc join fetch acc.bank where acc.member.email =:email")
    List<Account> findByMemberEmail(@Param("email") String email);

    @Query("select acc from Account acc join fetch acc.bank where acc.member.email =:email and acc.accountCode <>:accountCode")
    List<Account> findByMemberEmailExceptMe(@Param("email") String email, @Param("accountCode") String accountCode);

    @Query("select acc.name from Account acc where acc.accountCode =:accountCode")
    Optional<String> findAccountNameByAccountCode(@Param("accountCode") String accountCode);

    @Query("select mem.name from Account acc join acc.member mem where acc.accountCode =:accountCode")
    Optional<String> findMemberNameByAccountCode(@Param("accountCode") String accountCode);
}
