package com.joinus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubInterestsVo {

	private int club_interest_no; 
	private int club_no; 
	private int interest_no; 
	private int interest_detail_no;
	
}
