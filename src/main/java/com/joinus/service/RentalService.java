package com.joinus.service;

import java.sql.Date;
import java.util.List;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.PartnerPlacesVo;
import com.joinus.domain.PaymentsVo;
import com.joinus.domain.RentalPlacesVo;

public interface RentalService {
	
	// 제휴시설 상세보기
	public PartnerPlacesVo getPartnerPlaceContent(Integer partner_place_no);
	
	// 제휴시설 리스트(전체)
	public List<PartnerPlacesVo> getAllPartnerPlaceList();
	
	// 부산 (구) 리스트
	public List<LocationCityVo> getBusanGuList();
	
	// (구) 별 제휴시설리스트
	public List<PartnerPlacesVo> getCityPartnerPlaceList(String partner_place_address);
	
	// 장소유형, 위치 제휴시설리스트
	public List<PartnerPlacesVo> getPartnerPlaceList(String partner_place_type, String partner_place_address);
	
	// 장소 유형 별 제휴시설리스트
	public List<PartnerPlacesVo> getTypePartnerPlaceList(String partner_place_type);
	
	// 예약 일자/시간 정보 리스트
	public List<RentalPlacesVo> getRentalPlaceDate(Integer partner_place_no);
	
	// 예약 시간 리스트
	public List<Integer> getRentalTime(Date rental_date, Integer partner_place_no);

	// 결제
	public Integer pay(PaymentsVo vo);
	
	// 결제 전 예약정보 저장(일자, 시간)
	public void insertPlaceBeforePay(Date rental_date, Integer rentaltimeno);
	
	// 가장 최근에 저장한 예약정보 가져오기
	public RentalPlacesVo getLatelyRentalPlace();
	
	// 예약정보 개수
	public int getRentalPlaceCnt();
	
	// 결제 후 예약정보 업데이트
	public void updateLatelyRentalPlace(String reservation_no, Integer member_no, Integer partner_place_no, Integer payment_no, Integer rental_places_no);
}
