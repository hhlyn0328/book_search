package com.kabank.book.model.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BookSearchResponseMeta {	
	
	private long totalCount;
	private long pageableCount;	
	@JsonProperty("is_end")
	private boolean end;
	
}

