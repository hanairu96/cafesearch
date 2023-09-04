package com.toy.cafesearch.repository;

import com.toy.cafesearch.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
