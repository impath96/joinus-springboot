package com.joinus.service;

import com.joinus.domain.InterestsVo;
import com.joinus.persistence.InterestDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

	private final InterestDao interestDao;
	
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
