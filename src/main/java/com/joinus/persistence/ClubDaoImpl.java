package com.joinus.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

@Repository
public class ClubDaoImpl implements ClubDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	static final String NAMESPACE ="com.joinus.mapper.ClubMapper";
	static final String NAMESPACE2 ="com.joinus.mapper.MeetingMapper";
	
	private static final Logger log = LoggerFactory.getLogger(ClubDaoImpl.class);
	
	//클럽 멤버 조회
	@Override
	public List<ClubTotalBean> clubMemberList(int club_no) {
		
		return sqlSession.selectList(NAMESPACE+".ClubMemberList", club_no);
	}
	
	//클럽 수(관심사별) 조회
	@Override
	public Integer getTotalCnt(Integer interest_no) {
		
		return sqlSession.selectOne(NAMESPACE+".CountClub", interest_no);
	}
	
	//전체 클럽 수 조회
	@Override
	public Integer getTotalCnt() {
		
		return sqlSession.selectOne(NAMESPACE+".CountClub");
	}
	
	//클럽 수(디테일)
	@Override
	public int getDetailCnt(Integer interest_detail_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+".CountClubDetail");
	}

	//클럽 리스트(관심사별) 조회
	@Override
	public List<ClubTotalBean> clubList(int interest_no, Criteria cri) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("interest_no", interest_no);
		param.put("cri", cri);
		
		log.info(param+"");
		
		List<ClubTotalBean> result = sqlSession.selectList(NAMESPACE+".ClubList", param);
		
		//log.info(result+"");
		
		return result;
		
	}
	
	//클럽 리스트(디테일)
	@Override
	public List<ClubTotalBean> clubListDetail(Integer interest_detail_no, Criteria cri) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("interest_detail_no", interest_detail_no);
		param.put("cri", cri);
		
		log.info(param+"");
		
		List<ClubTotalBean> result = sqlSession.selectList(NAMESPACE+".ClubListDetail", param);
		
		log.info(result+"");
		
		return result;
	}
	
	

	@Override
	public List<ClubTotalBean> clubListMonth() {
		List<ClubTotalBean> result = sqlSession.selectList(NAMESPACE+".ClubListMonth");
		return result;
	}

	//관심사 디테일
	@Override
	public List<InterestDetailsVo> interestDetail(Integer interest_no) {
		List<InterestDetailsVo> reslt = sqlSession.selectList(NAMESPACE+".InterestDetail", interest_no);
		return reslt;
	}

	//전체 클럽 리스트 조회
	@Override
	public List<ClubTotalBean> clubList(Criteria cri) {
		
		//log.info(cri.getPageStart()+"");
		
		List<ClubTotalBean> result = sqlSession.selectList(NAMESPACE+".ClubListAll", cri);
		
		//log.info(result+"");
		
		return result;
	}
	
	//클럽 정보 조회
	@Override
	public List<ClubsVo> clubInfo(int club_no) {
		
		return sqlSession.selectList(NAMESPACE+".ClubInfo", club_no);
	}
	
	//회원 권한 조회
	@Override
	public Integer clubRole(int club_no, int member_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_no", club_no);
		param.put("member_no", member_no);
		log.info("param : " + param);
		
		String role = sqlSession.selectOne(NAMESPACE+".ClubRole",param);
		log.info("role : "+role);

		Integer result = 0;
		if(role == null) {
			result = 3;
		}else if (role.equals("admin")){
			result = 2;
		}else if (role.equals("common")){
			result = 1;
		}
			
		return (Integer)result;
	}
	
	

	@Override
	public int getMeetingMemberStatus(Integer club_meeting_no, int member_no) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_meeting_no", club_meeting_no);
		param.put("member_no", member_no);
		log.info("param : " + param);

		if(sqlSession.selectOne(NAMESPACE2+".getMeetingMemberStatus", param) != null ) {
			return 1;
		}else {
			return 0;
		}
		
	}

	//클럽 강퇴하기
	@Override
	public void clubBan(int member_no, int club_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_no", club_no);
		param.put("member_no", member_no);
		
		log.info(param+"");
		
		List<ClubMembersVo> vanMember = new ArrayList<ClubMembersVo>();
		vanMember=sqlSession.selectList(NAMESPACE+".ClubBan",param);
		log.info("vanMember : " + vanMember);
		sqlSession.insert(NAMESPACE+".VanMemberInsert",vanMember);
		log.info("회원정보 이동 완료");
		sqlSession.delete(NAMESPACE+".ClubMemberBan",param);
		log.info("강퇴완료");
		
	}
	
	//클럽장 양도
	@Override
	public void clubAuth(Integer member_no, int club_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_no", club_no);
		param.put("member_no", member_no);
		
		sqlSession.update(NAMESPACE+".ClubMemberAuth1", param);
		log.info("모임장 권한 삭제");
		sqlSession.update(NAMESPACE+".ClubMemberAuth2", param);
		log.info("새 모임장 생성");
		
	}
	
	//클럽 나가기
	@Override
	public void clubLeave(MembersVo member, Integer club_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("member", member);
		param.put("club_no", club_no);
		
		sqlSession.delete(NAMESPACE+".ClubLeave", param);
		log.info("모임나가기 완료");
		
	}
	
	//클럽 정보 수정
	@Override
	public void clubUpdate(ClubsVo clubsvo, Integer club_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("clubsvo", clubsvo);
		param.put("club_no", club_no);
		
		sqlSession.update(NAMESPACE+".ClubUpdate", param);
		log.info("모임 정보 수정 완료");
		
	}

	//예약정보 가져오기
	@Override
	public List<MeetingTotalBean> getRental(int member_no) {
		
		return sqlSession.selectList(NAMESPACE2+".GetRental", member_no);
	}
	
	//예약정보 가져오기 - REST
	@Override
	public List<MeetingTotalBean> getRentalREST(int rental_places_no) {
		return sqlSession.selectList(NAMESPACE2+".GetRentalREST", rental_places_no);
	}
	
	//정모 생성
	@Override
	public void createMeeting(ClubMeetingsVo vo) {
		sqlSession.insert(NAMESPACE2+".CreateMeeting", vo);
	}
	
	//정모 정보 가져오기
	@Override
	public List<ClubMeetingsVo> getMeetings_no(Integer club_meeting_no) {
		
		return sqlSession.selectList(NAMESPACE2+".GetMeeting_no", club_meeting_no);
	}
	
	//정모 정보 수정
	@Override
	public Integer updateMeeting(Integer club_meeting_no, ClubMeetingsVo vo) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_meeting_no", club_meeting_no);
		param.put("vo", vo);
		
		return sqlSession.update(NAMESPACE2+".UpdateMeeting", param);
	}

	//정모 삭제
	@Override
	public void deleteMeeting(Integer club_meeting_no) {
		
		sqlSession.delete(NAMESPACE2+".DeleteMeetingMember", club_meeting_no);
		log.info("미팅멤버 삭제");
		sqlSession.delete(NAMESPACE2+".DeleteMeeting", club_meeting_no);
		log.info("미팅 삭제");

	}
	
	//정모 주소
	@Override
	public String getMeetingAddr(int club_meeting_no) {
		
		return sqlSession.selectOne(NAMESPACE2+".SelectAddr", club_meeting_no);
	}
	
	//정모 멤버
	@Override
	public List<MeetingTotalBean> getMeetingMember(Integer club_meeting_no, Integer club_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_meeting_no", club_meeting_no);
		param.put("club_no", club_no);
		
		return sqlSession.selectList(NAMESPACE2+".GetMeetingMember", param);
	}
	
	@Override
	public String getMeetingStatus(Integer club_meeting_no) {
		
		return sqlSession.selectOne(NAMESPACE2+".GetMeetingStatus",club_meeting_no);
	}
	
	@Override
	public String updateMeetingStatus(Integer club_meeting_no, String club_meeting_status) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_meeting_no", club_meeting_no);
		param.put("club_meeting_status", club_meeting_status);
		
		return sqlSession.selectOne(NAMESPACE2+".UpdateMeetingStatus",param);
	}
	
	//정모 리스트
	@Override
	public List<ClubMeetingsVo> getMeetingList(Integer club_no, String status) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("status", status);
		param.put("club_no", club_no);
		
		return sqlSession.selectList(NAMESPACE2+".GetMeetingList", param);
	}
	
	//정모 참가
	@Override
	public void joinMeeting(Integer club_meeting_no, Integer club_no, Integer member_no) {
		
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_meeting_no", club_meeting_no);
		param.put("club_no", club_no);
		param.put("member_no", member_no);
		
		sqlSession.update(NAMESPACE2+".InsertMeetingMember", param);
		
	}
	
	@Override
	public void cancelMeeting(Integer club_meeting_no, Integer club_no, Integer member_no) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("club_meeting_no", club_meeting_no);
		param.put("club_no", club_no);
		param.put("member_no", member_no);

		sqlSession.update(NAMESPACE2+".CancelMeetingMember", param);
		
	}
	
	@Override
	public Integer checkMeetingDate(Integer club_no) {
		Integer cnt =  sqlSession.selectOne(NAMESPACE2+".CheckMeetingDate",club_no);
		log.info(cnt+"");
		return cnt;
	}
	

	@Override
	public Integer getMeetingMemberCnt(Integer club_meeting_no) {
		
		return sqlSession.selectOne(NAMESPACE2+".CntMeetingMember", club_meeting_no);
	}
	
//=======================허수빈=============================================================



	@Override
	public void writeBoard(ClubBoardsVo vo) {
		log.info(" write() 호출 ");
		
		// 정보 전달받아서 mapper를 통해 db저장
		sqlSession.insert(NAMESPACE+".writeBoard", vo);
		
	}


	@Override
	public List<BoardTotalBean> getBoardListAll(Integer club_no, Criteria cri) {
		log.info(" getBoardListAll() 호출 ");
		log.info("@@@@@@"+club_no+", "+cri);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("club_no", club_no);
		param.put("pageStart", cri.getPageStart());
		param.put("perPageNum", cri.getPerPageNum());
		
		return sqlSession.selectList(NAMESPACE+".getBoardListAll", param);
	}
	@Override
	public Integer getTotalBoardCnt(Integer club_no) {
		return sqlSession.selectOne(NAMESPACE+".totalBoardCnt", club_no);
	}
	
	
	@Override
	public List<BoardTotalBean> getBoardList(Integer club_no, Integer board_type_no, Criteria cri) {
		log.info(" getBoardList() 호출 ");
		log.info("@@@@@@"+club_no+", "+board_type_no+", "+cri);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("club_no", club_no);
		param.put("board_type_no", board_type_no);
		param.put("pageStart", cri.getPageStart());
		param.put("perPageNum", cri.getPerPageNum());
		
		return sqlSession.selectList(NAMESPACE+".getBoardList", param);
	}
	
	@Override
	public Integer getTypeBoardCnt(Integer club_no, Integer board_type_no) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("club_no", club_no);
		param.put("board_type_no", board_type_no);
		
		return sqlSession.selectOne(NAMESPACE+".typeBoardCnt", param);
	}

	@Override
	public List<ClubBoardsVo> getBoardImageList(Integer club_no) {
		return sqlSession.selectList(NAMESPACE+".getBoardImageList", club_no);
	}

	@Override
	public BoardTotalBean getBoardContent(Integer club_board_no) {
		return sqlSession.selectOne(NAMESPACE+".getBoardContent", club_board_no);
	}

	@Override
	public void modifyBoardContent(ClubBoardsVo vo) {
		sqlSession.update(NAMESPACE+".modifyBoardContent", vo);
	}

	@Override
	public void deleteBoard(Integer club_board_no) {
		sqlSession.delete(NAMESPACE+".deleteBoard", club_board_no);
	}

	@Override
	public void writeComment(BoardCommentsVo vo) {
		sqlSession.insert(NAMESPACE+".writeComment", vo);
	}

	@Override
	public int getCommentCnt(Integer club_board_no) {
		return sqlSession.selectOne(NAMESPACE+".commentCnt", club_board_no);
	}

	@Override
	public List<BoardTotalBean> getCommentList(Integer club_board_no) {
		return sqlSession.selectList(NAMESPACE+".getCommentList", club_board_no);
	}

	@Override
	public void updateCommentCnt(Integer club_board_no) {
		sqlSession.update(NAMESPACE+".updateCommentCnt", club_board_no);
	}

	@Override
	public void updateComment(BoardCommentsVo vo) {
		sqlSession.update(NAMESPACE+".updateComment", vo);
	}

	@Override
	public void deleteComment(Integer board_comment_no) {
		sqlSession.delete(NAMESPACE+".deleteComment", board_comment_no);
	}

	@Override
	public void decreaseCommentCnt(Integer club_board_no) {
		sqlSession.update(NAMESPACE+".decreaseCommentCnt", club_board_no);
	}

	@Override
	public int getLikeCnt(Integer club_board_no) {
		return sqlSession.selectOne(NAMESPACE+".likeCnt", club_board_no);
	}

	@Override
	public int checkLike(Integer club_board_no, Integer member_no) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("club_board_no", club_board_no);
		param.put("member_no", member_no);
		
		return sqlSession.selectOne(NAMESPACE+".checkLike", param);
	}

	@Override
	public List<BoardTotalBean> getLikeList(Integer club_board_no) {
		return sqlSession.selectList(NAMESPACE+".boardLikeList", club_board_no);
	}

	@Override
	public void insertLike(BoardLikesVo vo) {
		sqlSession.insert(NAMESPACE+".insertLike", vo);
	}

	@Override
	public void increaseLikeCnt(Integer club_board_no) {
		sqlSession.update(NAMESPACE+".increaseLikeCnt", club_board_no);
	}

	@Override
	public void cancelLike(Integer club_board_no, Integer member_no) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("club_board_no", club_board_no);
		param.put("member_no", member_no);
		
		sqlSession.delete(NAMESPACE+".cancelLike", param);
	}

	@Override
	public void decreaseLikeCnt(Integer club_board_no) {
		sqlSession.update(NAMESPACE+".decreaseLikeCnt", club_board_no);
	}
	
	@Override
	public int checkClubMember(Integer club_no, Integer member_no) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("club_no", club_no);
		param.put("member_no", member_no);
		
		return sqlSession.selectOne(NAMESPACE+".checkClubMember", param);
	}
	
	@Override
	public int checkClubAdmin(Integer member_no) {
		return sqlSession.selectOne(NAMESPACE+".checkClubAdmin", member_no);
	}
	
	@Override
	public String getClubAdminAddr(Integer member_no) {
		return sqlSession.selectOne(NAMESPACE+".getClubAdminAddr", member_no);
	}
	

	// =========================== 김민호 =============================
	

	// 내 모임 리스트 출력 - limit 제한
	@Override
	public List<ClubsVo> ClubListByMemberNo(int member_no, int limit) {
		log.info("회원 번호가 {} 인 회원의 모임 리스트 출력", member_no);
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("member_no", member_no);
		paramMap.put("limit", limit);
		return sqlSession.selectList(NAMESPACE+".ClubListByMemberNoLimit", paramMap);
	}
	
	// 내 모임 리스트 출력 - 제한 X
	@Override
	public List<ClubsVo> ClubListByMemberNo(int member_no) {
		log.info("회원 번호가 {} 인 회원의 모임 리스트 출력", member_no);
		return sqlSession.selectList(NAMESPACE+".ClubListByMember", member_no);
	}

	@Override
	public List<ClubsVo> myClubList(int member_no) {
		log.info("회원 번호가 {}인 회원이 만든 모임 리스트 출력", member_no);
		
		return sqlSession.selectList(NAMESPACE+".myClubList", member_no);
	}
	
	@Override
	public List<ClubsVo> myClubList(int member_no, int limit) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("member_no", member_no);
		paramMap.put("limit", limit);
		
		return sqlSession.selectList(NAMESPACE+".myClubListLimit", paramMap);
	}
	

	@Override
	public List<ClubListDto> clubListForAdmin(Criteria cri) {
		List<ClubListDto> clubList = sqlSession.selectList(NAMESPACE+".clubListForAdmin", cri);
		return clubList;
	}

	
	
	

	
	// =============================================================================

	
	
	
	//=======================강성민=============================================================
	
	
			//회원정보 가져오기
			@Override
			public InterestsVo interest(Integer num) {
				return sqlSession.selectOne(NAMESPACE+".getMemberInterest",num);
			}
			//회원관심사 가져오기
			@Override
			public MembersVo getMember(Integer num) {
				return sqlSession.selectOne(NAMESPACE+".getMember", num);
			}
			
			//회원이 선택한 관심사의 세부관심사리스트 가져오기
			@Override
			public List<InterestDetailsVo> getDetailName(Integer num) {
				return sqlSession.selectList(NAMESPACE+".getInterestNameDetails", num);
			}

			
			//회원이 입력한 클럽정보 저장하기
			@Override
			public void newClub(ClubsVo vo) {
				sqlSession.insert(NAMESPACE+".createClub", vo);
			}
			//회원이 선택한 관심사 넘버값 가져오기
			@Override
			public InterestDetailsVo getInterestNo(String name) {
				return sqlSession.selectOne(NAMESPACE+".getInterestNo", name);
			}
			//회원이 입력한 클럽관심사 저장하기
			@Override
			public void newClubInterest(Integer club_no, Integer interest_no, Integer interest_detail_no) {
				Map<String, Integer> num = new HashMap<String, Integer>();
				num.put("club_no", club_no);
				num.put("interest_no", interest_no);
				num.put("interest_detail_no", interest_detail_no);
				
				sqlSession.insert(NAMESPACE+".createClubInterest", num);
			}
			
			// 모임 이름 중복체크
			@Override
			public ClubsVo checkClubName(String name) {
				return sqlSession.selectOne(NAMESPACE+".checkClubName", name);
			}
			
			//모임가입하기
			@Override
			public void join(ClubMembersVo members) {
				sqlSession.insert(NAMESPACE+".joinMembers",members);
			}

			//모임정보가져오기
			@Override
			public ClubsVo getClubInfo(Integer num) {
				return sqlSession.selectOne(NAMESPACE+".getClubInfo", num);
			}
			
			//모임 관심사 가져오기
			@Override
			public String getClubInterestDName(Integer num) {
				return sqlSession.selectOne(NAMESPACE+".getClubInterestDName", num);
			}

			//모임회원정보가져오기
			@Override
			public List<ClubMembersVo> getClubMembers(Integer num) {
				return sqlSession.selectList(NAMESPACE+".getClubMember", num);
			}
			// 모임 회원 정보 가져오기(특정)
			@Override
			public ClubMembersVo getClubMemberNo(Integer num, Integer num2) {
				Map<String, Integer> nummm = new HashMap<String, Integer>();
				nummm.put("club_no", num);
				nummm.put("member_no", num2);
				return sqlSession.selectOne(NAMESPACE+".getClubMemberNo", nummm);
			}
			
			// 모임 회원수 가져오기
			@Override
			public Integer clubMemberCount(Integer num) {
				return sqlSession.selectOne(NAMESPACE+".getClubMemberCNT", num);
			}

			//별점주기
			@Override
			public void clubGrade(ClubGradesVo vo) {
				sqlSession.selectList(NAMESPACE+".clubGrade", vo);		
			}
			//별점정보 가져오기
			@Override
			public List<ClubGradesVo> getClubGrade(Integer num) {
				return sqlSession.selectList(NAMESPACE+".getClubGrade", num);
			}
			//별점 평균값,참여자수 가져오기
			@Override
			public List<Map<String, Integer>> getClubAvgCnt(Integer num) {
				List<Map<String, Integer>> list = sqlSession.selectList(NAMESPACE+".getGradeOption", num);
				return list;
			}

			// 모임 별점 참여자 가져오기
			@Override
			public Integer getGradeinfo(Integer num, Integer num2) {
				Map<String, Integer> numm = new HashMap<String, Integer>();
				numm.put("club_no", num);
				numm.put("member_no", num2);
				return sqlSession.selectOne(NAMESPACE+".clubBtn", numm);
			}

			// 모임 찜하기
			@Override
			public void clubDip(Integer num, Integer num2) {
				Map<String, Integer> dipNums = new HashMap<String, Integer>();
				dipNums.put("member_no", num);
				dipNums.put("club_no", num2);
				sqlSession.insert(NAMESPACE+".clubDip", dipNums);
			}
			// 모임 찜 여부 확인
			@Override
			public List<Integer> dip(Integer num) {
				return sqlSession.selectList(NAMESPACE+".dipCheck", num);
			}
			
			// 모임 찜 취소
			@Override
			public void dipX(Integer num, Integer num2) {
				Map<String, Integer> dipx = new HashMap<String, Integer>();
				dipx.put("member_no", num);
				dipx.put("club_no", num2);
				sqlSession.insert(NAMESPACE+".dipX", dipx);
			}


			//정모 리스트
			@Override
			public List<ClubMeetingsVo> getMeetings(Integer num) {
				return sqlSession.selectList(NAMESPACE+".getMeetingList", num);
			}

			//게시글 이미지리스트 가져오기
			@Override
			public List<ClubBoardsVo> getBoards(Integer num) {
				return sqlSession.selectList(NAMESPACE+".getBoardImageList", num);
			}
			
			//정모참여
			@Override
			public void joinMeeting(MeetingMembersVo vo) {
				sqlSession.insert(NAMESPACE2+".joinMeeting", vo);
			}
			// 정모 참석취소
			@Override
			public void outMeeting(MeetingMembersVo vo) {
				sqlSession.delete(NAMESPACE2+".outMeeting", vo);
			}
			// 정모 참석자 리스트
			@Override
			public List<MeetingMembersVo> checkMeetingMember(Integer num,Integer num2) {
				Map<String, Integer> meetingMlist = new HashMap<String, Integer>();
				meetingMlist.put("club_no", num);
				meetingMlist.put("member_no", num2);
				return sqlSession.selectList(NAMESPACE2+".getMeetingMember", meetingMlist);
			}
			// 정모 참석 인원수
			@Override
			public List<Map<String, Integer>> MeetingMemberCnt(Integer num) {
				List<Map<String, Integer>> list = sqlSession.selectList(NAMESPACE2+".getMeetingMemberCnt",num);
				return list;
			}

			
			@Override
			public List<Map<String, Integer>> getMemberCapa(Integer num, Integer num2) {
				Map<String, Integer> numbers = new HashMap<String, Integer>();
				numbers.put("club_meeting_no", num);
				numbers.put("club_no", num2);
				
				List<Map<String, Integer>> map = sqlSession.selectList(NAMESPACE2+".getMemberCapa",numbers);
				
				return map;
			}

			// 벤당한 회원번호 리스트 가져오기
			@Override
			public List<Integer> getBanMember(Integer num) {
				return sqlSession.selectList(NAMESPACE+(".getBanMembers"), num);
			}
			@Override
			public List<ClubsVo> recentViewClubList(List<Integer> recentViewClub) {
				
				return sqlSession.selectList(NAMESPACE+".clubList", recentViewClub);
			}
			


			
			

			
	
	
	
}
	

	
