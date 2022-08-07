package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankRepository extends JpaRepository<Bank, Long> {
    @Query("select b.id from Bank b where b.name =:name")
    Long findBankByName(@Param("name") String name);
}
