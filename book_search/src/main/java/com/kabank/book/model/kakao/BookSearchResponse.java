package com.kabank.book.model.kakao;

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
public class BookSearchResponse {	
	private BookSearchResponseMeta meta;
	private List<BookSearchResponseDocument> documents;
}

