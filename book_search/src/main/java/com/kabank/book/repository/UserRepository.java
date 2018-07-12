package com.kabank.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kabank.book.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Modifying
	@Query ("UPDATE History h set h.isBookMark = ?2 WHERE h.no = ?1 ")
	public int updateBookMark(Long historyId, boolean isBookMark);
	
}
