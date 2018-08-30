package com.kabank.book.service.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kabank.book.model.Member;
import com.kabank.book.repository.MemberRepository;

@Service
public class SecurityMemberService implements UserDetailsService{
			
	@Autowired
	MemberRepository memberRepository;
	
	Logger log = LoggerFactory.getLogger("SecurityMemberService");
	
	@Override
	public UserDetails loadUserByUsername (String uid) throws UsernameNotFoundException{
		return memberRepository.findByUid(uid)
				.filter(m -> m!= null)
				.map(m -> new SecurityMember(m)).get();
	}
	

	public Member getLoginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<Member> member = memberRepository.findByUid(auth.getName());
		
		if(member.isPresent()) {
			return member.get();
			
		}else {
			log.error("로그인한 사용자 조회 실패");
			return null;
		}
		
	}
	
}
