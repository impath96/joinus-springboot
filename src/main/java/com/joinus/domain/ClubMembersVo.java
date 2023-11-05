package com.joinus.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubMembersVo {
	
	private int club_member_no; 
	private int club_no; 
	private int member_no;
	private Date club_member_regdate;
	private String club_member_role;
	
}
