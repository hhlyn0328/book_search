package com.kabank.book.service;

import java.util.List;

import com.kabank.book.model.Book;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageHelper;

public interface BookService {
	public List<Book> findByFilter(FilterHelper filter, PageHelper page);
}
