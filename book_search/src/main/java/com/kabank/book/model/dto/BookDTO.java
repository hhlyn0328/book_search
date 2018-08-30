package com.kabank.book.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//KakaoAPI 결과 저장을 위한 model

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	private String title;
	private String contents;
	private String url;
	private String isbn;
	private List<String> authors;
	private String publisher;
	private String thumbnail;
}
