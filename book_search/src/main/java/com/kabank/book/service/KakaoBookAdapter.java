package com.kabank.book.service;

import java.io.IOException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabank.book.model.kakao.BookSearchResponse;


@Service
public class KakaoBookAdapter {
	
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
	

	//kakao api 호출
	public BookSearchResponse search(MultiValueMap<String, String> params) throws IOException {
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
		return mapper.readValue(result, BookSearchResponse.class);
	}
}
