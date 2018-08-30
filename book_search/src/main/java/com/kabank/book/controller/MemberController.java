package com.kabank.book.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kabank.book.model.Member;
import com.kabank.book.model.Role;
import com.kabank.book.repository.MemberRepository;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PasswordEncoder pwEncoder;

	@GetMapping("/join")
	public ModelAndView join() {
		ModelAndView modelAndView = new ModelAndView();
		Member member = new Member();
		modelAndView.addObject("member", member);
		modelAndView.setViewName("member/join");
		return modelAndView;
	}

	// 회원가입 저장
	@PostMapping("/join")
	public ModelAndView create(HttpServletRequest request, 
			@ModelAttribute("member") @Valid Member member,
			BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("member/join");
			return modelAndView;
		}
		
		if (memberRepository.findByUid(member.getUid()).isPresent()) {
			modelAndView.addObject("msg", "중복된 아이디입니다");
			modelAndView.setViewName("member/join");
			return modelAndView;
		}

		Role role = new Role();
		role.setRoleName("BASIC");
		member.setUpw(pwEncoder.encode(member.getUpw()));
		member.setRole(role);
		memberRepository.save(member);
		modelAndView.setViewName("redirect:/login");
		return modelAndView;

	}

}
