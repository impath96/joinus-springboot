package com.joinus.controller;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.select.Evaluator.IsEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joinus.domain.BoardCommentsVo;
import com.joinus.domain.BoardLikesVo;
import com.joinus.domain.ClubBoardsVo;
import com.joinus.domain.ClubGradesVo;
import com.joinus.domain.ClubMeetingsVo;
import com.joinus.domain.ClubMembersVo;
import com.joinus.domain.ClubTotalBean;
import com.joinus.domain.ClubsVo;
import com.joinus.domain.Criteria;
import com.joinus.domain.InterestDetailsVo;
import com.joinus.domain.InterestsVo;
import com.joinus.domain.MeetingMembersVo;
import com.joinus.domain.MeetingTotalBean;
import com.joinus.domain.MemberDipsVo;
import com.joinus.domain.MembersVo;
import com.joinus.domain.PageMaker;
import com.joinus.service.ClubService;


@Controller
@RequestMapping("/club/*")
public class ClubController {
	
	@Autowired
	private ClubService service;
	
	private static final Logger log = LoggerFactory.getLogger(ClubController.class);
	

	//http://localhost:8088/club/clubList?page=1
	//http://localhost:8088/club/clubList?interest_no=2
	@RequestMapping(value="/clubList", method = RequestMethod.GET)
	public String clubList(@ModelAttribute("interest_no") String interest_no,
						Criteria cri, Model model,HttpSession session, RedirectAttributes rttr) {
		log.info("interest_no : "+interest_no);	
		
		List<ClubTotalBean> clubMonthList = service.clubListMonth();
		log.info("clubMonthList : "+clubMonthList);
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			}
		
		if(interest_no.isEmpty()) {
			model.addAttribute("clubList", service.clubList(cri));
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.totalCnt());
			log.info(pageMaker+"");
			model.addAttribute("pm", pageMaker);
			model.addAttribute("interest_no", 0);
			model.addAttribute("clubMonthList", clubMonthList);
			rttr.addFlashAttribute("check","ClubList");
			log.info("clubList() 호출");
			
		}else{
			
			model.addAttribute("clubList", service.clubList(Integer.parseInt(interest_no),cri));
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.totalCnt(Integer.parseInt(interest_no)));
			model.addAttribute("interest_no",Integer.parseInt(interest_no));
			model.addAttribute("clubMonthList", clubMonthList);
			log.info(pageMaker+"");
			model.addAttribute("pm", pageMaker);
			rttr.addFlashAttribute("check","ClubList");
			log.info("clubList(no) 호출");
			
		}
		
		return "club/clubList";
		
	}
	
	//http://localhost:8088/club/1/clubMembers
	@RequestMapping(value="/{club_no}/clubMembers", method = RequestMethod.GET)
	public String clubMember(Model model, 
							@PathVariable("club_no") Integer club_no, HttpSession session) throws Exception{
		log.info("clubMember() 호출");		
		
		//비회원
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			
			Integer member_no =member.getMember_no();
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		List<ClubsVo> clubInfo = service.clubInfo(club_no);
		
		log.info("result : "+result);
		model.addAttribute("clubInfo", clubInfo);
		model.addAttribute("club_no",club_no);
		model.addAttribute("clubMemberList",service.clubMemberListAll(club_no));
		model.addAttribute("result", result);
		
		return "club/clubMembers";
	}
	
	//http://localhost:8088/club/1/clubAdmin
	@RequestMapping(value="/{club_no}/clubAdmin", method = RequestMethod.GET)
	public String clubAdmin(Model model, 
			@PathVariable("club_no") Integer club_no, HttpSession session) throws Exception{
		log.info("clubAdmin() 호출");		
		
		//비회원
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			
			Integer member_no =member.getMember_no();
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		List<ClubsVo> clubInfo = service.clubInfo(club_no);
		
		log.info("result : "+result);
		model.addAttribute("clubInfo", clubInfo);
		model.addAttribute("club_no",club_no);
		model.addAttribute("clubMemberList",service.clubMemberListAll(club_no));
		model.addAttribute("result", result);
		
		return "club/clubAdmin";
	}
	
	
	//http://localhost:8088/club/1/clubMember/ban
	@RequestMapping(value="/{club_no}/clubMembers/ban", method=RequestMethod.GET)
	public String clubMemberBan(@ModelAttribute("member_no") Integer member_no,
			@PathVariable("club_no") Integer club_no,
			RedirectAttributes rttr) {
		
		log.info("ban 호출");
		
		service.clubBan(member_no, club_no);
		log.info("ban 완료");
		
		rttr.addFlashAttribute("check","BANOK");
		
		return "redirect:/club/{club_no}/clubMembers";
		
	}
	//클럽 모임장 권한 이동
	@RequestMapping(value="/{club_no}/clubMembers/auth", method=RequestMethod.GET)
	public String clubMemberAuth(@ModelAttribute("member_no") Integer member_no,
			@PathVariable("club_no") Integer club_no,RedirectAttributes rttr) {
		
		log.info("auth 호출");
		service.clubAuth(member_no, club_no);
		
		
		rttr.addFlashAttribute("check","AUTHOK");
		
		return "redirect:/club/{club_no}/clubMembers";
		
	}
	
	//클럽 나가기
	@RequestMapping(value="/{club_no}/leave", method=RequestMethod.GET)
	public String clubLeave(@PathVariable("club_no") Integer club_no,
								HttpSession session, RedirectAttributes rttr) {
		log.info("clubLeave()호출");
		MembersVo member = (MembersVo) session.getAttribute("member");
		log.info(member+"");
		
		Integer member_no =member.getMember_no();
		
		if(service.checkClubRole(club_no, member_no) == 2) {
			rttr.addFlashAttribute("check","LEAVENOT");
			return "redirect:/club/{club_no}/clubMembers";
			
		}else {
			service.clubLeave(member, club_no);
			rttr.addFlashAttribute("check","LEAVEOK");
			return "redirect:/club/{club_no}/clubMembers";
		}
	}
	
	//http://localhost:8088/club/1/modify
		@RequestMapping(value = "/{club_no}/modify", method=RequestMethod.GET)
		public String clubModifyGET(@PathVariable("club_no") Integer club_no,
				HttpSession session, RedirectAttributes rttr, Model model) {
			
			log.info("clubModifyGET 이동");
			
			//비회원
			Integer result = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				Integer member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
			}
			
			List<ClubsVo> clubInfo = service.clubInfo(club_no);
			log.info("club_no : "+ club_no);
			
			model.addAttribute("clubInfo", clubInfo);
			
			return "/club/clubModify";
		}

	//http://localhost:8088/club/1/modify
		@RequestMapping(value = "/{club_no}/modify", method=RequestMethod.POST)
		public String clubModifyPOST(@PathVariable("club_no") Integer club_no,
				HttpSession session, RedirectAttributes rttr, Model model, ClubsVo clubsvo,
				MultipartFile file, HttpServletRequest request, String null_image) throws IOException{
			
			log.info("clubModifyPOST 호출");
			
			//모임 사진등록 
			if(!file.isEmpty()) { 
			
			//가상업로드 폴더 설정
			ServletContext ctx =request.getServletContext();
			String realpath = ctx.getRealPath("/resources/upload/clubs/");
			log.info("파일저장경로: " +realpath);		
			
			//String FileName = file.getOriginalFilename();
			String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			log.info("파일명: "+savedFileName);

			
			String fullpath = realpath;
			fullpath += File.separator + savedFileName;
			File saveFile = new File(fullpath);
			
			file.transferTo(saveFile);
			
			clubsvo.setClub_image(savedFileName);
			log.info("사진저장 완료");
			
			}
			
			if(file.isEmpty()) {
				clubsvo.setClub_image(null_image);
			}
			
			log.info(clubsvo+"");
			
			service.updateClubs(clubsvo, club_no);
		
			return "redirect:/club/{club_no}/clubMembers";
		}
		
		
		
		//http://localhost:8088/club/1/meeting/new
		@RequestMapping(value="/{club_no}/meeting/new", method = RequestMethod.GET)
		public String meetingWritegPOST(Model model,
				@PathVariable("club_no") Integer club_no, HttpSession session) {
			
			log.info("meetingWriteGET() 호출");
			
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			
			Integer member_no =member.getMember_no();
			
			List<MeetingTotalBean> rentalList = (List<MeetingTotalBean>)service.getRental(member_no);
			log.info(rentalList+"");

			List<ClubsVo> clubInfo = service.clubInfo(club_no);
			log.info(clubInfo+"");
			model.addAttribute("clubInfo", clubInfo);
			model.addAttribute("rentalList", rentalList);
			return "/club/meeting/meetingWrite";
			
		}
		
		
		//http://localhost:8088/club/1/meeting/new
		@RequestMapping(value="/{club_no}/meeting/new", method = RequestMethod.POST)
		public String clubMeetingGET(Model model, ClubMeetingsVo vo, RedirectAttributes rttr,
				@PathVariable("club_no") Integer club_no, HttpSession session) {
			log.info("meetingWritePOST() 호출");
			
			log.info(vo+"");
			
			//비회원
			Integer result = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				Integer member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
			}
			
	
			service.createMeeting(vo);
			model.addAttribute("result", result);
			log.info(result+"");
			rttr.addFlashAttribute("check","MeetingNew");
			
			return "redirect:/club/{club_no}";
		}
		
		//http://localhost:8088/club/1/meeting/1
		
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}", method = RequestMethod.GET)
		public String meetingModifyGET(Model model, HttpSession session,
				@PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no) {
			
			log.info("meeting() 호출");
			
			//비회원
			Integer result = 0;
			Integer meetingMemberStatus = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				Integer member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				model.addAttribute("member_no", member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
				log.info("참석여부 체크");
				meetingMemberStatus = service.checkMeetingMemberStatus(club_meeting_no,member_no);
				//meetingMemberStatus = 1 : 참가중
				//meetingMemberStatus = 0 : 미참가
			}
			
			List<ClubMeetingsVo> meetingList = service.getMeeting(club_meeting_no);
			List<MeetingTotalBean> meetingMember = service.getMeetingMember(club_meeting_no, club_no);
			String meetingStatus = service.getMeetingStatus(club_meeting_no);
			Integer meetingMemberCnt = service.getMeetingMemberCount(club_meeting_no);
			
			log.info("result : "+result );
			log.info("meetingMemberStatus : " + meetingMemberStatus);
			log.info("meetingMemberCnt : "+meetingMemberCnt);
			log.info("meetingStatus : "+meetingStatus);
			
			List<ClubsVo> clubInfo = service.clubInfo(club_no);
			//log.info(clubInfo+"");
			model.addAttribute("clubInfo", clubInfo);
			model.addAttribute("meetingList", meetingList);
			model.addAttribute("meetingMember", meetingMember);
			model.addAttribute("result", result);
			model.addAttribute("meetingStatus", meetingStatus);
			model.addAttribute("meetingMemberStatus", meetingMemberStatus);
			model.addAttribute("meetingMemberCnt", meetingMemberCnt);
			return "/club/meeting/meetingContent";
			
		}
		
		//http://localhost:8088/club/1/meeting/1/modify
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/modify", method = RequestMethod.GET)
		public String meetingModifyGET(Model model,
				@PathVariable("club_no") Integer club_no, HttpSession session,
				 @PathVariable("club_meeting_no") Integer club_meeting_no) {
			
			log.info("meetingModifyGET() 호출");
			
			//비회원
			Integer result = 0;
			Integer member_no = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
			}
			
			List<MeetingTotalBean> rentalList = (List<MeetingTotalBean>)service.getRental(member_no);
			log.info(rentalList+"");
			
			List<ClubMeetingsVo> meetingList = service.getMeeting(club_meeting_no);
			log.info(meetingList+"");
			
			List<ClubsVo> clubInfo = service.clubInfo(club_no);
			log.info(clubInfo+"");
			model.addAttribute("clubInfo", clubInfo);
			model.addAttribute("meetingList", meetingList);
			model.addAttribute("rentalList", rentalList);
			model.addAttribute("result", result);
			
			return "/club/meeting/meetingModify";
			
		}

		//http://localhost:8088/club/1/meeting/1/modify
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/modify", method = RequestMethod.POST)
		public String meetingModifyPOST(Model model, @PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, ClubMeetingsVo vo, HttpSession session) {
			
			log.info("meetingModifyPOST() 호출");
			
			//비회원
			Integer result = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				Integer member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
			}
			
			
			service.updateMeeting(club_meeting_no, vo);
			//log.info(meetingList+"");
			
			List<ClubsVo> clubInfo = service.clubInfo(club_no);
			log.info(clubInfo+"");
			model.addAttribute("clubInfo", clubInfo);
			
			return "redirect:/club/{club_no}/meeting/{club_meeting_no}";
			
		}
		
		
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/delete", method = RequestMethod.GET)
		public String meetingDelete(Model model, @PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, HttpSession session) {
			
			log.info("meetingDelete 호출");
			
			//비회원
			Integer result = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				Integer member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
			}
			
			service.deleteClubMeeting(club_meeting_no);
			
			log.info("정모 삭제 완료");
			model.addAttribute("result", result);
			
			return "redirect:/club/{club_no}";
		}
		
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/close", method = RequestMethod.GET)
		public String clubMeetingClose(RedirectAttributes rttr,
				@PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, Model model){
			String club_meeting_status = "마감";
			service.updateMeetingStatus(club_meeting_no,club_meeting_status);
			String meetingStatus = service.getMeetingStatus(club_meeting_no);
			model.addAttribute("meetingStatus", meetingStatus);
			rttr.addFlashAttribute("check", "Close");
			
			return "redirect:/club/{club_no}/meeting/{club_meeting_no}";
		}
		
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/reopen", method = RequestMethod.GET)
		public String clubMeetingReopen(RedirectAttributes rttr,
				@PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, Model model){
			String club_meeting_status = "모집중";
			service.updateMeetingStatus(club_meeting_no, club_meeting_status);
			String meetingStatus = service.getMeetingStatus(club_meeting_no);
			model.addAttribute("meetingStatus", meetingStatus);
			rttr.addFlashAttribute("check", "Reopen");
			
			return "redirect:/club/{club_no}/meeting/{club_meeting_no}";
		}
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/end", method = RequestMethod.GET)
		public String clubMeetingEnd(RedirectAttributes rttr,
				@PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, Model model){
			String club_meeting_status = "완료";
			service.updateMeetingStatus(club_meeting_no, club_meeting_status);
			String meetingStatus = service.getMeetingStatus(club_meeting_no);
			model.addAttribute("meetingStatus", meetingStatus);
			rttr.addFlashAttribute("check", "END");
			
			return "redirect:/club/{club_no}/meeting/{club_meeting_no}";
		}
		
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/join", method = RequestMethod.GET)
		public String clubMeetingJoin(RedirectAttributes rttr, HttpSession session,
				@PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, Model model){
			
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			
			Integer member_no =member.getMember_no();
			
			service.joinMeeting(club_meeting_no,club_no,member_no );

			rttr.addFlashAttribute("check", "JOIN");
			
			return "redirect:/club/{club_no}/meeting/{club_meeting_no}";
		}
		
		@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/cancel", method = RequestMethod.GET)
		public String clubMeetingCancel(RedirectAttributes rttr, HttpSession session,
				@PathVariable("club_no") Integer club_no,
				@PathVariable("club_meeting_no") Integer club_meeting_no, Model model){
			
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			
			Integer member_no =member.getMember_no();
			
			service.cancelMeeting(club_meeting_no,club_no,member_no );
			
			rttr.addFlashAttribute("check", "CANCEL");
			
			return "redirect:/club/{club_no}/meeting/{club_meeting_no}";
		}
		
		@RequestMapping(value="/{club_no}/meetingList", method = RequestMethod.GET)
		public String meetingListGET(Model model, HttpSession session,
				@PathVariable("club_no") Integer club_no) {
			
			log.info("meetingListGET() 호출");
			
			//비회원
			Integer result = 0;
			
			if(session.getAttribute("member") != null) {
				MembersVo member = (MembersVo) session.getAttribute("member");
				log.info(member+"");
				
				Integer member_no =member.getMember_no();
				result = service.checkClubRole(club_no, member_no);
				//result = 3 : 클럽 미가입 회원
				//result = 1 : 클럽 가입 회원
				//result = 2 : 클럽장
			}
			log.info(club_no+"");
			
			String status = "모집중";
			List<ClubMeetingsVo> meetingList_ing = service.getMeetingList(club_no ,status);
			
			status = "마감";
			List<ClubMeetingsVo> meetingList_close = service.getMeetingList(club_no ,status);
			
			status = "완료";
			List<ClubMeetingsVo> meetingList_end = service.getMeetingList(club_no ,status);

			//log.info(meetingList_ing+"");
			//log.info(meetingList_close+"");
			//log.info(meetingList_end+"");
			
			log.info(result+"");
			
			model.addAttribute("meetingList_ing", meetingList_ing);
			model.addAttribute("meetingList_close", meetingList_close);
			model.addAttribute("meetingList_end", meetingList_end);
			model.addAttribute("result", result);
			model.addAttribute("club_no", club_no);
			return "/club//meeting/meetingList";
			
		}
			
	
	//================================================================================================
	
	// http://localhost:8088/club/46/boards/new
	// 게시판글쓰기
	@RequestMapping(value = "/{club_no}/boards/new", method = RequestMethod.GET)
	public String boardWriteGet(@PathVariable("club_no") Integer club_no, HttpSession session, Model model) {
		log.info(" boardWriteGet() 호출 ");
		log.info(" club_no : "+club_no);
		
		Integer result = 0;
		
		// 로그인안했으면 로그인페이지로
		if(session.getAttribute("member") == null) {
			return "redirect:/member/signin";
		} else {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		model.addAttribute("result", result);
		
		return "/club/boards/boardWrite";
	}
	
	// 파일 X
	@RequestMapping(value = "/{club_no}/boards/new", method = RequestMethod.POST)
	public String boardWritePost(ClubBoardsVo vo) {
		log.info(" boardWritePost() 호출 ");
		
		// 전달된 정보 저장(글쓰기 정보)
		log.info("글쓰기 정보 : "+vo);
//		log.info("count : "+count);
		
		service.writeBoard(vo);
		log.info(" 글쓰기(파일X) 완료! ");
		
		Integer club_no = vo.getClub_no();
		
		return "redirect:/club/"+club_no+"/boards";
	}
	
	// 파일 O
	@RequestMapping(value = "/{club_no}/boards/fileNew", method = RequestMethod.POST)
	public String boardFileWritePost(ClubBoardsVo vo, HttpServletRequest request, MultipartFile file) {
		log.info(" boardFileWritePost() 호출 ");
		
		// 1) 파일 업로드
		// - 가상의 업로드 폴더 설정 upload 폴더 생성
		ServletContext ctx = request.getServletContext();
		String realPath = ctx.getRealPath("/resources/upload/boards");
		log.info(" 파일 저장 경로 : "+realPath);
		
		// realPath 경로에 파일업로드하기 위한 폴더가 있는지 확인
		File savePath = new File(realPath);
		if(!savePath.exists()) {
			savePath.mkdirs(); // 없으면 경로에 폴더를 만들어줌
		}
		
		String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		log.info("파일명 : "+savedFileName);
		
		String fullPath = realPath;
		
		fullPath += File.separator + savedFileName;
		File saveFile = new File(fullPath);
		
		// 경로+파일명
//		log.info("@@@@@@@@@최종 fullPath : "+fullPath);
		
		// 파일 저장
		try {
			
			file.transferTo(saveFile);
			
			// 썸네일
			File thumbnailFile = new File(realPath, "sm_"+savedFileName);
			BufferedImage bo_image = ImageIO.read(saveFile);
			BufferedImage bt_image = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = bt_image.createGraphics();
			graphic.drawImage(bo_image, 0, 0, 200, 150, null);
			ImageIO.write(bt_image, "jpg", thumbnailFile);
			
			
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		vo.setClub_board_image(savedFileName);
		// 전달된 정보 저장(글쓰기 정보)
//		log.info("글쓰기 정보 : "+vo);
		
		service.writeBoard(vo);
		log.info(" 글쓰기(파일O) 완료! ");
		
		Integer club_no = vo.getClub_no();
		
		return "redirect:/club/"+club_no+"/boards";
	}
	
	
	// http://localhost:8088/club/{club_no}/boards
	// http://localhost:8088/club/46/boards
	// http://localhost:8088/club/46/boards?page=1
	// 게시글리스트
	@RequestMapping(value = "/{club_no}/boards", method = RequestMethod.GET)
	public String boardListAllGet(@PathVariable("club_no") Integer club_no, Model model, Criteria cri, HttpSession session, @ModelAttribute("page") String page) {
		log.info(" boardListAllGet() 호출 ");
		log.info("club_no : "+club_no);
		
		model.addAttribute("boardList", service.getBoardListAll(club_no, cri));
		PageMaker pageMarker = new PageMaker();
		pageMarker.setCri(cri);
		pageMarker.setTotalCount(service.getTotalBoardCnt(club_no));
		log.info(pageMarker+"");
		model.addAttribute("pm", pageMarker);
		
		// 모임원일 때만 글쓰기 버튼이 보이도록 제어(1:모임가입O / 0:모임가입X / -1:로그인X)
		Integer checkMember = -1;
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			checkMember = service.checkClubMember(club_no, member_no);
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		log.info("모임원 확인 : "+checkMember);
		model.addAttribute("checkMember", checkMember);
		model.addAttribute("result", result);
		
		return "/club/boards/boardList";
			
	}
	
	// http://localhost:8088/club/{club_no}/boards/type/{board_type_no}
	// http://localhost:8088/club/46/boards/type/1
	// 게시글리스트(게시글유형별)
	@RequestMapping(value = "/{club_no}/boards/type/{board_type_no}", method = RequestMethod.GET)
	public String boardListTypeGet(@PathVariable("club_no") Integer club_no, @PathVariable("board_type_no") Integer board_type_no, 
			Model model, Criteria cri, HttpSession session) {
		log.info(" boardListTypeGet() 호출 ");
		log.info("club_no : "+club_no);
		log.info("board_type_no : "+board_type_no);
		
		model.addAttribute("club_no", club_no);
		
		model.addAttribute("boardList", service.getBoardList(club_no, board_type_no, cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.getTypeBoardCnt(club_no, board_type_no));
		log.info(pageMaker+"");
		model.addAttribute("pm", pageMaker);
		
		// 모임원일 때만 글쓰기 버튼이 보이도록 제어(1:모임가입O / 0:모임가입X / -1:로그인X)
		Integer checkMember = -1;
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			checkMember = service.checkClubMember(club_no, member_no);
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		log.info("모임원 확인 : "+checkMember);
		model.addAttribute("checkMember", checkMember);
		model.addAttribute("result", result);
		
		return "/club/boards/boardList";
	}
	
	// http://localhost:8088/club/{club_no}/gallery
	// http://localhost:8088/club/46/gallery
	// 갤러리 게시판
	@RequestMapping(value = "/{club_no}/gallery", method = RequestMethod.GET)
	public String galleryBoardGet(@PathVariable("club_no") Integer club_no, Model model, HttpSession session) {
		log.info(" galleryBoardGet() 호출 ");
		//log.info("club_no : "+club_no);	// view에서 ${club_no} 로 호출가능
		
		model.addAttribute("imageList", service.getBoardImageList(club_no));
		
		// 모임원이 아니면 상세보기가 안되도록 제어(1:모임가입O / 0:모임가입X / -1:로그인X)
		Integer checkMember = -1;
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			checkMember = service.checkClubMember(club_no, member_no);
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		model.addAttribute("checkMember", checkMember);
		model.addAttribute("result", result);
		
		return "/club/boards/boardGallery";
	}
	
	
	/// http://localhost:8088/club/{club_no}/boards/{club_board_no}
	/// http://localhost:8088/club/46/boards/1
	// 게시글 상세보기 (조건 - 모임가입한 사람만 확인가능:list에서 처리)
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}", method = RequestMethod.GET)
	public String boardContentGet(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, 
			Model model, HttpSession session, @ModelAttribute("page") String page) {
		log.info(" boardContentGet() 호출 ");
		log.info(service.getBoardContent(club_board_no)+"");
		log.info("@@@@@@@@@page : "+page);
		
		// 게시글과 관련된 정보 가져가기 (여기서 가져가는 member_name : 게시글 작성자)
		// 동명이인일 수도 있으니 member_no로 비교하는 게 나을 듯
		model.addAttribute("vo", service.getBoardContent(club_board_no));
		
		Integer commentCnt = service.getCommentCnt(club_board_no);
		model.addAttribute("commentCnt", commentCnt);
		
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
		
		
		// 해당글의 댓글이 있는지 체크(댓글리스트 가져오기)
		if(commentCnt > 0) {
			model.addAttribute("commentList", service.getCommentList(club_board_no));
		}
		
		model.addAttribute("likeCnt", service.getLikeCnt(club_board_no));
		
		// 좋아요 체크 (세션에 저장된 member 체크)
		if(session.getAttribute("member") == null) {
			return "/member/signin";
		}
		
		MembersVo member = (MembersVo) session.getAttribute("member");
		Integer member_no = member.getMember_no();
		
		// checkLike - 1:좋아요O / 0:좋아요X (해당 회원이 좋아요를 눌렀는지 체크)
		model.addAttribute("checkLike", service.checkLike(club_board_no, member_no));
		
		// 좋아요 멤버리스트
		model.addAttribute("likeList", service.getLikeList(club_board_no));
		model.addAttribute("result", result);
		
		
		
		return "/club/boards/boardContent";
	}
	
	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/modify
	// http://localhost:8088/club/53/boards/17/modify
	// 게시글 수정
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/modify", method = RequestMethod.GET)
	public String modifyBoardGet(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, Model model, HttpSession session) {
		log.info(" modifyBoardGet() 호출 ");
		log.info("club_no : "+club_no);
		log.info("club_board_no : "+club_board_no);
		
		Integer result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			result = service.checkClubRole(club_no, member_no);
			//result = 3 : 클럽 미가입 회원
			//result = 1 : 클럽 가입 회원
			//result = 2 : 클럽장
		}
	
		model.addAttribute("board", service.getBoardContent(club_board_no));
		model.addAttribute("result", result);
	
		return "/club/boards/boardModify";
	}
	
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/modify", method = RequestMethod.POST)
	public String modifyBoardPost(ClubBoardsVo vo) {
		log.info(" modifyBoardPost() 호출 ");
		Integer club_board_no = vo.getClub_board_no();
//		log.info("club_board_no : "+club_board_no);
//		log.info("vo : "+vo);
		
		service.modifyBoardContent(vo);
//		
		return "redirect:/club/"+vo.getClub_no()+"/boards";
		
	}
	
	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/delete
	// http://localhost:8088/club/53/boards/26/delete
	// 게시글 삭제
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/delete", method = RequestMethod.POST)
	public String deleteBoardPost(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, RedirectAttributes rttr) {
		log.info(" deleteBoardPost() 호출 ");
		log.info("club_no : "+club_no);
		
		service.deleteBoard(club_board_no);
		rttr.addFlashAttribute("check", "DELOK");
		
		return "redirect:/club/"+club_no+"/boards";
	}
	
	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/comment
	// http://localhost:8088/club/46/boards/24/comment
	// 댓글 등록
	@ResponseBody
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/comment", method = RequestMethod.POST)
	public void writeCommentPost(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, HttpSession session, BoardCommentsVo vo) {
		log.info(" writeCommentPost() 호출 ");
		
		// 전달받은 데이터
		log.info("commentVo : "+vo);
		
		// 작성자(member_no) 세션에서 꺼내쓰기
		MembersVo member = (MembersVo) session.getAttribute("member");
		Integer member_no = member.getMember_no();
		vo.setMember_no(member_no);
		log.info("member추가 vo : "+vo);
		
		service.writeComment(vo);
		service.updateCommentCnt(club_board_no);
		log.info(" 댓글등록 완료! ");
		
		
	}
	
	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/comment/modify?board_comment_no="+board_comment_no
	// 댓글 수정
	@ResponseBody
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/comment/modify", method = RequestMethod.POST)
	public void updateCommentPost(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, 
			@RequestParam("board_comment_no") Integer board_comment_no, BoardCommentsVo vo) {
		log.info(" updateCommentPost() 호출 ");
		log.info("board_comment_no : "+board_comment_no);
		log.info("boardCommentsVo : "+vo);
		
		service.updateComment(vo);
		log.info(" 댓글수정 완료! ");
	}
	
	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/comment/delete?board_comment_no="+board_comment_no
	// 댓글 삭제
	@ResponseBody
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/comment/delete", method = RequestMethod.POST)
	public void deleteCommentPost(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no,
			@RequestParam("board_comment_no") Integer board_comment_no) {
		log.info(" deleteCommentPost() 호출 ");
		
		service.deleteComment(board_comment_no);
		service.decreaseCommentCnt(club_board_no);
		log.info(" 댓글삭제 완료! ");
		
	}
	
	
	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/likeUp
	// 좋아요 등록
	@ResponseBody
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/likeUp", method = RequestMethod.POST)
	public void LikePost(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, 
			BoardLikesVo vo, HttpSession session) {
		log.info(" LikePost() 호출 ");
		log.info("좋아요 vo : "+vo);
		
		// 세션에서 member_no
		MembersVo member = (MembersVo) session.getAttribute("member");
		Integer member_no = member.getMember_no();
		vo.setMember_no(member_no);
		log.info("member추가 vo : "+vo);
		
		service.insertLike(vo);
		service.increaseLikeCnt(club_board_no);
		log.info(" 좋아요 등록 완료! ");
	}

	// http://localhost:8088/club/{club_no}/boards/{club_board_no}/likeDown
	// 좋아요 취소
	@ResponseBody
	@RequestMapping(value = "/{club_no}/boards/{club_board_no}/likeDown", method = RequestMethod.POST)
	public void cancleLikePost(@PathVariable("club_no") Integer club_no, @PathVariable("club_board_no") Integer club_board_no, 
			HttpSession session) {
		log.info(" cancleLikePost() 호출 ");
		
		// 세션에서 member_no
		MembersVo member = (MembersVo) session.getAttribute("member");
		Integer member_no = member.getMember_no();
		
		service.cancelLike(club_board_no, member_no);
		service.decreaseLikeCnt(club_board_no);
		log.info(" 좋아요 취소 완료! ");
	}
	
	
	
	//============= 모임 등록 ==========================================
		
		// http://localhost:8088/club/new  모임등록 페이지
		// http://localhost:8088/club/new?member_no=7
		@RequestMapping(value="/new", method = RequestMethod.GET)
		public String newClubGet(Model model, HttpSession session) {
				
			MembersVo member = (MembersVo)session.getAttribute("member");
			if(member == null) {
				return "redirect:/member/signin";
			}
			
			if(member != null) {
			
			Integer no = member.getMember_no();
			log.info("member: "+no);
			
			//회원 관심사 출력
			InterestsVo interestvo = service.getMemberInterest(no);
			model.addAttribute("interest", interestvo);
			model.addAttribute("membervo", member);
			
			}
			return "/club/clubNew";
				 
	}
	
	
		// 상세관심사 ajax (등록페이지에 출력)
		// http://localhost:8088/club/getdetail
		@ResponseBody
		@RequestMapping(value="/getdetail", method = RequestMethod.GET)
		public List<InterestDetailsVo> test(@RequestParam("itemNum") int itemNum) {
			log.info("관심사는: "+itemNum);
			List<InterestDetailsVo> detailList = service.getDetailName(itemNum);
			return detailList;
	}
	
	
		// 모임 제목 중복체크 ajax 
		@ResponseBody
		@RequestMapping(value="/check_clubname", method = RequestMethod.GET)
		public int checkClubName(@RequestParam("club_name") String club_name) {
				
			ClubsVo vo=service.checkClubName(club_name);
				int result = 0;
		        if(vo != null) { result =  1; } //중복
		        else if(vo == null){ result =  0; } //없음
		        return result;
		}
		
		
		
		@RequestMapping(value = "/new", method = RequestMethod.POST)
		public String createClubPost(@RequestParam("interest_detail_name") String detail,MultipartFile file,
				ClubsVo clubsvo,Model model,HttpSession session,HttpServletRequest request ) throws IOException {
		
				log.info("모임등록 호출");
			
			  //모임 사진등록 
				if(!file.isEmpty()) { 
				
				//가상업로드 폴더 설정
				ServletContext ctx =request.getServletContext();
				String realpath = ctx.getRealPath("/resources/upload/clubs/");
				log.info("파일저장경로: " +realpath);
				
				//realpath 경로에 폴더 있는지 확인
				File savePath = new File(realpath);
				if(!savePath.exists()) {
					savePath.mkdirs();
				} //없다면 폴더 만듦
				
				
				//String FileName = file.getOriginalFilename();
				String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
				log.info("파일명: "+savedFileName);

				
				String fullpath = realpath;
				fullpath += File.separator + savedFileName;
				File saveFile = new File(fullpath);
				
				file.transferTo(saveFile);
				
				clubsvo.setClub_image(savedFileName);
				log.info("사진저장 완료");
				
				}
					
				if(file.isEmpty()) {
					clubsvo.setClub_image("joinus.png");
				}
				
				
				MembersVo member = (MembersVo)session.getAttribute("member");
				
				  //관심사번호 가져오기 
				  InterestDetailsVo interDetail = service.getInterestNo(detail);
				  //클럽정보 저장 + 넘버가져오기 
				  service.newClub(clubsvo); 
				  int club_no = (int)clubsvo.getClub_no();
				  model.addAttribute("club_no", club_no); 
				  //모임관심사 저장
				  service.newClubInterest(club_no,interDetail.getInterest_no(),interDetail.getInterest_detail_no()); 
				  //모임 별점주기
				  ClubGradesVo grade = new ClubGradesVo();
				  grade.setClub_grade_rate(5); //모임장은 자동5점
				  grade.setClub_no(club_no);
				  grade.setMember_no(member.getMember_no());
				  service.clubGrade(grade);
				  //모임가입
				  ClubMembersVo members = new ClubMembersVo(); 
				  members.setClub_no(club_no);
				  members.setMember_no(member.getMember_no()); 
				  members.setClub_member_role("admin"); //모임 첫생성은 관리자
				  service.join(members);
				  
				  return "redirect:/club/{club_no}";
		}
	
	
		// 모임상세페이지		
		// http://localhost:8088/club/	
		// http://localhost:8088/club/5
		@RequestMapping(value = "/{club_no}", method = RequestMethod.GET)
		public String info(Model model,HttpSession session, @PathVariable("club_no") int club_no,
							@CookieValue(value="recentViewClub", required = false) Cookie clubCookie,
							HttpServletResponse response) throws UnsupportedEncodingException {
			
				// 쿠키가 존재하면 해당 쿠키에 있는 값 출력
				log.info("club : {}", clubCookie);
				// 쿠키가 존재하지 않으면 쿠키 생성
				if(clubCookie == null) {
					Cookie cookie = new Cookie("recentViewClub", String.valueOf(club_no));
					cookie.setMaxAge(60*60*24*30);
					cookie.setPath("/");
					response.addCookie(cookie);
				} else {
					log.info("cookie value : {}", clubCookie.getValue());
					// 만약 쿠키가 존재한다면 값을 추가 (이때 동일한 값이 있을 경우 추가 X)
					String cookieValue = clubCookie.getValue();
					
					String[] cookieValues = cookieValue.split("&");
					
					boolean hasValue = false;
					for(String value : cookieValues) {
						if(value.equals(String.valueOf(club_no))) {
							hasValue = true;
						}
					}
					
					if(!hasValue) {
						cookieValue += "&"+String.valueOf(club_no);
						clubCookie.setValue(cookieValue);
					}
					
					log.info("데이터 추가 적용된 쿠키 : {}", clubCookie.getValue());
					clubCookie.setPath("/");
					clubCookie.setMaxAge(60*60*24*30);
					response.addCookie(clubCookie);
				}
			
				//모임정보
				ClubsVo clubvo = service.getClubInfo(club_no);
				model.addAttribute("clubvo", clubvo);
				
				MembersVo member = (MembersVo)session.getAttribute("member");
				if(member == null) {
					
					//모임회원 리스트
					List<ClubMembersVo> clubmemberList = service.getClubMembers(club_no);
					model.addAttribute("clubmemebrList", clubmemberList);
					
					//정모리스트 
					List<ClubMeetingsVo> meetings = service.getMeetings(club_no);
					model.addAttribute("meetings", meetings);
					log.info("정모리스트: "+meetings);
					int meetingSize = meetings.size();
					model.addAttribute("meetingSize", meetingSize);
					log.info("정모갯수: "+meetingSize);
					
					//게시글(사진빼오기)
					List<ClubBoardsVo> boards = service.getBoardImageList(club_no);
					model.addAttribute("boards", boards);
					log.info("게시글사진: "+boards);
					
					//모임관심사 정보로 관심사 가져오기
					String interDetail = service.getClubInterestDName(club_no);
					model.addAttribute("interDetail", interDetail);   
					log.info("모임 상세관심사"+interDetail);
					
					// 모임 회원수 가져오기
					model.addAttribute("clubMembercnt",service.clubMemberCount(club_no));
					
					
				}
				
				
				if(member != null) {
				
				
					
					model.addAttribute("member_no",member.getMember_no() );
					log.info("회원넘버: "+member.getMember_no());
					
					//모임회원 리스트
					List<ClubMembersVo> clubmemberList = service.getClubMembers(club_no);
					model.addAttribute("clubmemebrList", clubmemberList);
					
					//방문한 모임회원
					int result = 0; 
					String roleResult = "";
					ClubMembersVo clubmembersvo = service.getClubMemberNo(club_no,member.getMember_no());
					if(clubmembersvo != null) { 
						result = clubmembersvo.getMember_no(); 
						roleResult = clubmembersvo.getClub_member_role();
					}else if(clubmembersvo == null) { 
						result = 0;
						roleResult = ""; }
					model.addAttribute("clubmember", result);
					model.addAttribute("clubmemberRole", roleResult);
					log.info("모임회원O = 회원번호 / 모임회원X = 0 :   "+result);
					log.info("roleResult(admin/com): "+roleResult);
					
					
					//벤당한 모임회원
					List<Integer> ban = service.getBanMember(club_no);
					if(ban.isEmpty()) {
						ban.add(0);
					} // ban 리스트가 null이면 0만 출력됨
					model.addAttribute("ban", ban);
					
					// 모임 회원수 가져오기
					model.addAttribute("clubMembercnt",service.clubMemberCount(club_no));
					
					//별점정보
					List<ClubGradesVo> gradevo = service.getClubGrade(club_no);
					model.addAttribute("clubGrade", gradevo);
					
					//별점참여
					int result2 = 0;
					Integer graded = service.getGradeinfo(club_no,member.getMember_no());
					if(graded != null) { 
						result2 = graded; 
					}else if(graded == null) { 
						result2 = 0; }
					model.addAttribute("graded", result2);
					log.info("별점 참여시 회원번호, 미참여시 0: "+result2);
			
				
					//클럽별점 평균,참여자수
					model.addAttribute("gradeAvgCnt", service.getClubAvgCnt(club_no));   
									
					//모임관심사 정보로 관심사 가져오기
					String interDetail = service.getClubInterestDName(club_no);
					model.addAttribute("interDetail", interDetail);   
					log.info("모임 상세관심사"+interDetail);
					
					//찜 여부 확인
					model.addAttribute("dipMember", service.dip(clubvo.getClub_no()));   
					
					//정모리스트 
					List<ClubMeetingsVo> meetings = service.getMeetings(club_no);
					model.addAttribute("meetings", meetings);
					log.info("정모리스트: "+meetings);
					int meetingSize = meetings.size();
					model.addAttribute("meetingSize", meetingSize);
					log.info("정모갯수: "+meetingSize);
					
					//내 회원번호로 해당 클럽 정모멤버리스트 조회하기
					List<MeetingMembersVo> meetingMbrs = service.checkMeetingMember(club_no,member.getMember_no());
					List<MeetingMembersVo> mtMbrs = new ArrayList<MeetingMembersVo>();
					if(meetingMbrs != null) { 
						mtMbrs = meetingMbrs; 
					}
					model.addAttribute("meetingMbrs",mtMbrs);
					// null값 처리
					int result3 = 100;
					if(meetingMbrs.isEmpty()) { 
						result3 = 0;	}
					model.addAttribute("meetingMbrsNull",result3);
					log.info("정모멤버리스트: "+meetingMbrs); 
					log.info("정모멤버리스트: "+result3); 
					
					//정모 참석인원수
					model.addAttribute("meetingCnt", service.getMeetingMemberCnt(club_no));		
					
					
					//게시글(사진빼오기)
					List<ClubBoardsVo> boards = service.getBoardImageList(club_no);
					model.addAttribute("boards", boards);
					log.info("게시글사진: "+boards);
					
					
					session.setAttribute("member", member);
					
				}
				
				return "/club/clubInfo";
			}
			
	
	
		// 모임가입 ajax (상세페이지에서 alert창 띄움)
		@ResponseBody
		@RequestMapping(value = "/{club_no}",method=RequestMethod.POST)
		public void joinClub(@PathVariable("club_no") int club_no, @ModelAttribute("member_no") int member_no) {
				
				ClubMembersVo members = new ClubMembersVo();
				members.setMember_no(member_no);
				members.setClub_no(club_no);
				members.setClub_member_role("common"); //상세페이지에서 가입하면 무조건 회원
				service.join(members);
				log.info("모임가입완료");
				
			}
			
		// 별점주기 ajax (상세페이지에서 클릭)
		@ResponseBody
		@RequestMapping(value = "/{club_no}/grade", method = RequestMethod.POST)
		public void clubGrade(@ModelAttribute ClubGradesVo vo) {
					service.clubGrade(vo);
					log.info("별점주기 완료");
			}
		
		// 찜하기 ajax (상세페이지에서 클릭)
		@ResponseBody
		@RequestMapping(value = "/{club_no}/dip", method = RequestMethod.POST)
		public void clubDip(@ModelAttribute MemberDipsVo vo) {
			service.clubDip(vo.getMember_no(),vo.getClub_no());
			log.info("찜하기 완료");
			
		}
		// 찜취소 ajax (상세페이지에서 클릭)
		@ResponseBody
		@RequestMapping(value = "/{club_no}/dipX", method = RequestMethod.POST)
		public void clubDipX(@ModelAttribute MemberDipsVo vo) {
			service.dipX(vo.getMember_no(),vo.getClub_no());
			log.info("찜 취소완료");
			
		}
		
		// 정모참석 ajax (상세페이지에서 alert창 띄움)
				@ResponseBody
				@RequestMapping(value = "/{club_no}/meeting/{meeting_no}/join",method=RequestMethod.POST)
				public Boolean joinMeeting(@ModelAttribute MeetingMembersVo vo	) {
					List<Map<String, Integer>> map = service.getMemberCapa(vo.getClub_meeting_no(), vo.getClub_no());
					int clubCapa = Integer.parseInt(String.valueOf(map.get(0).get("clubCapa")));
					int memberCnt = Integer.parseInt(String.valueOf(map.get(0).get("memberCnt")));
					log.info("정모 참석하기 ajax 호출!");
					log.info("정모 정원 : "+ clubCapa+"명");
					log.info("정모 참가 인원 : "+memberCnt+"명");
					
					if(clubCapa < memberCnt) {
						log.info("인원마감 정모참석불가");
						return false;
					}
					if(clubCapa > memberCnt) {
						service.joinMeeting(vo);
						log.info("정모참석 신청 완료");
					}
					return true;
						
					}
		// 정모참석취소 ajax (상세페이지에서 alert창 띄움)
				@ResponseBody
				@RequestMapping(value = "/{club_no}/meeting/{meeting_no}/leave",method=RequestMethod.POST)
				public void outMeeting(@ModelAttribute MeetingMembersVo vo) {
					service.outMeeting(vo);
					log.info("정모신청이 취소되었습니다");
				}
		
		
	
	
	
			
			
}
