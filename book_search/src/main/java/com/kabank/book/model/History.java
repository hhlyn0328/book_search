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
	private String title;
	
	@Column (nullable = false, length = 100)
	private String publisher;
	
	@Column (nullable = false)
	private String url;
	
	@Column (nullable = false, length = 100)
	private String authors;
	
	@Column
	private String thumbnail;
		
	@CreationTimestamp
	private Timestamp createDate;

	private Long uid;

}
