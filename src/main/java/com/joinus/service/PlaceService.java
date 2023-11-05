package com.joinus.service;

import java.util.List;

import org.json.simple.JSONArray;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.PlacesVo;

public interface PlaceService {
	
//	public JSONArray placeList();
	
	// 시설 목록(비제휴)
	public List<PlacesVo> getPlaceList();
	
	// 부산 (구) 리스트
	public List<LocationCityVo> getBusanGuList();
	
	// (구) 별 제휴시설리스트
	public List<PlacesVo> getCityPlaceList(String place_address);
	
	// 제휴시설 상세보기
	public PlacesVo getPlaceContent(int place_no);
	
	// 원데이클래스 리스트 크롤링
	public JSONArray classList();

	// 원데이클래스 본문 크롤링
//	public JSONArray classContent();
	
	
	
}
