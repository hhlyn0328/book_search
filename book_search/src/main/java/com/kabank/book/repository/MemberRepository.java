package com.kabank.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabank.book.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Optional<Member> findByUid (String uid);
	
}
