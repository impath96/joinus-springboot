package com.joinus.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ClubBanMembersVo {

	private int club_ban_member_no; 
	private int club_no; 
	private int member_no; 
	private Date club_member_regdate;
	private Date club_member_bandate;
	
}
