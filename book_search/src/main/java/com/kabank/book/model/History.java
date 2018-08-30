package com.kabank.book.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString(exclude="member")
@Table(name="histories")
@NoArgsConstructor
@AllArgsConstructor
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column (nullable = false)
	private String title;
	
	@Column
	private String publisher;
	
	@Column
	private String url;
	
	@Column
	private String authors;
	
	@Column
	private String thumbnail;
		
	@CreationTimestamp
	private LocalDateTime createdAt;

	@ManyToOne
	private Member member;
}
