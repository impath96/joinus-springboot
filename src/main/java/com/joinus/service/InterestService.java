package com.joinus.service;

import com.joinus.domain.InterestsVo;
import java.util.List;

public interface InterestService {
	
	// 주 관심사 전체 조회
	List<InterestsVo> selectInterestAll();
	
	// 특정 회원의 관심사 조회
	InterestsVo selectInterestByMemberNo(int member_no);

}
