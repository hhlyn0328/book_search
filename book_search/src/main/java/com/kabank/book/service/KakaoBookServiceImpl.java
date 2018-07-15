package com.kabank.book.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabank.book.model.Book;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageHelper;

@Service("kakaoBookSearchService")
public class KakaoBookServiceImpl implements BookService {
	
	//kakao developer App Key
	@Value("${kakao.restapi.appkey}")
	private String apiAppKey;
	
	//api protocol
	@Value("${kakao.restapi.booksearch.scheme}")
	private String scheme;
	
	//api host
	@Value("${kakao.restapi.booksearch.host}")
	private String host;
	
	//api api uri
	@Value("${kakao.restapi.booksearch.path}")
	private String path;
	
	//thumbnail 이 없을 때, 보여줄 이미지
	@Value("${kakao.restapi.booksearch.thumbnail.default}")
	private String thumbnailDefault;
	
	//api 호출 결과 json 을 List<Book> 형태로 변환
	public List<Book> findByFilter(FilterHelper filter, PageHelper page){
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		params.add("target", filter.getSearchField());
		params.add("query", filter.getSearchValue());
		params.add("page", String.valueOf(filter.getPage()));
		params.add("size", String.valueOf(filter.getPageSize()));
		params.add("sort", filter.getSortField());	
		List<Book> books = new ArrayList<Book>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = search(params);
			JsonNode meta = node.get("meta");
			BeanUtils.copyProperties(new PageHelper(filter.getPage(),filter.getPageSize(),Integer.parseInt(meta.get("pageable_count").toString())), page);
			JsonNode documents = node.get("documents");
			documents.forEach(d -> {
				Book book = mapper.convertValue(d, Book.class);
				if(book.getThumbnail() == "") { 
					book.setThumbnail(thumbnailDefault);
				}
				books.add(book);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return books;
	}
	
	//kakao api 호출
	private JsonNode search(MultiValueMap<String, String> params) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = UriComponentsBuilder.newInstance()
				.scheme(scheme)
				.host(host)
				.path(path)
				.queryParams(params)
				.build().encode().toUri();
		HttpHeaders headers = new HttpHeaders();		
		headers.set("Authorization", apiAppKey);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String result = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResult = mapper.readTree(result);
        
        return jsonResult;
	}
}
