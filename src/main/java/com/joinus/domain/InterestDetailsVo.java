package com.joinus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestDetailsVo {

	private int interest_detail_no; 
	private int interest_no;
	private String interest_detail_name;
	
}
