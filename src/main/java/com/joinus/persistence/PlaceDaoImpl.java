package com.joinus.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.PlacesVo;

@Repository
public class PlaceDaoImpl implements PlaceDao{
	
	@Inject
	private SqlSession session;
	
	static final String NAMESPACE = "com.joinus.mapper.PlaceMapper";
	
	// 제휴시설 리스트(전체)
	@Override
	public List<PlacesVo> getPlaceList() {
		return session.selectList(NAMESPACE+".getPlaceList");
	}
	
	// 부산 (구) 리스트
	@Override
	public List<LocationCityVo> getBusanGuList() {
		return session.selectList(NAMESPACE+".getBusanGuList");
	}
	
	// (구) 별 제휴시설리스트
	@Override
	public List<PlacesVo> getCityPlaceList(String place_address) {
		return session.selectList(NAMESPACE+".getCityPlaceList");
	}
	
	// 제휴시설 상세보기
	@Override
	public PlacesVo getPlaceContent(int place_no) {
		return session.selectOne(NAMESPACE+".getPlaceContent", place_no);
	}
	
	
	
	
}
