package com.joinus.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joinus.domain.Criteria;
import com.joinus.domain.MembersVo;
import com.joinus.domain.MyClubDto;
import com.joinus.persistence.MemberDao;
import com.joinus.util.SHA256;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDao memberDao;


	// 회원가입 처리
	@Override
	@Transactional
	public MembersVo join(MembersVo member) throws NoSuchAlgorithmException {
		// 1) 먼저 회원을 등록
		//  -1) 만약 소셜 로그인 회원일 경우 비밀번호를 설정
		//  -2) 일반 이메일 회원가입 회원일 경우 이미지 URL, 회원가입 타입 설정(default.jpg, 'common')
		String password = "";
		
		if (member.getMember_signup_type() == null) {
			member.setMember_image("default.jpg");
			member.setMember_signup_type("joinus");
			password = SHA256.encrypt(member.getMember_pass());
		} else {
			password = SHA256.encrypt(UUID.randomUUID().toString());
		}
		member.setMember_pass(password);
		
		memberDao.insertMember(member);

		// 2) 등록된 회원 다시 꺼내오고(session에 회원번호 저장하기 위해서)
		MembersVo selectMember = memberDao.selectMemberByEmail(member.getMember_email());
		// log.info("꺼내온 member : {}", selectMember);

		return selectMember;
	}

	// 회원 이메일로 회원 찾기
	@Override
	public MembersVo findMemberByEmail(String member_email) {
		
		MembersVo findMember = memberDao.selectMemberByEmail(member_email);
		
		return findMember;
	}

	// 회원 번호로 회원 찾기
	@Override
	public MembersVo findMemberByNo(int member_no) {

		MembersVo findMember = memberDao.selectMemberByNo(member_no);

		return findMember;
	}

	// 이미지 변경 
	@Override
	public void updateImage(String savedFileName, int member_no) {
		
		memberDao.updateImage(savedFileName, member_no);
	}

	// 이메일과 패스워드를 통해 로그인 처리
	@Override
	public MembersVo signIn(String email, String password) {

		// 패스워드를 SHA-256으로 변환
		String encryptedPassword = "";
		
		try {
			encryptedPassword = SHA256.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// log.info("암호화된 패스워드: "+encryptedPassword);
		MembersVo member = memberDao.selectMember(email, encryptedPassword);
			
		return member;
	}

	@Override
	public void addInterest(int member_no, int interest) {

		memberDao.insertMemberInterest(member_no, interest);
		
	}

	@Override
	public MembersVo resetPassword(int member_no, String newPassword) {
		
		// 새로 전달받은 비밀번호 암호화
		String encryptedPassword = "";
		try {
			encryptedPassword = SHA256.encrypt(newPassword);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MembersVo member = memberDao.updatePassword(member_no, encryptedPassword);
		
		return member;
	}

	@Override
	public void updateName(String memberName, int member_no) {
		memberDao.updateName(memberName, member_no);
		
	}

	@Override
	public List<MyClubDto> getMyClubList(int member_no) {
		
		List<MyClubDto> list = memberDao.myClubList(member_no);
		
		return list;
	}

	// 모든 회원 목록 출력
	@Override
	public List<MembersVo> findMemberAll(Criteria cri) {
		
		return memberDao.getMemberAll(cri);
	}

	@Override
	public int getTotalCount() {
		
		return memberDao.getTotalCount();
	}

	@Override
	public void deleteMember(List<Integer> idList) {

		memberDao.deleteMember(idList);
	}

	@Override
	public void updateLocation(String parsedLocation, int member_no) {
		
		memberDao.updateLocation(parsedLocation, member_no);
	}

	@Override
	public MembersVo addLocation(String locationName, int member_no) {
		
		String parsedLocation = parseLocation(locationName);
		memberDao.updateLocation(parsedLocation, member_no);
		
		MembersVo member = memberDao.selectMemberByNo(member_no);
		
		return member;
	}
	
	// 다른 곳에서 동일한 기능 사용 -> 추후 Util 클래스로 분리
	private String parseLocation(String location_name) {
		
		String[] part = location_name.split(" ");
		String savedLocation = part[0];
		for (int i = 1; i < 3; i++) {
			savedLocation += " " + part[i];
		}
		return savedLocation;
	}

}
