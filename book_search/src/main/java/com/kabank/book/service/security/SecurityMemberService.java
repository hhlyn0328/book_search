package com.kabank.book.service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kabank.book.repository.MemberRepository;

@Service
public class SecurityMemberService implements UserDetailsService{
	
	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername (String uid) throws UsernameNotFoundException{
		return Optional.ofNullable(memberRepository.findByUid(uid))
				.filter(m -> m!= null)
				.map(m -> new SecurityMember(m)).get();
	}
	
	
}
