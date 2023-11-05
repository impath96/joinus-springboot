package com.joinus.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsVo {
	
	private int payment_no;
	private String reservation_no;
	private int member_no;
	private int partner_place_no;
	private int partner_place_price;
	private int payment_price;
	private Date payment_date;
	private int payment_status;
	
	
}
