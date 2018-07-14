package com.kabank.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabank.book.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	public Member findByUid (String uid);
	
}
