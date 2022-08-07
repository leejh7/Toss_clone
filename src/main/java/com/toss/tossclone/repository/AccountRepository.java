package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccountCode(String accountCode);

    @Query("select acc from Account acc where acc.member.email =:email")
    List<Account> findByMemberEmail(@Param("email") String email);
}
