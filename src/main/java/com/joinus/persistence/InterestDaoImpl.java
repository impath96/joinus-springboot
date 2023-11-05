package com.joinus.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joinus.domain.InterestsVo;

@Repository
public class InterestDaoImpl implements InterestDao{
	
	@Autowired
	SqlSession sqlSession;
	
	private static final Logger log = LoggerFactory.getLogger(InterestDaoImpl.class);

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
