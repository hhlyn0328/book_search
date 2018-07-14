package com.kabank.book.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
	private String title;
	private String contents;
	private String url;
	private String isbn;
	private String datetime;
	private List<String> authors;
	private String publisher;
	private List<String> translators;
	private int price;
	private int sale_price;
	private String sale_yn;
	private String category;
	private String thumbnail;
	private String barcode;
	private String ebook_barcode;
	private String status;
}
