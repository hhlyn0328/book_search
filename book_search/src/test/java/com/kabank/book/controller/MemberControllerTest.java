package com.kabank.book.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kabank.book.model.Member;
import com.kabank.book.repository.MemberRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MemberControllerTest {
	
	
	@Mock
	MemberRepository memberRepository;

	@Mock
	PasswordEncoder pwEncoder;
	
	@InjectMocks
	private MemberController sut;
	

	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(sut).alwaysDo(print()).build();
	}
	
	
	@Test
	public void join() throws Exception {
		mockMvc.perform(get("/member/join"))
			.andExpect(status().isOk())
			.andExpect(view().name("member/join"));
	}
	
	@Test
	public void joinSave() throws Exception {
		given(memberRepository.findByUid(anyString())).willReturn(Optional.ofNullable(null));
		mockMvc.perform(post("/member/join")
			.flashAttr("member", new Member(1L,"user1","pwd1","name1",null,null,null)))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void joinSaveDuplicationId() throws Exception {
		given(memberRepository.findByUid(anyString())).willReturn(Optional.ofNullable(new Member()));
		mockMvc.perform(post("/member/join")
			.flashAttr("member", new Member(1L,"user1","pwd1","name1",null,null,null)))
			.andExpect(status().isOk())
			.andExpect(view().name("member/join"))
			.andExpect(model().attribute("msg", "중복된 아이디입니다"));
	}
	

	@Test
	public void joinSaveWithoutUid() throws Exception {
		given(memberRepository.findByUid(anyString())).willReturn(Optional.ofNullable(new Member()));
		mockMvc.perform(post("/member/join")
			.flashAttr("member", new Member(1L,null,"pwd1","name1",null,null,null)))
			.andExpect(status().isOk())
			.andExpect(view().name("member/join"));
	}
	

	@Test
	public void joinSaveWithoutUpw() throws Exception {
		given(memberRepository.findByUid(anyString())).willReturn(Optional.ofNullable(new Member()));
		mockMvc.perform(post("/member/join")
			.flashAttr("member", new Member(1L,"user1",null,"name1",null,null,null)))
			.andExpect(status().isOk())
			.andExpect(view().name("member/join"));
	}
	

	@Test
	public void joinSaveWithoutUname() throws Exception {
		given(memberRepository.findByUid(anyString())).willReturn(Optional.ofNullable(new Member()));
		mockMvc.perform(post("/member/join")
			.flashAttr("member", new Member(1L,"user1","pwd1",null,null,null,null)))
			.andExpect(status().isOk())
			.andExpect(view().name("member/join"));
	}
	
}
