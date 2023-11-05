package com.joinus.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joinus.domain.InterestsVo;
import com.joinus.persistence.InterestDao;


@Service
public class InterestServiceImpl implements InterestService {

	@Autowired
	InterestDao interestDao;
	
	private static final Logger log = LoggerFactory.getLogger(InterestServiceImpl.class);

	@Override
	public List<InterestsVo> selectInterestAll() {
		log.info("interest-service : 주 관심사 전체 조회");
		return interestDao.selectInterestAll();
	}

	@Override
	public InterestsVo selectInterestByMemberNo(int member_no) {
		log.info("selectInterestByMemberNo 메서드 호출");
		log.info("전달받은 회원 번호 : {}", member_no);
		return interestDao.selectInterestByMemberNo(member_no);
	}

}
