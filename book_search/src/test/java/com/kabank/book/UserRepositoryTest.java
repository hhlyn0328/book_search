package com.kabank.book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kabank.book.model.History;
import com.kabank.book.model.User;
import com.kabank.book.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepo;
	/*
	@Test
	public void insertTest() {
		
		IntStream.range(1, 10).forEach(i -> {
			User user = new User();
			user.setId("user" + i);
			user.setPwd("pwd" + i);
			user.setName("username" + i);
			
			userRepo.save(user);
		});
	}
	
	@Test
	public void findOneTest() {
		User user = userRepo.findById(1L).orElse(null);
		System.out.println(user);
	}
	
	@Test
	public void updateTest() {
		User user = userRepo.findById(1L).orElse(null);
		user.setName("수정된 이름");
		userRepo.save(user);
	}
	
	*/
	
	@Test
	public void insertWithHistoryTest() {
		IntStream.range(1, 11).forEach(i -> {
			User user = new User();
			user.setId("user" + i);
			user.setPwd("pwd" + i);
			user.setName("username" + i);
			List<History> histories = new ArrayList<History>();
			IntStream.range(0, 10).forEach(j -> {
				History history = new History();
				history.setBookName("book" + i + j);
				history.setBookPublisher("Publisher" + i + j);
				history.setBookIsbn("isbn" + i + j);
				history.setBookMark(j%2 == 0);
				histories.add(history);
			});
			user.setHistories(histories);
			userRepo.save(user);
		});
	}
	
	
	
	/*
	@Transactional
	@Test
	public void findByUserNoTest() {
		User user = userRepo.findById(100L).orElse(null);
		if(user != null) {
			user.getHistories().forEach(h -> {
				System.out.println(h);
			});
		}
	}
	
	
	@Transactional
	@Test
	public void findByBookMarkTest() {
		List<User> users = userRepo.findAll();
		users.forEach(u -> {
			u.getBookmarks().forEach(m -> {
				System.out.println(m);
			});
		});
	}
	
	@Transactional
	@Test
	public void updateBookMarkTest() {
		User user = userRepo.findAll().get(0);
		user.getHistories().forEach(h -> {
			System.out.println(userRepo.updateBookMark(h.getNo(), !h.isBookMark()));
		});		
	}
	 
	
	@Transactional
	@Test
	public void deleteUser() {
		User user = userRepo.findAll().get(0);
		userRepo.delete(user);
	}
	*/
}
