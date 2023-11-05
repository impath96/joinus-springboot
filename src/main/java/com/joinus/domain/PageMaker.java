package com.joinus.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageMaker {
	
	//상단부 페이징 처리
	private Criteria cri;
	
	//하단부 페이징 처리
	private int totalCount;		//총 글 개수
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	//페이지 블럭 수
	private int displayPageNum = 5;
	
	//상단부 페이징 정보 가져오기
	public void setCri(Criteria cri) {
		log.info("criteria : {}", cri);
		this.cri = cri;
	}
	
	//현재 클럽 수
	public void setTotalCount(int totalCount) {
		log.info("totalCount : {}", totalCount);
		this.totalCount = totalCount;
		
		calData();
	}
	
	//페이징 처리에 필요한 정보계산
	public void calData() {
		// 페이지 블럭 마지막 페이지 = 반올림(페이지 시작 번호()/페이지 블럭수) * 페이지 블럭 수
		// 반올림(13/10) => 2 * 10 = 20
		// 내가 13페이지에 있으면 페이지 블럭의 마지막 페이지는 20
		endPage = (int)(Math.ceil(cri.getPage()/(double)displayPageNum) * displayPageNum);
		log.info("endPage : {}", endPage);
		// 페이지 블럭 시작페이지 = 20 -10
		// 13페이지에 있으면 (endPage)20 - 10 = 10 
		startPage = (endPage - displayPageNum)+1;
		
		// 마지막 페이지 = (총 글 개수 / 페이지 크기) 반올림
		int tmpEndPage = (int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
		
		//페이지 블력의 마지막 페이지가 실제 마지막페이지보다 크면 실제 마지막 페이지가 endPage
		if(endPage > tmpEndPage) {
			endPage = tmpEndPage;
		}
		//1페이지면 prev 없음
		prev = startPage ==1? false:true ;
		//마지막페이지 * 페이지당 글 개수 가 총 글 개수보다 크면 next 없음
		next = endPage *cri.getPerPageNum() >= totalCount? false:true ;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
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

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public String toString() {
		return "PageMaker [cri=" + cri + ", totalCount=" + totalCount + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prev=" + prev + ", next=" + next + ", displayPageNum=" + displayPageNum + "]";
	}
	
	

}
