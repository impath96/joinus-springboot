package com.joinus.domain;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ClubMeetingsVo {
	
	private int club_meeting_no; 
	private int club_no; 
	private String club_meeting_title; 
	private int club_meeting_capacity;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private String club_meeting_date; 

	
	private String club_meeting_location; 
	private int club_meeting_dues;
	private String club_meeting_address;
	private String club_meeting_content;
	private String club_meeting_status;

}
