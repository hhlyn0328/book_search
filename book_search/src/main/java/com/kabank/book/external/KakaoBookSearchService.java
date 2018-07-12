package com.kabank.book.external;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("kakaoBookSearchService")
public class KakaoBookSearchService {
	
	@Value("${kakao.restapi.appkey}")
	private String apiAppKey;
	
	@Value("${kakao.restapi.booksearch.scheme}")
	private String scheme;
	
	@Value("${kakao.restapi.booksearch.host}")
	private String host;
	
	@Value("${kakao.restapi.booksearch.path}")
	private String path;
	
	public JsonNode search(MultiValueMap<String, String> params) throws IOException {
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
