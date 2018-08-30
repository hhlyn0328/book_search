package com.kabank.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kabank.book.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
	
	Page<History> findAll(Pageable pageable);
	Page<History> findByMemberId (Long memberId, Pageable pageable);
}
