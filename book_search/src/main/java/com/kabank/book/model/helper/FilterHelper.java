package com.kabank.book.model.helper;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterHelper {
	
	private String searchField;
	private String searchValue;
	private String sortField;
	private String sortDir;
	private int page;
	private int pageSize;	
	
	public FilterHelper(String searchField, String searchValue, String sortField, String sortDir, int page, int size) {
		this.searchField = searchField;
		this.searchValue = searchValue;
		this.sortField = sortField;
		this.sortDir = sortDir;
		this.page = page;
		this.pageSize = size;
	}
}
