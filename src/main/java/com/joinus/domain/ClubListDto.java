package com.joinus.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ClubListDto {
	
	private int club_no;					// 모임 번호
	private String club_name;			// 모임 이름
	private String club_location;		// 모임 주활동지역
	private int club_capacity;			// 모임 정원
	private Date club_regdate;
	private int total;	// 모임 회원수
	private int member_no;				// 모임장 회원 번호
	private String member_name;			// 모임장 이름

}
