package com.kabank.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.kabank.book.model.dto.BookDTO;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageableApiResponse;
import com.kabank.book.model.kakao.BookSearchResponse;
import com.kabank.book.model.kakao.BookSearchResponseDocument;

@Service("kakaoBookSearchService")
public class KakaoBookServiceImpl implements BookService {

	//thumbnail 이 없을 때, 보여줄 이미지
	@Value("${kakao.restapi.booksearch.thumbnail.default}")
	private String thumbnailDefault;
	
	@Autowired
	private KakaoBookAdapter kakaoBookAdapter;
	
	Logger log = LoggerFactory.getLogger("KakaoBookSearchApiService");
	
	//api 호출 결과 json 을 List<BookDTO> 형태로 변환
	public PageableApiResponse<BookDTO> findByCriteria(FilterHelper filter){
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();		
		params.add("target", filter.getSearchField());
		params.add("query", filter.getSearchValue());
		params.add("page", String.valueOf(filter.getPage()));
		params.add("size", String.valueOf(filter.getPageSize()));
		params.add("sort", filter.getSortField());	
		

		BookSearchResponse bookResponse = null; 
		try {
			bookResponse = kakaoBookAdapter.search(params);
		} catch(Exception e) {
			log.error("KAKAO API 검색 실패 : " + e.getStackTrace());
			return new PageableApiResponse<BookDTO>(new ArrayList<BookDTO>(), 0);

		}
		
		List<BookDTO> books = bookResponse.getDocuments()
			.stream()
			.map(toBook()).collect(Collectors.toList());
	
		return new PageableApiResponse<>(books, bookResponse.getMeta().getPageableCount());
	}
	
	private Function<BookSearchResponseDocument, BookDTO> toBook() {
		return it -> {
            BookDTO book = new BookDTO();
            book.setTitle(it.getTitle());
            book.setContents(it.getContents());
            book.setUrl(it.getUrl());
            book.setIsbn(it.getIsbn());
            book.setAuthors(it.getAuthors());
            book.setPublisher(it.getPublisher());
            book.setThumbnail(setThumbnailDefault(it.getThumbnail()));
            return book;
        };
	}

	private String setThumbnailDefault(String thumbnail) {
		if(StringUtils.isEmpty(thumbnail)) {
            return thumbnailDefault;
        }else {
            return thumbnail;
        }
	}
}
