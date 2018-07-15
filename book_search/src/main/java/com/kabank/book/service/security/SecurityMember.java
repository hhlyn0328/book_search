package com.kabank.book.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.kabank.book.model.Member;
import com.kabank.book.model.Role;

import lombok.Getter;
import lombok.Setter;

//spring security 사용을 위한 userdetail

@Getter
@Setter
public class SecurityMember extends User{
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	public SecurityMember(Member member) {
		super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
	}
		
	private static List<GrantedAuthority> makeGrantedAuthority(List<Role> roles){
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
		return list;
	}
	
}
