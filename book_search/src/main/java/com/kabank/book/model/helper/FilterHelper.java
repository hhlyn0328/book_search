package com.kabank.book.model.helper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//pageable 사용 불가한 api 를 위한 필터

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterHelper {
	
	private String searchField;
	private String searchValue;
	private String sortField;
	private String sortDir;
	private int page;
	private int pageSize;	
	
}
