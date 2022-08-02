package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccountCode(String accountCode);
}
