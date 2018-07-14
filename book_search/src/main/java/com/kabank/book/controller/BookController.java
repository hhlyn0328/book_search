package com.kabank.book.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kabank.book.model.Book;
import com.kabank.book.model.BookMark;
import com.kabank.book.model.History;
import com.kabank.book.model.Member;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageHelper;
import com.kabank.book.repository.BookMarkRepository;
import com.kabank.book.repository.HistoryRepository;
import com.kabank.book.repository.MemberRepository;
import com.kabank.book.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	MemberRepository memberRepository; 
	
	@Autowired
	HistoryRepository historyRepository; 
	
	@Autowired
	BookMarkRepository bookMarkRepository; 
	
	@GetMapping("/search")
	public ModelAndView search(HttpServletRequest request, 
			@RequestParam(value="searchField", defaultValue="all") String searchField,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			@RequestParam(value="sortField", defaultValue="accuracy") String sortField,
			@RequestParam(value="sortDir", defaultValue="ASC") String sortDir,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		FilterHelper filterHelper = new FilterHelper(searchField, searchValue, sortField, sortDir, page, pageSize);
		PageHelper pageHelper = new PageHelper(1, pageSize, 0);
		List<Book> books = new ArrayList<Book>();
		
		if(searchValue.length() != 0) {
			books = bookService.findByFilter(filterHelper, pageHelper);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageUri", request.getRequestURI());
		modelAndView.addObject("filterHelper", filterHelper);
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("books", books);
		modelAndView.setViewName("book/search");
		return modelAndView;
	}
	
	@GetMapping("/history")
	public ModelAndView history(HttpServletRequest request, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member member = memberRepository.findByUid(auth.getName());
		Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createDate");
		
		Page<History> histories = historyRepository.findByUid(member.getId(), pageable);
		PageHelper pageHelper = new PageHelper(page, pageSize, histories.getTotalPages());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("histories", histories.getContent());
		modelAndView.setViewName("book/history");
		return modelAndView;
	}
	
	@ResponseBody
	@PostMapping("/history")
	public String addHistory(HttpServletRequest request, 
			@RequestParam(value="title", defaultValue="") String title,
			@RequestParam(value="publisher", defaultValue="") String publisher,
			@RequestParam(value="authors", defaultValue="") String authors,
			@RequestParam(value="thumbnail", defaultValue="") String thumbnail,
			@RequestParam(value="detailUrl", defaultValue="") String detailUrl) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member member = memberRepository.findByUid(auth.getName());
		
		History history = new History();
		history.setTitle(title);
		history.setAuthors(authors);
		history.setPublisher(publisher);
		history.setThumbnail(thumbnail);
		history.setUid(member.getId());
		history.setUrl(detailUrl);
		
		member.getHistories().add(history);
		memberRepository.save(member);
		return "Success";
	}
	
	@GetMapping("/bookmark")
	public ModelAndView bookmark(HttpServletRequest request, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member member = memberRepository.findByUid(auth.getName());
		Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createDate");
		
		Page<BookMark> bookmarks = bookMarkRepository.findByUid(member.getId(), pageable);
		PageHelper pageHelper = new PageHelper(page, pageSize, bookmarks.getTotalPages());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("bookmarks", bookmarks.getContent());
		modelAndView.setViewName("book/bookmark");
		return modelAndView;
	}
	
	@ResponseBody
	@PostMapping("/bookmark")
	public String addBookmark(HttpServletRequest request, 
			@RequestParam(value="title", defaultValue="") String title,
			@RequestParam(value="publisher", defaultValue="") String publisher,
			@RequestParam(value="authors", defaultValue="") String authors,
			@RequestParam(value="thumbnail", defaultValue="") String thumbnail,
			@RequestParam(value="detailUrl", defaultValue="") String detailUrl,
			@RequestParam(value="isbn", defaultValue="") String isbn) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member member = memberRepository.findByUid(auth.getName());
		
		if(isbn != "") {
			String key = "";
			BookMark mark = new BookMark();
			if(isbn.contains(" ")) {
				key = isbn.substring(isbn.indexOf(" ")+1);
				mark.setIsbn(key);
			}else {
				mark.setIsbn(isbn);
			}
			if(bookMarkRepository.countByUidAndIsbn(member.getId(), key) == 0) {
				mark.setTitle(title);
				mark.setAuthors(authors);
				mark.setPublisher(publisher);
				mark.setThumbnail(thumbnail);
				mark.setUid(member.getId());
				mark.setUrl(detailUrl);
				bookMarkRepository.save(mark);
				return "Success";
			}			
		}
		return "Fail";
	}
	
	@ResponseBody
	@PostMapping("/bookmark/remove")
	public String addBookmark(HttpServletRequest request, 
			@RequestParam(value="bookmarkNo") Long bookmarkNo) {
		
		try {

			BookMark mark = bookMarkRepository.findByNo(bookmarkNo);
			bookMarkRepository.delete(mark);
		}catch(Exception e) {
			return e.getMessage();
		}
		
		return "Success";
	}
}
