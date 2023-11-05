package com.joinus.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class RentalPlacesVo {
	
	private int rental_places_no;
	private String reservation_no;
	private int member_no;
	private int partner_place_no;
	private int payment_no;
	private Date rental_date;
	private int rental_time_no;
	private int rental_status;
	
}
