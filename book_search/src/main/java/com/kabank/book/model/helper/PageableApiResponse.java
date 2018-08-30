package com.kabank.book.model.helper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageableApiResponse<T> {
	
	private List<T> contents;
	private long pageableCount;
}

