package com.kabank.book.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long no;
	
	@Column (unique = true, nullable = false, length = 100)
	private String id;
	
	@Column (nullable = false, length = 100)
	private String pwd;
	
	@Column (length = 50)
	private String name;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userNo")
	private List<History> histories;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userNo")
	@Where(clause = "is_book_mark = true")
	private List<History> bookmarks;
	
}
