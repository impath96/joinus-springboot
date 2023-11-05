package com.joinus.persistence;

import java.util.List;

import com.joinus.domain.ClubsVo;

public interface MainDao {
	
	//찜cnt가 제일 많은 리스트 4개
	public List<ClubsVo> getMostPopularClub();
	//가장 최근에 만들어진 모임 4개
	public List<ClubsVo> getMostRecentClub();
	//최근 가장 많은 인원이 있는 모임 4개(최신순)
	public List<ClubsVo> getMostNumerousClub();
	//내 관심사 모임
	public List<ClubsVo> getMyInterestClub(Integer num);
	//내 지역 모임
	public List<ClubsVo> getMyClubs(String location);
}
