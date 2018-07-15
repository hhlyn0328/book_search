package com.kabank.book.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kabank.book.model.Member;
import com.kabank.book.model.Role;
import com.kabank.book.repository.MemberRepository;

@Controller
public class MemberController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder pwEncoder;
	
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/book/search");
		return view;
	}
	@GetMapping("/join")
	public void join() { 

	}
	
	//회원가입 저장	
	@PostMapping("/join")
	public String create(@ModelAttribute("member") Member member) {
		Role role = new Role();
		member.setUpw(pwEncoder.encode(member.getUpw()));
		role.setRoleName("BASIC");
		member.setRoles(Arrays.asList(role));
		memberRepository.save(member);
		return "redirect:/login";
	}
	
	@RequestMapping("/login")
	public void login() { 

	}
	
	@RequestMapping("/logout")
	public void logout() { 

	}
}
