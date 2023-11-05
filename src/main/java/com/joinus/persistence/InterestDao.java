package com.joinus.persistence;

import com.joinus.domain.InterestsVo;
import java.util.List;

public interface InterestDao {
	
	// 주 관심사 모두 출력
	List<InterestsVo> selectInterestAll();

	// 특정 회원 관심사 조회
	InterestsVo selectInterestByMemberNo(int member_no);
	

}
