package com.joinus.persistence;

import com.joinus.domain.InterestsVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InterestDaoImpl implements InterestDao{
	
	private final SqlSession sqlSession;

	private static final String NAMESPACE ="com.joinus.mapper.InterestMapper";

	@Override
	public List<InterestsVo> selectInterestAll() {
		
		return sqlSession.selectList(NAMESPACE+".selectInterestAll");
	}
	
	// 특정 회원의 관심사 조회
	@Override
	public InterestsVo selectInterestByMemberNo(int member_no) {
		log.info("DAO - 특정 회원의 관심사 조회");
		InterestsVo interestsVo = sqlSession.selectOne(NAMESPACE+".selectInterestByMemberNo", member_no);
		log.info("DB에서 찾은 {} 회원의 관심사 : {}", member_no, interestsVo);
		return interestsVo;
	}

}
