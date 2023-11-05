package com.joinus.persistence;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.PartnerPlacesVo;
import com.joinus.domain.PaymentsVo;
import com.joinus.domain.RentalPlacesVo;

@Repository
public class RentalDaoImpl implements RentalDao {
	
	@Inject
	private SqlSession sqlSession;
	
	static final String NAMESPACE = "com.joinus.mapper.RentalMapper";
	

	@Override
	public PartnerPlacesVo getPartnerPlaceContent(Integer partner_place_no) {
		return sqlSession.selectOne(NAMESPACE+".getPlaceContent", partner_place_no);
	}

	@Override
	public List<PartnerPlacesVo> getAllPartnerPlaceList() {
		return sqlSession.selectList(NAMESPACE+".getAllPartnerPlaceList");
	}

	@Override
	public List<LocationCityVo> getBusanGuList() {
		return sqlSession.selectList(NAMESPACE+".getBusanGuList");
	}

	@Override
	public List<PartnerPlacesVo> getCityPartnerPlaceList(String partner_place_address) {
		return sqlSession.selectList(NAMESPACE+".getCityPartnerPlaceList", partner_place_address);
	}

	@Override
	public List<PartnerPlacesVo> getPartnerPlaceList(String partner_place_type, String partner_place_address) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("partner_place_type", partner_place_type);
		param.put("partner_place_address", partner_place_address);
		
		return sqlSession.selectList(NAMESPACE+".getPartnerPlaceList", param);
	}

	@Override
	public List<PartnerPlacesVo> getTypePartnerPlaceList(String partner_place_type) {
		return sqlSession.selectList(NAMESPACE+".getTypePartnerPlaceList", partner_place_type);
	}
	
	@Override
	public List<RentalPlacesVo> getRentalPlaceDate(Integer partner_place_no) {
		return sqlSession.selectList(NAMESPACE+".getRentalPlaceDate", partner_place_no);
	}
	
	@Override
	public List<Integer> getRentalTime(Date rental_date, Integer partner_place_no) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rental_date", rental_date);
		param.put("partner_place_no", partner_place_no);
		
		return sqlSession.selectList(NAMESPACE+".getRentalTime", param);
	}

	@Override
	public Integer pay(PaymentsVo vo) {
		return sqlSession.insert(NAMESPACE+".pay", vo);
	}

	@Override
	public void insertPlaceBeforePay(Date rental_date, Integer rentaltimeno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rental_date", rental_date);
		param.put("rental_time_no", rentaltimeno);
		
		sqlSession.insert(NAMESPACE+".placeBeforePay", param);
	}

	@Override
	public RentalPlacesVo getLatelyRentalPlace() {
		return sqlSession.selectOne(NAMESPACE+".getLatelyRentalPlace");
	}

	@Override
	public int getRentalPlaceCnt() {
		return sqlSession.selectOne(NAMESPACE+".rentalPlaceCnt");
	}

	@Override
	public void updateLatelyRentalPlace(String reservation_no, Integer member_no, Integer partner_place_no,
			Integer payment_no, Integer rental_places_no) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("reservation_no", reservation_no);
		param.put("member_no", member_no);
		param.put("partner_place_no", partner_place_no);
		param.put("payment_no", payment_no);
		param.put("rental_places_no", rental_places_no);
		
		sqlSession.update(NAMESPACE+".updateLatelyRentalPlace", param);
	}
	
	
	
	
}
