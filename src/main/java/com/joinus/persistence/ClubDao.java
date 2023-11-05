package com.joinus.persistence;


import java.util.List;
import java.util.Map;

import com.joinus.domain.BoardCommentsVo;
import com.joinus.domain.BoardLikesVo;
import com.joinus.domain.BoardTotalBean;
import com.joinus.domain.ClubBoardsVo;
import com.joinus.domain.ClubGradesVo;
import com.joinus.domain.ClubListDto;
import com.joinus.domain.ClubMeetingsVo;
import com.joinus.domain.ClubMembersVo;
import com.joinus.domain.ClubTotalBean;
import com.joinus.domain.ClubsVo;
import com.joinus.domain.Criteria;
import com.joinus.domain.InterestDetailsVo;
import com.joinus.domain.InterestsVo;
import com.joinus.domain.MeetingMembersVo;
import com.joinus.domain.MeetingTotalBean;
import com.joinus.domain.MembersVo;


public interface ClubDao {
	
	//=========================고은비=========================
	//클럽회원 리스트
	public List<ClubTotalBean> clubMemberList(int club_no);
	
	//클럽 전체 개수 조회
	public Integer getTotalCnt(Integer interest_no);
	public Integer getTotalCnt();
	public int getDetailCnt(Integer interest_detail_no);
	
	//클럽리스트(관심사o)
	public List<ClubTotalBean> clubList(int interest_no, Criteria cri);
	
	//클럽리스트(디테일)
	public List<ClubTotalBean> clubListDetail(Integer interest_detail_no, Criteria cri);
	
	//관심사 디테일
	public List<InterestDetailsVo> interestDetail(Integer interest_no);

	//클럽리스트(관심사x)
	public List<ClubTotalBean> clubList(Criteria cri);
	
	//이달의 모임
	public List<ClubTotalBean> clubListMonth();
	
	//클럽 정보
	public List<ClubsVo> clubInfo(int club_no);
	
	//모임 회원 권한 가져오기
	public Integer clubRole(int club_no, int member_no);
	
	//클럽 강퇴
	public void clubBan(int member_no, int club_no);
	
	//모임장 양도
	public void clubAuth(Integer member_no, int club_no);
	
	//클럽 나가기
	public void clubLeave(MembersVo member, Integer club_no);
	
	//클럽 정보 수정
	public void clubUpdate(ClubsVo clubsvo, Integer club_no);
	
	//예약정보 가져오기 - LIST
	public List<MeetingTotalBean> getRental(int member_no);
	
	//예약정보 가져오기 - REST
	public List<MeetingTotalBean> getRentalREST(int rental_places_no);
	
	//정모 만들기
	public void createMeeting(ClubMeetingsVo vo);
	
	//정모 정보 가져오기
	public List<ClubMeetingsVo> getMeetings_no(Integer club_meeting_no);
	
	//정모 참가 
	public int getMeetingMemberStatus(Integer club_meeting_no, int member_no);

	//정모 수정하기
	public Integer updateMeeting(Integer club_meeting_no, ClubMeetingsVo vo);
	
	//정모 삭제하기
	public void deleteMeeting(Integer club_meeting_no);
	
	//정모 주소 가져오기
	public String getMeetingAddr(int club_meeting_no);
	
	//정모 인원
	public List<MeetingTotalBean> getMeetingMember(Integer club_meeting_no, Integer club_no);
	
	//정모상태
	public String getMeetingStatus(Integer club_meeting_no);
	
	//정모상태 수정
	public String updateMeetingStatus(Integer club_meeting_no, String club_meeting_status);
	
	//정모 리스트
	public List<ClubMeetingsVo> getMeetingList(Integer club_no, String status);
	
	//정모 참가
	public void joinMeeting(Integer club_meeting_no, Integer club_no, Integer member_no);

	//정모 참가 취소
	public void cancelMeeting(Integer club_meeting_no, Integer club_no, Integer member_no);
	
	//정모 날짜 확인
	public Integer checkMeetingDate(Integer club_no);
	
	//정모 참가 인원
	public Integer getMeetingMemberCnt(Integer club_meeting_no);
	
	//=========================고은비=========================
	
	
	//=========================허수빈========================
	// 글쓰기
	public void writeBoard(ClubBoardsVo vo);
	
	// 모임고유값에 따른 게시글리스트
	public List<BoardTotalBean> getBoardListAll(Integer club_no, Criteria cri);
	
	public Integer getTotalBoardCnt(Integer club_no);
	
	public List<BoardTotalBean> getBoardList(Integer club_no, Integer board_type_no, Criteria cri);
	
	public Integer getTypeBoardCnt(Integer club_no, Integer board_type_no);
	
	public List<ClubBoardsVo> getBoardImageList(Integer club_no);
	
	// 게시글 본문
//	public ClubBoardsVo getBoardContent(Integer club_board_no);
	public BoardTotalBean getBoardContent(Integer club_board_no);
	
	// 게시글 수정
	public void modifyBoardContent(ClubBoardsVo vo);
	
	// 게시글 삭제
	public void deleteBoard(Integer club_board_no);
	
	// 댓글 등록
	public void writeComment(BoardCommentsVo vo);
	
	// 댓글수
	public int getCommentCnt(Integer club_board_no);
	
	// 댓글 출력
	public List<BoardTotalBean> getCommentList(Integer club_board_no);
	
	// 댓글수 + 1
	public void updateCommentCnt(Integer club_board_no);
	
	// 댓글 등록
	public void updateComment(BoardCommentsVo vo);
	
	// 댓글 삭제
	public void deleteComment(Integer board_comment_no);
	
	// 댓글수 - 1
	public void decreaseCommentCnt(Integer club_board_no);
	
	// 좋아요수
	public int getLikeCnt(Integer club_board_no);
	
	// 좋아요 눌렀는지 확인(1:좋아요O / 0:좋아요X)
	public int checkLike(Integer club_board_no, Integer member_no);
	
	// 좋아요 멤버 리스트
	public List<BoardTotalBean> getLikeList(Integer club_board_no);
	
	// 좋아요 등록
	public void insertLike(BoardLikesVo vo);
	
	// 좋아요수 + 1
	public void increaseLikeCnt(Integer club_board_no);
	
	// 좋아요 취소
	public void cancelLike(Integer club_board_no, Integer member_no);
	
	// 좋아요수 - 1
	public void decreaseLikeCnt(Integer club_board_no);
	
	// 모임원이 맞는지 확인(1:모임가입O / 0:모임가입X)
	public int checkClubMember(Integer club_no, Integer member_no);
	
	// 모임장인지 확인(0보다 크면 모임장)
	public int checkClubAdmin(Integer member_no);
	
	// 모임장 주소
	public String getClubAdminAddr(Integer member_no);
	
	//=========================허수빈========================

	//=========================김민호========================
	
	// 내 모임 리스트
	public List<ClubsVo> ClubListByMemberNo(int member_no);

	// 내 모임 리스트에 개수 제한 
	List<ClubsVo> ClubListByMemberNo(int member_no, int limit);

	// 내가 만든 모임 리스트
	public List<ClubsVo> myClubList(int member_no);

	public List<ClubsVo> myClubList(int member_no, int limit);
	
	// 관리자 페이지 - 모임 리스트
	public List<ClubListDto> clubListForAdmin(Criteria cri);
	
	
	//=========================김민호========================
	
	
	
	//=========================강성민========================
	
	
	// 회원정보
	public MembersVo getMember(Integer num);
	// 회원관심사
	public InterestsVo interest(Integer num);
	// 벤당한 회원번호 가져오기
	public List<Integer> getBanMember(Integer num);
	
	// 세부관심사리스트 가져오기
	public List<InterestDetailsVo> getDetailName(Integer num);
		
	// 회원이 선택한 관심사 넘버값 가져오기
	public InterestDetailsVo getInterestNo(String name);
	
	// 모임 이름 중복체크
	public ClubsVo checkClubName(String name);
	
	// 모임생성
	public void newClub(ClubsVo vo);
	// 모임관심사 저장
	public void newClubInterest(Integer club_no, Integer interest_no,Integer interest_detail_no);
	// 모임가입하기
	public void join(ClubMembersVo members);
	// 모임별점주기
	public void clubGrade(ClubGradesVo vo);
	// 모임 별점 정보 가져오기
	public List<ClubGradesVo> getClubGrade(Integer num);
	// 모임 별점 참여자 가져오기
	public Integer getGradeinfo(Integer num, Integer num2);
	// 모임 별점 평균값, 참여자 수 가져오기
	public List<Map<String, Integer>> getClubAvgCnt(Integer num);
		
	// 모임 정보 가져오기
	public ClubsVo getClubInfo(Integer num);
	// 모임 관심사 가져오기
	public String getClubInterestDName(Integer num);
	// 모임 회원 정보 가져오기(리스트)
	public List<ClubMembersVo> getClubMembers(Integer num);
	// 모임 회원 정보 가져오기(특정)
	public ClubMembersVo getClubMemberNo(Integer num,Integer num2);
	// 모임 회원수 가져오기
	public Integer clubMemberCount(Integer num);
	
	
	// 모임 찜하기
	public void clubDip(Integer num,Integer num2);
	// 모임 찜 여부 확인
	public List<Integer> dip(Integer num);
	// 모임 찜 취소
	public void dipX(Integer num,Integer num2);
	
	// 정모 리스트
	public List<ClubMeetingsVo> getMeetings(Integer num);
	
	// 정모 참석하기
	public void joinMeeting(MeetingMembersVo vo);
	// 정모 참석취소
	public void outMeeting(MeetingMembersVo vo);
	// 정모 참석여부확인
	public List<MeetingMembersVo> checkMeetingMember(Integer num,Integer num2);
	// 정모 참석 인원수 리스트
	public List<Map<String, Integer>> MeetingMemberCnt(Integer num);
	// 정모 참석 인원수 특정
	public List<Map<String, Integer>> getMemberCapa(Integer num,Integer num2);
	
	
	//게시글 리스트
	public List<ClubBoardsVo> getBoards(Integer num);

	public List<ClubsVo> recentViewClubList(List<Integer> recentViewClub);







	
	



	

	
	//=========================강성민========================
	
	
	
}
