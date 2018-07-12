package com.kabank.book.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="history")
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long no;
	
	@Column (nullable = false, length = 100)
	private String bookName;
	
	@Column (nullable = false, length = 100)
	private String bookPublisher;
	
	@Column (nullable = false, length = 20)
	private String bookIsbn;
	
	@Column
	private String bookThumbnail;
	
	@Column 
	private boolean isBookMark;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@UpdateTimestamp
	private Timestamp updateDate;	

	private Long userNo;

}
