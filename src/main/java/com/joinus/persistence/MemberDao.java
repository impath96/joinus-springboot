package com.joinus.persistence;


import java.util.List;

import com.joinus.domain.Criteria;
import com.joinus.domain.MembersVo;
import com.joinus.domain.MyClubDto;

public interface MemberDao {
	// 이메일로 회원 찾기
	public MembersVo selectMemberByEmail(String member_email);
	
	// 회원번호로 회원 찾기
	public MembersVo selectMemberByNo(Integer member_no);
	
	//회원 등록(회원가입)
	public void insertMember(MembersVo member);

	// 이미지 변경(URL)
	public void updateImage(String savedFileName, int member_no);
	
	// 이메일과 비밀번호로 회원 찾기(로그인시)
	public MembersVo selectMember(String email, String password);
	
	// 관심사 등록
	public void insertMemberInterest(int member_no, int interest);
	
	// 비밀번호 변경
	public MembersVo updatePassword(int member_no, String encryptedPassword);
	
	// 이름 변경
	public void updateName(String memberName, int member_no);
	
	// 내가 참여하고 있는 모든 모임 출력
	public List<MyClubDto> myClubList(int member_no);

	// 모든 회원 목록 
	public List<MembersVo> getMemberAll(Criteria cri);

	public int getTotalCount();

	public void deleteMember(List<Integer> idList);

	public void updateLocation(String parsedLocation, int member_no);

}
