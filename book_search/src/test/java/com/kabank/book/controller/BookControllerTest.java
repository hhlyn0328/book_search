package com.kabank.book.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kabank.book.model.BookMark;
import com.kabank.book.model.History;
import com.kabank.book.model.Member;
import com.kabank.book.repository.BookMarkRepository;
import com.kabank.book.repository.HistoryRepository;
import com.kabank.book.service.BookService;
import com.kabank.book.service.security.SecurityMemberService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BookControllerTest {
	
	
	@Mock
	private BookService bookService;
	
	@Mock
	private SecurityMemberService securityMemberService;
		
	@Mock
	private HistoryRepository historyRepository;
	
	@Mock
	private BookMarkRepository bookMarkRepository;
	
	@InjectMocks
	private BookController sut;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(sut).alwaysDo(print()).build();
	}
	
	@Test
	public void search() throws Exception {
		mockMvc.perform(get("/book/search"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/search"));
	}
	
	@Test
	public void history() throws Exception {
		
		given(securityMemberService.getLoginUser()).willReturn(getMember());
		given(historyRepository.findByMemberId(anyLong(), any(Pageable.class))).willReturn(new PageImpl<>(getHistories()));		
		mockMvc.perform(get("/book/history"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/history"));
	}
	
	@Test
	public void historyMemberIsNotExist() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(null);		
		mockMvc.perform(get("/book/history"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void insertHistory() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());		
		mockMvc.perform(post("/book/history")
		.flashAttr("history", new History(1L,"title1","publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Success"));
	}
	
	@Test
	public void insertHistoryMemberIsNotExist() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(null);		
		mockMvc.perform(post("/book/history")
				.flashAttr("history", new History(1L,"title1","publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	@Test
	public void insertHistoryHasErrorWithOutTitle() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());		
		mockMvc.perform(post("/book/history")
		.flashAttr("history", new History(1L,null,"publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	@Test
	public void insertHistoryNotSendHistory() throws Exception {		
		mockMvc.perform(post("/book/history"))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	@Test
	public void bookMark() throws Exception {
		
		given(securityMemberService.getLoginUser()).willReturn(getMember());
		given(bookMarkRepository.findByMemberId(anyLong(), any(Pageable.class))).willReturn(new PageImpl<>(getBookMarks()));		
		mockMvc.perform(get("/book/bookmark"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/bookmark"));
	}
	
	@Test
	public void bookMarkMemberIsNotExist() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(null);		
		mockMvc.perform(get("/book/bookmark"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void insertBookMark() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());
		given(bookMarkRepository.countByMemberIdAndIsbn(anyLong(), anyString())).willReturn(0L);
		mockMvc.perform(post("/book/bookmark")
		.flashAttr("bookMark", new BookMark(1L,"isbn1","title1","publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Success"));
	}
	
	@Test
	public void insertBookMarkMemberIsNotExist() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(null);		
		mockMvc.perform(post("/book/bookmark")
		.flashAttr("bookMark", new BookMark(1L,"isbn1","title1","publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	@Test
	public void insertBookMarkHasErrorWithOutIsbn() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());		
		mockMvc.perform(post("/book/bookmark")
		.flashAttr("bookMark", new BookMark(1L,null,"title1","publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	@Test
	public void insertBookMarkHasErrorWithOutTitle() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());		
		mockMvc.perform(post("/book/bookmark")
		.flashAttr("bookMark", new BookMark(1L,"isbn1",null,"publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}

	@Test
	public void insertBookMarkDuplicate() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());
		given(bookMarkRepository.countByMemberIdAndIsbn(anyLong(), anyString())).willReturn(1L);
		mockMvc.perform(post("/book/bookmark")
		.flashAttr("bookMark", new BookMark(1L,"isbn1","title1","publisher1","url1","authors1",null,null,null)))
		.andExpect(status().isOk())
		.andExpect(content().string("Duplicate"));
	}
	

	@Test
	public void removeBookMark() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());
		given(bookMarkRepository.findById(anyLong())).willReturn(Optional.ofNullable(new BookMark(1L,"isbn1","title1","publisher1","url1","authors1",null,null,null)));
		mockMvc.perform(post("/book/bookmark/remove")
		.param("bookmarkNo", "1"))
		.andExpect(status().isOk())
		.andExpect(content().string("Success"));
	}
	
	@Test
	public void removeBookMarkNotExist() throws Exception {		
		given(securityMemberService.getLoginUser()).willReturn(getMember());
		given(bookMarkRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
		mockMvc.perform(post("/book/bookmark/remove")
		.param("bookmarkNo", "1"))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	@Test
	public void insertBookMarkNotSendBookMark() throws Exception {		
		mockMvc.perform(post("/book/bookmark"))
		.andExpect(status().isOk())
		.andExpect(content().string("Fail"));
	}
	
	
	
	private Member getMember() {
		return new Member(1L, "uid", "upw", "uname", null, null, null);
	}
	
	private List<History> getHistories() {
		List<History> histories = new ArrayList<History>();
		histories.add(new History(1L,"title1","publisher1","url1","authors1",null,null,null));
		histories.add(new History(2L,"title2","publisher2","url2","authors2",null,null,null));
		return histories;
	}
	
	private List<BookMark> getBookMarks() {
		List<BookMark> bookMarks = new ArrayList<BookMark>();
		bookMarks.add(new BookMark(1L,"isbn1","title1","publisher1","url1","authors1",null,null,null));
		bookMarks.add(new BookMark(2L,"isbn2 isbnlong2","title2","publisher2","url2","authors2",null,null,null));
		return bookMarks;
	}
}
