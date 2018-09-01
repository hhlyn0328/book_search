package com.kabank.book.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kabank.book.model.BookMark;

public interface BookMarkService {
	Optional<BookMark> findById(Long Id);
	Page<BookMark> findAll(Pageable pageable);
	Page<BookMark> findByMemberId (Long memberId, Pageable pageable);
	Long countByMemberIdAndIsbn (Long memberId, String isbn);
	BookMark findByMemberIdAndIsbn (Long memberId, String isbn);
	Optional<BookMark> findByMemberIdAndId(Long memberId, Long Id);
	void save(BookMark bookMark);
	void delete(BookMark bookMark);
}
