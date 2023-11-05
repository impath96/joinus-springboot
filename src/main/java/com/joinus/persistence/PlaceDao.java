package com.joinus.persistence;

import java.util.List;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.PlacesVo;

public interface PlaceDao {
	
	// 제휴시설 리스트(전체)
	public List<PlacesVo> getPlaceList();
	
	// 부산 (구) 리스트
	public List<LocationCityVo> getBusanGuList();
	
	// (구) 별 제휴시설리스트
	public List<PlacesVo> getCityPlaceList(String place_address);
	
	// 제휴시설 상세보기
	public PlacesVo getPlaceContent(int place_no);
	
}
