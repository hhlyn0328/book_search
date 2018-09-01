package com.kabank.book.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabank.book.model.Member;
import com.kabank.book.repository.MemberRepository;

@Service("memberService")
public class MemberServiceImpl {
	
	@Autowired
	MemberRepository memberRepository;
	
	public Optional<Member> findByUid (String uid){
		return memberRepository.findByUid(uid);
	}
}
