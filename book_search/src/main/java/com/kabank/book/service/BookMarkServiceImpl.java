package com.kabank.book.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kabank.book.model.BookMark;
import com.kabank.book.repository.BookMarkRepository;

@Service("bookMarkService")
public class BookMarkServiceImpl implements BookMarkService {
	
	@Autowired
	BookMarkRepository bookMarkRepository;
	
	public Optional<BookMark> findById(Long Id){
		return bookMarkRepository.findById(Id);
	}
	
	public Page<BookMark> findAll(Pageable pageable){
		return bookMarkRepository.findAll(pageable);
	}
	
	public Page<BookMark> findByMemberId (Long memberId, Pageable pageable){
		return bookMarkRepository.findByMemberId(memberId, pageable);
	}
	
	public Long countByMemberIdAndIsbn (Long memberId, String isbn) {
		return bookMarkRepository.countByMemberIdAndIsbn(memberId, isbn);
	}
	
	public BookMark findByMemberIdAndIsbn (Long memberId, String isbn) {
		return bookMarkRepository.findByMemberIdAndIsbn(memberId, isbn);
	}
	
	public Optional<BookMark> findByMemberIdAndId(Long memberId, Long Id){
		return bookMarkRepository.findByMemberIdAndId(memberId, Id);
	}	
	public void save(BookMark bookMark) {
		bookMarkRepository.save(bookMark);
	}	
	public void delete(BookMark bookMark) {
		bookMarkRepository.delete(bookMark);
	}
}
