package com.kabank.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kabank.book.model.History;
import com.kabank.book.repository.HistoryRepository;

@Service("historyService")
public class HistoryServiceImpl {
	
	@Autowired
	HistoryRepository historyRepository;
	
	public Page<History> findAll(Pageable pageable){
		return historyRepository.findAll(pageable);
	}
	
	public Page<History> findByMemberId (Long memberId, Pageable pageable){
		return historyRepository.findByMemberId(memberId, pageable);
	}
	
	public void save(History history) {
		historyRepository.save(history);
	}
}
