package com.kabank.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kabank.book.model.BookMark;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
	
	BookMark findByNo(Long no);
	Page<BookMark> findAll(Pageable pageable);
	Page<BookMark> findByUid (Long uid, Pageable pageable);
	Long countByUidAndIsbn (Long uid, String isbn);
	BookMark findByUidAndIsbn (Long uid, String isbn);
}
