package com.joinus.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ClubBoardsVo {

	private int club_board_no; 
	private int club_no; 
	private int board_type_no;
	private int member_no; 
	private String club_board_title; 
	private String club_board_content; 
	private String club_board_image; 
	private Date club_board_date; 
	private Date club_board_updatedate;
	private int club_board_likecnt;
	private int club_board_commentcnt;
	
}
