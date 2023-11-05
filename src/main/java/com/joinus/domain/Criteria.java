package com.joinus.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Criteria {

	private static final Logger log = LoggerFactory.getLogger(Criteria.class);
	
	//페이징 처리
	private int page;			//페이지 시작 번호
	private int perPageNum;		//페이지 크기(한 페이지 안에 글 개수)
	
	//기본 생성자(설정안하면 1페이지 시작 & 10개씩 보여줌)
	public Criteria() {
		this.page = 1;
		this.perPageNum = 6;
	}
	
	//변수로 사용
	//페이지 시작번호가 0보다 작거나 같으면 시작 번호를 1로 지정
	// 0보다 크면 그 값을 그대로 사용
	public void setPage(int page) {
		//log.info("------------setPage : "+page+"페이지------------");
		if(page <= 0) {
			this.page=1;
			return ;
		}
		this.page = page;
	}
	
	//페이지 크기가 0보다 작거나 100보다 크면 페이지 크기를 10으로 지정
	public void setPerPageNum(int perPageNum) {
		//log.info("------------setPerPageNum : "+perPageNum+"개------------");
		if(perPageNum <= 0 || perPageNum >100) {
			this.perPageNum = 6;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	//mapper에서 사용될 메서드
	
	//페이지 시작 글 번호 = 현재페이지 -1 * 페이지 크기
	//2페이지 >> 2-1*10 = 10번째 글 부터 시작
	public int getPageStart() {
		//log.info("-------------getPageStart-------------------------------");
		return (this.page -1) * perPageNum;
	}
	
	//
	public int getPage() {
		//log.info("-------------getPage-------------------------------");
		return page;
	}
	
	public int getPerPageNum() {
		//log.info("-------------getPerPageNum-------------------------------");
		return perPageNum;
	}

	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}

	
	
}
