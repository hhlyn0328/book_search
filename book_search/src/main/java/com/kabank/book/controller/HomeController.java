package com.kabank.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/book/search");
		return modelAndView;
	}	

	@RequestMapping("/login")
	public void login() {

	}

	@RequestMapping("/logout")
	public void logout() {

	}
}
