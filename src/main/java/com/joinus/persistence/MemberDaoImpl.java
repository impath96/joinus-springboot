package com.joinus.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.joinus.domain.Criteria;
import com.joinus.domain.MembersVo;
import com.joinus.domain.MyClubDto;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	SqlSession sqlSession;
	private static final String NAMESPACE = "com.joinus.mapper.MemberMapper";
	
	private static final Logger log = LoggerFactory.getLogger(MemberDaoImpl.class);

	@Inject
	public MemberDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public MembersVo selectMemberByEmail(String member_email) {

		return sqlSession.selectOne(NAMESPACE+".selectMemberByEmail", member_email);
	}
	@Override
	public MembersVo selectMemberByNo(Integer member_no) {
		
		return sqlSession.selectOne(NAMESPACE+".selectMemberByNo", member_no);
	}

	@Override
	public void insertMember(MembersVo member) {

		sqlSession.insert(NAMESPACE+".insertMember", member);
	}

	@Override
	public void updateImage(String savedFileName, int member_no) {
		log.info(" 회원 프로필 사진 변경 SQL 실행");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("savedFileName", savedFileName);
		paramMap.put("member_no", member_no);
		
		sqlSession.update(NAMESPACE+".updateImage", paramMap);
	}

	@Override
	public MembersVo selectMember(String email, String password) {
		log.info("이메일과 패스워드를 통해 일치하는 회원 있는지 확인");
		log.info("전달받은 이메일 : {}, 패스워드 : {}", email, password);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("member_email", email);
		paramMap.put("member_pass", password);
		
		// 테스트 하기 쉽게 분리
		MembersVo member = sqlSession.selectOne(NAMESPACE+".selectMember", paramMap);
		log.info("이메일과 패스워드 모두 일치하는 회원 : {}", member);
		return member;
	}

	@Override
	public void insertMemberInterest(int member_no, int interest) {
		log.info("전달받은 회원번호 : {}, 관심사번호 : {}", member_no, interest);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_no", member_no);
		paramMap.put("interest_no", interest);
			
		sqlSession.insert(NAMESPACE+".insertMemberInterest", paramMap);
	}

	@Override
	public MembersVo updatePassword(int member_no, String encryptedPassword) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_no", member_no);
		paramMap.put("member_pass", encryptedPassword);
		
		sqlSession.update(NAMESPACE+".updatePassword", paramMap);
		
		return sqlSession.selectOne(NAMESPACE + ".selectMemberByNo", member_no);
	}

	@Override
	public void updateName(String memberName, int member_no) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_no", member_no);
		paramMap.put("member_name", memberName);
		
		sqlSession.update(NAMESPACE+".updateName", paramMap);
		
	}

	@Override
	public List<MyClubDto> myClubList(int member_no) {
		List<MyClubDto> list = sqlSession.selectList(NAMESPACE+".myClubList", member_no);
		log.info("내가 참여하고 있는 모든 모임 리스트 : {}", list);
		return list;
	}

	@Override
	public List<MembersVo> getMemberAll(Criteria cri) {
		log.info("Criteria : {}", cri);
		List<MembersVo> members = sqlSession.selectList(NAMESPACE+".getMemberAll", cri);
		return members;
	}

	@Override
	public int getTotalCount() {
		int totalCount = sqlSession.selectOne(NAMESPACE+".getTotalCount");
		return totalCount;
	}

	@Override
	public void deleteMember(List<Integer> idList) {

		sqlSession.update(NAMESPACE+".deleteMembers", idList);
	}

	@Override
	public void updateLocation(String parsedLocation, int member_no) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_no", member_no);
		paramMap.put("member_location", parsedLocation);
		sqlSession.update(NAMESPACE+".updateLocation", paramMap);
	}
	

}
