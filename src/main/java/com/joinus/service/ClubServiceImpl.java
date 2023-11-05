package com.joinus.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.joinus.persistence.ClubDao;

@Service
public class ClubServiceImpl implements ClubService{
	
	private static final Logger log = LoggerFactory.getLogger(ClubServiceImpl.class);
	
	@Autowired
	private ClubDao dao;
	
	//클럽 멤버 조회
	@Override
	public List<ClubTotalBean> clubMemberListAll(int club_no) {
		//log.info("clubMemberListAll() 호출");
		return dao.clubMemberList(club_no);
	}
	
	//클럽 수(관심사별) 조회
	@Override
	public Integer totalCnt(Integer interest_no) {
		//log.info("clubList-interest_no 호출");
		return dao.getTotalCnt(interest_no);
	}
	
	//전체 클럽 수 조회
	@Override
	public Integer totalCnt() {
		return dao.getTotalCnt();
	}
	

	@Override
	public int totalCntDetail(Integer interest_detail_no) {
		
		return dao.getDetailCnt(interest_detail_no);
	}

	//클럽 리스트(관심사별) 조회
	@Override
	public List<ClubTotalBean> clubList(int interest_no, Criteria cri) {
		
		List<ClubTotalBean> result = dao.clubList(interest_no, cri);
		
		return result;
	}
	
	
	@Override
	public List<ClubTotalBean> clubListDetail(Integer interest_detail_no, Criteria cri) {
		
		List<ClubTotalBean> result = dao.clubListDetail(interest_detail_no, cri);
		
		return result;
	}
	

	@Override
	public List<ClubTotalBean> clubListMonth() {
		
		List<ClubTotalBean> result = dao.clubListMonth();
		
		return result;
	}

	//관심사 디테일 조회
	@Override
	public List<InterestDetailsVo> getInterestDetail(Integer interest_no) {
		
		List<InterestDetailsVo> result = dao.interestDetail(interest_no);
		
		return result;
	}

	//전체 클럽 리스트 조회
	@Override
	public List<ClubTotalBean> clubList(Criteria cri) {
		
		return dao.clubList(cri);
	}
	
	//클럽 정보 조회
	@Override
	public List<ClubsVo> clubInfo(int club_no) {
		
		return dao.clubInfo(club_no);
	}
	
	//모임 회원 권한 조회
	@Override
	public Integer checkClubRole(int club_no, int member_no) {
		
		return dao.clubRole(club_no, member_no);
	}
	
	//클럽 강퇴하기
	@Override
	public void clubBan(int member_no, int club_no) {
		
		dao.clubBan(member_no, club_no);
	}
	
	//클럽장 양도
	@Override
	public void clubAuth(Integer member_no, int club_no) {
		
		dao.clubAuth(member_no, club_no);
	}
	
	//클럽 나가기
	@Override
	public void clubLeave(MembersVo member, Integer club_no) {
		
		dao.clubLeave(member, club_no);
	}
	
	
	//클럽 정보 수정
	@Override
	public void updateClubs(ClubsVo clubsvo, Integer club_no) {
		
		dao.clubUpdate(clubsvo, club_no);
	}

	//예약정보 불러오기 - LIST
	@Override
	public List<MeetingTotalBean> getRental(int member_no) {
		
		return dao.getRental(member_no);
	}
	
	//예약정보 불러오기 - REST
	@Override
	public List<MeetingTotalBean> getRentalREST(int rental_places_no) {
		
		return dao.getRentalREST(rental_places_no);
	}
	
	//정모 생성
	@Override
	public void createMeeting(ClubMeetingsVo vo) {
		
		dao.createMeeting(vo);
	}
	
	//정모 상세
	@Override
	public List<ClubMeetingsVo> getMeeting(Integer club_meeting_no) {

		return dao.getMeetings_no(club_meeting_no);
	}
	
	
	//정모 참석
	@Override
	public int checkMeetingMemberStatus(Integer club_meeting_no, int member_no) {
		
		return dao.getMeetingMemberStatus(club_meeting_no,member_no);
	}

	//정모 수정
	@Override
	public Integer updateMeeting(Integer club_meeting_no, ClubMeetingsVo vo) {
		
		return dao.updateMeeting(club_meeting_no ,vo);
	}
	
	//정모 삭제
	@Override
	public void deleteClubMeeting(Integer club_meeting_no) {
		
		dao.deleteMeeting(club_meeting_no);
	}
	
	//정모 장소
	@Override
	public String getMeetingAddr(int club_meeting_no) {
		
		return dao.getMeetingAddr(club_meeting_no);
	}
	
	//정모 멤버
	@Override
	public List<MeetingTotalBean> getMeetingMember(Integer club_meeting_no, Integer club_no) {
		
		return dao.getMeetingMember(club_meeting_no, club_no);
	}
	//정모 상태
	@Override
	public String getMeetingStatus(Integer club_meeting_no) {
		
		return dao.getMeetingStatus(club_meeting_no);
	}
	//정모 상태 수정
	@Override
	public String updateMeetingStatus(Integer club_meeting_no, String club_meeting_status) {
		
		return dao.updateMeetingStatus(club_meeting_no, club_meeting_status);
	}
	
	//정모 리스트
	@Override
	public List<ClubMeetingsVo> getMeetingList(Integer club_no, String status) {
		
		return dao.getMeetingList(club_no, status);
	}
	
	//정모 참가
	@Override
	public void joinMeeting(Integer club_meeting_no, Integer club_no, Integer member_no) {
		dao.joinMeeting(club_meeting_no,club_no,member_no);
		
	}
	//정모 참석 취소
	@Override
	public void cancelMeeting(Integer club_meeting_no, Integer club_no, Integer member_no) {
		
		dao.cancelMeeting(club_meeting_no,club_no,member_no);
	}
	
	
	@Override
	public Integer checkMeetingDate(Integer club_no) {
		
		return dao.checkMeetingDate(club_no);
	}
	
	
	@Override
	public Integer getMeetingMemberCount(Integer club_meeting_no) {
		// TODO Auto-generated method stub
		return dao.getMeetingMemberCnt(club_meeting_no);
	}
	
	//===================================================================

	
	@Override
	public void writeBoard(ClubBoardsVo vo) {
		dao.writeBoard(vo);
		
	}

	//========================= 김민호 =======================
	


	@Override
	public List<ClubsVo> getClubListByMemberNo(int member_no) {
		return dao.ClubListByMemberNo(member_no);
	}

	@Override
	public List<ClubsVo> getClubListByMemberNo(int member_no, int limit) {
		return dao.ClubListByMemberNo(member_no, limit);
	}

	@Override
	public List<ClubsVo> getMyClubList(int member_no) {
		
		return dao.myClubList(member_no);
	}
	
	@Override
	public List<ClubsVo> getMyClubList(int member_no, int limit) {
		return dao.myClubList(member_no, limit);
	}
	

	@Override
	public List<ClubListDto> getClubListForAdmin(Criteria cri) {
		return dao.clubListForAdmin(cri);
	}

	// ========================================================

//	@Override
//	public List<ClubBoardVo> getBoardListAll(Integer club_no) {
//		return dao.getBoardListAll(club_no);
//	}
	@Override
	public List<BoardTotalBean> getBoardListAll(Integer club_no, Criteria cri) {
		return dao.getBoardListAll(club_no, cri);
	}
	
	@Override
	public Integer getTotalBoardCnt(Integer club_no) {
		return dao.getTotalBoardCnt(club_no);
	}

	@Override
	public List<BoardTotalBean> getBoardList(Integer club_no, Integer board_type_no, Criteria cri) {
		return dao.getBoardList(club_no, board_type_no, cri);
	}
	
	@Override
	public Integer getTypeBoardCnt(Integer club_no, Integer board_type_no) {
		return dao.getTypeBoardCnt(club_no, board_type_no);
	}

	@Override
	public List<ClubBoardsVo> getBoardImageList(Integer club_no) {
		return dao.getBoardImageList(club_no);
	}

	@Override
	public BoardTotalBean getBoardContent(Integer club_board_no) {
		return dao.getBoardContent(club_board_no);
	}

	@Override
	public void modifyBoardContent(ClubBoardsVo vo) {
		dao.modifyBoardContent(vo);
	}

	@Override
	public void deleteBoard(Integer club_board_no) {
		dao.deleteBoard(club_board_no);
	}

	@Override
	public void writeComment(BoardCommentsVo vo) {
		dao.writeComment(vo);
	}

	@Override
	public int getCommentCnt(Integer club_board_no) {
		return dao.getCommentCnt(club_board_no);
	}

	@Override
	public List<BoardTotalBean> getCommentList(Integer club_board_no) {
		return dao.getCommentList(club_board_no);
	}

	@Override
	public void updateCommentCnt(Integer club_board_no) {
		dao.updateCommentCnt(club_board_no);
	}

	@Override
	public void updateComment(BoardCommentsVo vo) {
		dao.updateComment(vo);
	}

	@Override
	public void deleteComment(Integer board_comment_no) {
		dao.deleteComment(board_comment_no);
	}

	@Override
	public void decreaseCommentCnt(Integer club_board_no) {
		dao.decreaseCommentCnt(club_board_no);
	}

	@Override
	public int getLikeCnt(Integer club_board_no) {
		return dao.getLikeCnt(club_board_no);
	}

	@Override
	public int checkLike(Integer club_board_no, Integer member_no) {
		return dao.checkLike(club_board_no, member_no);
	}

	@Override
	public List<BoardTotalBean> getLikeList(Integer club_board_no) {
		return dao.getLikeList(club_board_no);
	}

	@Override
	public void insertLike(BoardLikesVo vo) {
		dao.insertLike(vo);
	}

	@Override
	public void increaseLikeCnt(Integer club_board_no) {
		dao.increaseLikeCnt(club_board_no);
	}

	@Override
	public void cancelLike(Integer club_board_no, Integer member_no) {
		dao.cancelLike(club_board_no, member_no);
	}

	@Override
	public void decreaseLikeCnt(Integer club_board_no) {
		dao.decreaseLikeCnt(club_board_no);
	}
	
	@Override
	public int checkClubMember(Integer club_no, Integer member_no) {
		return dao.checkClubMember(club_no, member_no);
	}
	
	@Override
	public int checkClubAdmin(Integer member_no) {
		return dao.checkClubAdmin(member_no);
	}
	
	@Override
	public String getClubAdminAddr(Integer member_no) {
		return dao.getClubAdminAddr(member_no);
	}
	
	
	
	//=========================강성민========================
	

		//회원정보 가져오기
		@Override
		public MembersVo getMember(Integer num) {
			return dao.getMember(num);
		}
		//회원관심사 가져오기
		@Override
		public InterestsVo getMemberInterest(Integer num) {
			return dao.interest(num);
		}
		//회원이 선택한 관심사의 세부관심사리스트 가져오기
		@Override
		public List<InterestDetailsVo> getDetailName(Integer num) {
			return dao.getDetailName(num);
		}
	
		//회원이 입력한 클럽정보 저장
		@Override
		public void newClub(ClubsVo vo) {
			dao.newClub(vo);
		}
		//회원이 선택한 관심사 넘버값 가져오기
		@Override
		public InterestDetailsVo getInterestNo(String name) {
			return dao.getInterestNo(name);
		}
		//회원이 입력한 클럽관심사 저장하기
		@Override
		public void newClubInterest(Integer club_no, Integer interest_no, Integer interest_detail_no) {
			dao.newClubInterest(club_no, interest_no, interest_detail_no);
		}

		// 모임 이름 중복체크
		@Override
		public ClubsVo checkClubName(String name) {
			return dao.checkClubName(name);
		}
		
		//모임가입하기
		@Override
		public void join(ClubMembersVo members) {
			dao.join(members);
		}
		//모임별점주기
		@Override
		public void clubGrade(ClubGradesVo vo) {
			dao.clubGrade(vo);
		}
		//모임 별점 정보 가져오기
		@Override
		public List<ClubGradesVo> getClubGrade(Integer num) {
			return dao.getClubGrade(num);
		}
		//모임 별점 평균값, 참여자 수 가져오기
		@Override
		public List<Map<String, Integer>> getClubAvgCnt(Integer num) {
			return dao.getClubAvgCnt(num);
		}
		//모임정보가져오기
		@Override
		public ClubsVo getClubInfo(Integer num) {
			return dao.getClubInfo(num);
		}
		//모임 관심사 가져오기
		@Override
		public String getClubInterestDName(Integer num) {
			return dao.getClubInterestDName(num);
		}

		//모임회원정보가져오기
		@Override
		public List<ClubMembersVo> getClubMembers(Integer num) {
			return dao.getClubMembers(num);
		}

		// 모임 회원 정보 가져오기(특정)
		@Override
		public ClubMembersVo getClubMemberNo(Integer num, Integer num2) {
			return dao.getClubMemberNo(num,num2);
		}
		
		// 모임 회원수 가져오기
		@Override
		public Integer clubMemberCount(Integer num) {
			return dao.clubMemberCount(num);
		}
		
		// 모임 별점 참여자 가져오기
		@Override
		public Integer getGradeinfo(Integer num, Integer num2) {
			return dao.getGradeinfo(num,num2);
		}

		// 모임 찜하기
		@Override
		public void clubDip(Integer num, Integer num2) {
			dao.clubDip(num, num2);
		}
		// 모임 찜 여부 확인
		@Override
		public List<Integer> dip(Integer num) {
			return dao.dip(num);
		}

		// 모임 찜 취소
		@Override
		public void dipX(Integer num, Integer num2) {
			dao.dipX(num, num2);
		}

		//정모 리스트
		@Override
		public List<ClubMeetingsVo> getMeetings(Integer num) {
			return dao.getMeetings(num);
		}

		//게시글 리스트
		@Override
		public List<ClubBoardsVo> getBoardsforimg(Integer num) {
			return dao.getBoards(num);
		}

		//정모참여
		@Override
		public void joinMeeting(MeetingMembersVo vo) {
			dao.joinMeeting(vo);
		}
		//정모취소
		@Override
		public void outMeeting(MeetingMembersVo vo) {
			dao.outMeeting(vo);
		}
		//정모참석체크
		@Override
		public List<MeetingMembersVo> checkMeetingMember(Integer num,Integer num2) {
			return dao.checkMeetingMember(num, num2);
		}
		// 정모 참석 인원수
		@Override
		public List<Map<String, Integer>> getMeetingMemberCnt(Integer num) {
			return dao.MeetingMemberCnt(num);
		}
		

		@Override
		public List<Map<String, Integer>> getMemberCapa(Integer num, Integer num2) {
			return dao.getMemberCapa(num, num2);
		}

		// 벤당한 회원번호 리스트 가져오기
		@Override
		public List<Integer> getBanMember(Integer num) {
			return dao.getBanMember(num);
		}

		@Override
		public List<ClubsVo> getClubList(List<Integer> recentViewClub) {
			
			return dao.recentViewClubList(recentViewClub);
		}


		
		
		
}
