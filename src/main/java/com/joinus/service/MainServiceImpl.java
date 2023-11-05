package com.joinus.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.joinus.domain.ClubsVo;
import com.joinus.persistence.MainDao;

@Service
public class MainServiceImpl implements MainService {

	@Inject
	private MainDao dao;
	
	
	@Override
	public List<ClubsVo> getMostPopularClub() {
		return dao.getMostPopularClub();
	}

	@Override
	public List<ClubsVo> getMostRecentClub() {
		return dao.getMostRecentClub();
	}

	@Override
	public List<ClubsVo> getMostNumerousClub() {
		return dao.getMostNumerousClub();
	}

	@Override
	public List<ClubsVo> getMyClubs(String location) {
		return dao.getMyClubs(location);
	}

	@Override
	public List<ClubsVo> getMyInterestClub(Integer num) {
		return dao.getMyInterestClub(num);
	}

}
