package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByName(String name);
}
