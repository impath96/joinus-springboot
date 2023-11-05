package com.joinus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubGradesVo {

	private int club_grade_no; 
	private int club_no; 
	private int member_no; 
	private int club_grade_rate;
	
}
