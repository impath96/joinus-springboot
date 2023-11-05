package com.joinus.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joinus.domain.ClubListDto;
import com.joinus.domain.ClubsVo;
import com.joinus.domain.Criteria;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Slf4j
public class ClubDaoTest {
	
	@Autowired
	ClubDao clubDao;
	
	// 관리자 페이지 - 모임 정보 출력 
	//@Test
	public void clubList() {
		Criteria cri = new Criteria();
		List<ClubListDto> clubList = clubDao.clubListForAdmin(cri);
		
		log.info("clubList : {}", clubList);
		
	}
	
	@Test
	public void recentClubList() {
		
		List<Integer> recentClub = new ArrayList<Integer>();
		recentClub.add(44);
		recentClub.add(45);
		recentClub.add(46);
		recentClub.add(43);
		
		List<ClubsVo> clubList = clubDao.recentViewClubList(recentClub);
		log.info("clubList : {}", clubList);
			
	}

}
