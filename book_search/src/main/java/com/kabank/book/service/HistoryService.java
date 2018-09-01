package com.kabank.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kabank.book.model.History;

public interface HistoryService {
	Page<History> findAll(Pageable pageable);
	Page<History> findByMemberId (Long memberId, Pageable pageable);
	void save(History history);
}
