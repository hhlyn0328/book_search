package com.kabank.book.model.kakao;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//KakaoAPI 결과 저장을 위한 model

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResponseDocument {	
	private String title;
	private String contents;
	private String url;
	private String isbn;
	private String datetime;
	private List<String> authors;
	private String publisher;
	private List<String> translators;
	private int price;
	private int salePrice;
	private String saleYn;
	private String category;
	private String thumbnail;
	private String barcode;
	private String ebookBarcode;
	private String status;
	
	public BookSearchResponseDocument (String title, String thumbnail) {
		this.title = title;
		this.thumbnail = thumbnail;
	}
}

