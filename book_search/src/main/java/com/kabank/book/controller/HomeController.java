package com.kabank.book.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kabank.book.model.Book;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageHelper;
import com.kabank.book.service.BookService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/search")
	public ModelAndView home(HttpServletRequest request, 
			@RequestParam(value="searchField", defaultValue="all") String searchField,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			@RequestParam(value="sortField", defaultValue="accuracy") String sortField,
			@RequestParam(value="sortDir", defaultValue="ASC") String sortDir,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		FilterHelper filterHelper = new FilterHelper(searchField, searchValue, sortField, sortDir, page, pageSize);
		PageHelper pageHelper = new PageHelper(1, 0);
		List<Book> books = new ArrayList<Book>();
		
		if(searchValue.length() != 0) {
			books = bookService.findByFilter(filterHelper, pageHelper);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageUri", request.getRequestURI());
		modelAndView.addObject("filterHelper", filterHelper);
		modelAndView.addObject("pageHelper", pageHelper);
		modelAndView.addObject("books", books);
		modelAndView.setViewName("booksearch/main");
		return modelAndView;
	}
}
