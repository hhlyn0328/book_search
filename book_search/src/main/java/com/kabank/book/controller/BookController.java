package com.kabank.book.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import com.kabank.book.model.BookMark;
import com.kabank.book.model.History;
import com.kabank.book.model.Member;
import com.kabank.book.model.dto.BookDTO;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageHelper;
import com.kabank.book.model.helper.PageableApiResponse;
import com.kabank.book.repository.BookMarkRepository;
import com.kabank.book.repository.HistoryRepository;
import com.kabank.book.service.BookService;
import com.kabank.book.service.security.SecurityMemberService;


@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	SecurityMemberService securityMemberService;

	@Autowired
	HistoryRepository historyRepository;

	@Autowired
	BookMarkRepository bookMarkRepository;
	
	Logger log = LoggerFactory.getLogger("BookController");

	// 책 검색
	@GetMapping("/search")
	public ModelAndView search(HttpServletRequest request,
			@RequestParam(value = "searchField", defaultValue = "all") String searchField,
			@RequestParam(value = "searchValue", defaultValue = "") String searchValue,
			@RequestParam(value = "sortField", defaultValue = "accuracy") String sortField,
			@RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		FilterHelper filterHelper = new FilterHelper(searchField, searchValue, sortField, sortDir, page, pageSize);
		
		PageableApiResponse<BookDTO> response = null;
		if (!StringUtils.isEmpty(searchValue)) {
			response = bookService.findByCriteria(filterHelper);
		}else {			
			response = new PageableApiResponse<>(new ArrayList<BookDTO>(), 0);
		}
		
		PageHelper pageHelper = new PageHelper(page, pageSize, response.getPageableCount());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageUri", request.getRequestURI());
		modelAndView.addObject("filterHelper", filterHelper);
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("books", response.getContents());
		modelAndView.addObject("bookmark", new BookMark());
		modelAndView.addObject("history", new History());
		modelAndView.setViewName("book/search");
		return modelAndView;
	}

	// 검색 히스토리 조회
	@GetMapping("/history")
	public ModelAndView history(HttpServletRequest request, 
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		Member member = securityMemberService.getLoginUser();
		if(member == null) {
			return new ModelAndView("redirect:/login");
		}

		Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createdAt");
		Page<History> histories = historyRepository.findByMemberId(member.getId(), pageable);
		PageHelper pageHelper = new PageHelper(page, pageSize, histories.getTotalElements());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageUri", request.getRequestURI());
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("histories", histories.getContent());
		modelAndView.setViewName("book/history");
		return modelAndView;
	}
	
	@ResponseBody
	@PostMapping("/history")
	public String addHistory(HttpServletRequest request, 
			@ModelAttribute("history") @Valid History history,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			log.error("유효하지 않은 검색이력 정보");
			return "Fail";
		}

		Member member = securityMemberService.getLoginUser();
		if(member == null) {
			return "Fail";
		}
		
		history.setMember(member);
		historyRepository.save(history);
		return "Success";
	}

	// 북마크 조회
	@GetMapping("/bookmark")
	public ModelAndView bookmark(HttpServletRequest request, 
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(value = "sortDir", defaultValue = "DESC") String sortDir) {

		Member member = securityMemberService.getLoginUser();
		if(member == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Pageable pageable = PageRequest.of(page - 1, pageSize,
				"ASC".equals(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
		Page<BookMark> bookmarks = bookMarkRepository.findByMemberId(member.getId(), pageable);
		PageHelper pageHelper = new PageHelper(page, pageSize, bookmarks.getTotalElements());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageUri", request.getRequestURI());
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("sortDir", sortDir);
		modelAndView.addObject("sortField", sortField);
		modelAndView.addObject("bookmarks", bookmarks.getContent());
		modelAndView.setViewName("book/bookmark");
		return modelAndView;
	}
	
	@ResponseBody
	@PostMapping("/bookmark")
	public String addBookmark(HttpServletRequest request,
			@ModelAttribute("bookMark") @Valid BookMark bookMark,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			log.error("유효하지 않은 북마크 정보");
			return "Fail";
		}

		Member member = securityMemberService.getLoginUser();
		if(member == null) {
			return "Fail";
		}

		bookMark.setIsbn(getKeyIsbn(bookMark.getIsbn()));
		
		if (bookMarkRepository.countByMemberIdAndIsbn(member.getId(), bookMark.getIsbn()) == 0) {
			bookMark.setMember(member);
			bookMarkRepository.save(bookMark);
			return "Success";
		}
		
		log.info("중복된 북마크 등록");
		return "Duplicate";

	}

	// ISBN10,ISBN13 중에 하나 이상 존재할 경우 ' '(공백)을 구분자로 뒤의 isbn13을 키로 사용
	private String getKeyIsbn(String isbn) {
		if (isbn.contains(" ")) {
			return isbn.substring(isbn.indexOf(" ") + 1);
		}
		return isbn;
	}

	// 북마크 제거
	@ResponseBody
	@PostMapping("/bookmark/remove")
	public String addBookmark(HttpServletRequest request, @RequestParam(value = "bookmarkNo") String bookmarkNo) {

		try {
			Optional<BookMark> bookMarkOptional = bookMarkRepository.findById(Long.valueOf(bookmarkNo));
			if(bookMarkOptional.isPresent()) {
				bookMarkRepository.delete(bookMarkOptional.get());
				return "Success";
			}			
		} catch (Exception e) {
			log.error("북마크 삭제 실패 : " + e.getStackTrace());			
		}
		return "Fail";
	}
	
}
