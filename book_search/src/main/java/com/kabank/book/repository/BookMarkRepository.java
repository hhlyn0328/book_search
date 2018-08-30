package com.kabank.book.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kabank.book.model.BookMark;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
	
	Optional<BookMark> findById(Long Id);
	Page<BookMark> findAll(Pageable pageable);
	Page<BookMark> findByMemberId (Long memberId, Pageable pageable);
	Long countByMemberIdAndIsbn (Long memberId, String isbn);
	BookMark findByMemberIdAndIsbn (Long memberId, String isbn);
}
