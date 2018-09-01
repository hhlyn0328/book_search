package com.kabank.book.service;

import java.util.Optional;

import com.kabank.book.model.Member;

public interface MemberService {
	Optional<Member> findByUid (String uid);
}
