package com.kabank.book.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="member")
@EqualsAndHashCode(of = "uid")
public class Member {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true, length=50)
	private String uid;
	
	@Column(nullable = false, length=200)
	private String upw;
	
	@Column(nullable = false, unique = true, length=50)
	private String uname;
	
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="uid")
	private List<Role> roles;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "uid")
	private List<History> histories;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "uid")
	private List<BookMark> BookMarks;
}
