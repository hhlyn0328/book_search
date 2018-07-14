package com.kabank.book;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoBookSearchTest {
/*	@Autowired
	private KakaoBookRepository bookSearchService;

	@Test
	public void searchByMultiValueMapTest() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target", "title");
		params.add("query", "미움");
		params.add("page", "2");
		params.add("size", "5");
		params.add("sort", "recency");		
		JsonNode node = bookSearchService.search(params);
		JsonNode meta = node.get("meta");
		JsonNode documents = node.get("documents");
		System.out.println("total : "+ meta.get("total_count"));
		documents.forEach(d -> {
			System.out.println("title : " + d.get("title") + ", date : " + d.get("datetime"));
		});
	}*/
}
