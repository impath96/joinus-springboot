package com.joinus.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.joinus.domain.Criteria;
import com.joinus.domain.MembersVo;
import com.joinus.domain.MyClubDto;

public interface MemberService {
	
	// 회원가입
	public MembersVo join(MembersVo member) throws NoSuchAlgorithmException;
	
	// 이메일로 회원찾기
	public MembersVo findMemberByEmail(String member_email);
	
	// 회원번호로 회원찾기
	public MembersVo findMemberByNo(int member_no);
	
	// 이미지 변경
	public void updateImage(String savedFileName, int member_no);
	
	// 로그인
	public MembersVo signIn(String email, String password);
	
	// 관심사 등록
	public void addInterest(int member_no, int interest);
	
	// 패스워드 변경
	public MembersVo resetPassword(int member_no, String newPassword);
	
	// 닉네임 변경
	public void updateName(String memberName, int member_no);
	
	// 내 클럽 목록
	public List<MyClubDto> getMyClubList(int member_no);
	
	// 모든 회원 목록 - 관리자 
	public List<MembersVo> findMemberAll(Criteria cri);
	
	// 회원수
	public int getTotalCount();
	
	// 회원 삭제 - 관리자
	public void deleteMember(List<Integer> idList);

	public void updateLocation(String parsedLocation, int member_no);

	public MembersVo addLocation(String locationName, int member_no);

}
