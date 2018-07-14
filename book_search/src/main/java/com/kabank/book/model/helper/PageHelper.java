package com.kabank.book.model.helper;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageHelper {
	
	private int currentPageNum;
	private int totalPageNum;
	private int beginPageNum;
	private int endPageNum;
	private boolean start;
	private boolean end;
	private boolean empty;
	
	public PageHelper() { }
	
	public PageHelper(int page, int totalCount) {
		this.currentPageNum = page;
		this.totalPageNum = totalCount;
		this.endPageNum = Math.min((int)(Math.ceil(this.currentPageNum/10.0)*10), totalPageNum);
		this.beginPageNum = Math.max(endPageNum - 9, 1);
		this.end = (this.endPageNum == this.totalPageNum);
		this.start = (this.beginPageNum == 1);
		this.empty = (this.totalPageNum == 0);		
	}	
}
