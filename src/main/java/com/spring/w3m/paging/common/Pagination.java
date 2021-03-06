package com.spring.w3m.paging.common;

import org.springframework.stereotype.Repository;

@Repository
public class Pagination {
	private int rownum;
	private int listSize = 10; // 초기값으로 목록개수를 10로 셋팅
	private int rangeSize = 10; // 초기값으로 페이지범위를 10으로 셋팅
	private int page;
	private int range;
	private int listCnt;
	private int pageCnt;
	private int startPage;
	private int startList;
	private int endPage;
	private boolean prev;
	private boolean next;
	private String searchKeyword;
	private String keyword;
	private String searchType;
	private boolean fix;

	// get,setter
	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getRangeSize() {
		return rangeSize;
	}

	public void setRangeSize(int rangeSize) {
		this.rangeSize = rangeSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getListCnt() {
		return listCnt;
	}

	public void setListCnt(int listCnt) {
		this.listCnt = listCnt;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getStartList() {
		return startList;
	}

	public void setStartList(int startList) {
		this.startList = startList;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void pageInfo(int page, int range, int listCnt) {
		this.page = page;
		this.range = range;
		this.listCnt = listCnt;

		// 전체 페이지수
		this.pageCnt = (int) Math.ceil((double) listCnt / listSize);

		// 시작 페이지
		this.startPage = (range - 1) * rangeSize + 1;

		// 끝 페이지
		this.endPage = range * rangeSize;

		// 게시판 시작번호
		this.startList = (page - 1) * listSize;

		// 이전 버튼 상태
		this.prev = range == 1 ? false : true;

		// 다음 버튼 상태
		this.next = pageCnt > endPage ? true : false;

		if (this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.next = false;
		}
	}

	public void pageInfo(int page, int range, int listCnt, String searchKeyword) {

		this.page = page;
		this.range = range;
		this.listCnt = listCnt;
		this.searchKeyword = searchKeyword;

		// 전체 페이지수
		this.pageCnt = (int) Math.ceil((double) listCnt / listSize);

		// 시작 페이지
		this.startPage = (range - 1) * rangeSize + 1;

		// 끝 페이지
		this.endPage = range * rangeSize;

		// 게시판 시작번호
		this.startList = (page - 1) * listSize;

		// 이전 버튼 상태
		this.prev = range == 1 ? false : true;

		// 다음 버튼 상태
		this.next = pageCnt > endPage ? true : false;

		if (this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.next = false;
		}
	}

	@Override
	public String toString() {
		return "Pagination [rownum=" + rownum + ", listSize=" + listSize + ", rangeSize=" + rangeSize + ", page=" + page
				+ ", range=" + range + ", listCnt=" + listCnt + ", pageCnt=" + pageCnt + ", startPage=" + startPage
				+ ", startList=" + startList + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", searchKeyword=" + searchKeyword + ", keyword=" + keyword + ", searchType=" + searchType + ", fix="
				+ fix + ", getRownum()=" + getRownum() + ", getListSize()=" + getListSize() + ", getRangeSize()="
				+ getRangeSize() + ", getPage()=" + getPage() + ", getRange()=" + getRange() + ", getListCnt()="
				+ getListCnt() + ", getPageCnt()=" + getPageCnt() + ", getStartPage()=" + getStartPage()
				+ ", getStartList()=" + getStartList() + ", getEndPage()=" + getEndPage() + ", isPrev()=" + isPrev()
				+ ", isNext()=" + isNext() + ", getSearchKeyword()=" + getSearchKeyword() + ", isFix()=" + isFix()
				+ ", getKeyword()=" + getKeyword() + ", getSearchType()=" + getSearchType() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
