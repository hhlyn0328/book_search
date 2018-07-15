package com.kabank.book.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public ModelAndView join() { 
		ModelAndView modelAndView = new ModelAndView();
		Member member = new Member();
		modelAndView.addObject("member", member);
		modelAndView.setViewName("/join");
		return modelAndView;
	}
	
	//회원가입 저장	
	@PostMapping("/join")
	public ModelAndView create(HttpServletRequest request, @ModelAttribute("member") @Valid Member member, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		if(member.getUid() == null || member.getUid() == "") {
    		FieldError uidError = new FieldError("Member", "uid", "아이디는 필수항목입니다.");
    		bindingResult.addError(uidError);
    	} else if(memberRepository.findByUid(member.getUid())!=null) {
    		FieldError uidDupError = new FieldError("Member", "uid", "중복된 아이디입니다.");
    		bindingResult.addError(uidDupError);
    	} 
    	
    	if(member.getUname() == null || member.getUname() == "") {
    		FieldError unameError = new FieldError("Member", "uname", "이름은 필수항목입니다.");
    		bindingResult.addError(unameError);
    	}	
    	
    	if(member.getUpw() == null || member.getUpw() == ""){
    		FieldError upwError = new FieldError("Member", "upw", "비밀번호는 필수항목입니다.");
    		bindingResult.addError(upwError);
    	} 
		
    	if(!bindingResult.hasErrors()) {
    		Role role = new Role();
    		member.setUpw(pwEncoder.encode(member.getUpw()));
    		role.setRoleName("BASIC");
    		member.setRoles(Arrays.asList(role));
    		memberRepository.save(member);
    		modelAndView.setViewName("redirect:/login");
    		return modelAndView;
    	}
		
    	modelAndView.setViewName("/join");
		return modelAndView;
		
	}
	
	@RequestMapping("/login")
	public void login() { 

	}
	
	@RequestMapping("/logout")
	public void logout() { 

	}
}
