package com.kabank.book.service;

import com.kabank.book.model.dto.BookDTO;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageableApiResponse;

public interface BookService {
	PageableApiResponse<BookDTO> findByCriteria(FilterHelper filter);
}
