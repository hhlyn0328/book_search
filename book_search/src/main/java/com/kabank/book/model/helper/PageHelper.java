package com.kabank.book.model.helper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// pagenation model

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageHelper {
	
	private long currentPageNum;
	private long totalPageNum;
	private long beginPageNum;
	private long endPageNum;
	private long totalSize;
	private long pageSize;
	private boolean start;
	private boolean end;
	private boolean empty;
		
	public PageHelper(long page, long pageSize, long totalCount) {
		this.currentPageNum = page;
		this.pageSize = pageSize;
		this.totalSize = totalCount;
		this.totalPageNum = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		this.endPageNum = Math.min((int)(Math.ceil(this.currentPageNum/10.0)*10), totalPageNum);
		this.beginPageNum = Math.max(endPageNum - 9, 1);
		this.end = (this.endPageNum == this.totalPageNum);
		this.start = (this.beginPageNum == 1);
		this.empty = (this.totalPageNum == 0);		
	}	
}
