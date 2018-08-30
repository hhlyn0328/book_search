package com.kabank.book.service;

import com.kabank.book.model.dto.BookDTO;
import com.kabank.book.model.helper.FilterHelper;
import com.kabank.book.model.helper.PageableApiResponse;
import com.kabank.book.model.kakao.BookSearchResponse;
import com.kabank.book.model.kakao.BookSearchResponseDocument;
import com.kabank.book.model.kakao.BookSearchResponseMeta;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KakaoBookServiceTest {
		
	@Value("${kakao.restapi.booksearch.thumbnail.default}")
	private String thumbnailDefault;
	
	@Mock
	private KakaoBookAdapter kakaoBookAdapter;
	
	@InjectMocks
	private KakaoBookServiceImpl sut;		
	
	@Test
    public void test_pageableCount() throws IOException {

        BookSearchResponseMeta meta = new BookSearchResponseMeta();
        meta.setPageableCount(10);

        BookSearchResponse response = new BookSearchResponse();
        response.setDocuments(Lists.newArrayList());
        response.setMeta(meta);
        given(kakaoBookAdapter.search(any(MultiValueMap.class))).willReturn(response);
        PageableApiResponse<BookDTO> actual = sut.findByCriteria(new FilterHelper("", "", "", "", 1, 10));

        assertThat(actual.getPageableCount(), Matchers.equalTo(10L));
    }
	
	@Test
    public void test_contentsSize() throws IOException {

        BookSearchResponse response = getKakaoBookSearchResponse();
        given(kakaoBookAdapter.search(any(MultiValueMap.class))).willReturn(response);
        PageableApiResponse<BookDTO> actual = sut.findByCriteria(new FilterHelper("", "", "", "", 1, 10));

        assertThat(actual.getContents().size(), Matchers.equalTo(4));
    }

	@Test
    public void test_toBook() throws IOException {

        BookSearchResponse response = getKakaoBookSearchResponse();
        given(kakaoBookAdapter.search(any(MultiValueMap.class))).willReturn(response);
        PageableApiResponse<BookDTO> actual = sut.findByCriteria(new FilterHelper("", "", "", "", 1, 10));

        assertThat(actual.getContents().get(0).getTitle(), Matchers.equalTo("미움 극복"));
    }

	@Test
    public void test_setThumbnail() throws IOException {

        BookSearchResponse response = getKakaoBookSearchResponse();
        given(kakaoBookAdapter.search(any(MultiValueMap.class))).willReturn(response);
        PageableApiResponse<BookDTO> actual = sut.findByCriteria(new FilterHelper("", "", "", "", 1, 10));

        assertThat(actual.getContents().get(0).getThumbnail(), Matchers.equalTo("http://t1.daumcdn.net/thumb/1"));
    }
	
	@Test
    public void test_setThumbnailDefault() throws IOException {

        BookSearchResponse response = getKakaoBookSearchResponse();
        given(kakaoBookAdapter.search(any(MultiValueMap.class))).willReturn(response);
        PageableApiResponse<BookDTO> actual = sut.findByCriteria(new FilterHelper("", "", "", "", 1, 10));

        assertThat(actual.getContents().get(2).getThumbnail(), Matchers.equalTo(thumbnailDefault));
    }
	
	private BookSearchResponse getKakaoBookSearchResponse() {
		BookSearchResponse response = new BookSearchResponse();
		BookSearchResponseMeta meta = new BookSearchResponseMeta(100,10,false);
		List<BookSearchResponseDocument> documents = new ArrayList<BookSearchResponseDocument>();

		documents.add(new BookSearchResponseDocument("미움 극복", "http://t1.daumcdn.net/thumb/1" ));
		documents.add(new BookSearchResponseDocument("마음아 그만, 미움아 그만" , "http://t1.daumcdn.net/thumb/2"));
		documents.add(new BookSearchResponseDocument("모든 순간이 너였다", ""));
		documents.add(new BookSearchResponseDocument("미움받을 용기" , ""));		
		
		response.setMeta(meta);
		response.setDocuments(documents);
		
		return response;
	}
}
