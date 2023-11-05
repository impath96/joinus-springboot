package com.joinus.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joinus.domain.ClubMeetingsVo;
import com.joinus.domain.ClubTotalBean;
import com.joinus.domain.Criteria;
import com.joinus.domain.InterestDetailsVo;
import com.joinus.domain.MeetingTotalBean;
import com.joinus.domain.MembersVo;
import com.joinus.domain.PageMaker;
import com.joinus.service.ClubService;

@RestController
@RequestMapping("/club/*")
public class ClubRestController {

	private static final Logger log = LoggerFactory.getLogger(ClubRestController.class);
	
	@Autowired
	private ClubService service;
	
	@RequestMapping(value="/{club_no}/rentalList", method=RequestMethod.GET)
	public ResponseEntity<List<MeetingTotalBean>> rentalAll(
			@PathVariable("club_no") Integer club_no,
			HttpSession session){
		
		log.info("rentalList -REST 호출");
		//멤버정보 가져오기
		MembersVo member = (MembersVo) session.getAttribute("member");
		int member_no =member.getMember_no();
		
		//예약정보 불러오기
		List<MeetingTotalBean> rentalList = (List<MeetingTotalBean>)service.getRental(member_no);
		log.info(rentalList+"");
		
		ResponseEntity<List<MeetingTotalBean>> entity = null;
		
		try {
			entity = new ResponseEntity<List<MeetingTotalBean>>(rentalList, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	
	
	@RequestMapping(value="/{club_no}/meeting/rental_no/{rental_places_no}")
	public ResponseEntity<List<MeetingTotalBean>> getRentalREST(@PathVariable("club_no") int club_no,
								@PathVariable("rental_places_no") int rental_places_no) {
		log.info("rentalPlace -REST 호출");
		List<MeetingTotalBean> rentalList = service.getRentalREST(rental_places_no);
		
		log.info(rentalList+"");
		
		ResponseEntity<List<MeetingTotalBean>> entity = null;
		
		try {
			entity = new ResponseEntity<List<MeetingTotalBean>>(rentalList, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		log.info(entity+"");
		
		return entity;
	
	}
	
	@RequestMapping(value="/{club_no}/meeting/{club_meeting_no}/address",
			produces = "application/text; charset=utf8")
	public ResponseEntity<String> getAddressREST(@PathVariable("club_no") int club_no,
							@PathVariable("club_meeting_no") int club_meeting_no
													){
	log.info(club_meeting_no+"");	
	String address = service.getMeetingAddr(club_meeting_no);
	
	log.info(address+"");
	
	ResponseEntity<String> entity = null;
	
	try {
		entity = new ResponseEntity<String>(address, HttpStatus.OK);
	} catch (Exception e) {
		entity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	log.info(entity+"");
	
	return entity;
	}
	
	
	@RequestMapping(value="/clubList/{interest_no}", method=RequestMethod.GET)
	public ResponseEntity<List<InterestDetailsVo>> getInterestDetailREST(
			@PathVariable("interest_no") Integer interest_no){
		
		log.info(interest_no+"");	
		
		List<InterestDetailsVo> interest_list = service.getInterestDetail(interest_no);
		
		ResponseEntity<List<InterestDetailsVo>> entity = null;
		
		try {
			entity = new ResponseEntity<List<InterestDetailsVo>>(interest_list, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		log.info(entity+"");
		
	return entity;
	}
	
	
	@RequestMapping(value="/clubList/{interest_no}/{interest_detail_no}", method = RequestMethod.GET)
	public ResponseEntity<List<ClubTotalBean>> clubListDetailRest(@PathVariable("interest_detail_no") Integer interest_detail_no,
						@PathVariable("interest_no") int interest_no, Criteria cri, Model model,HttpSession session) {
		
		log.info("interest_detail_no : "+interest_detail_no);	
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			log.info(member+"");
			}
		List<ClubTotalBean> clubDetailList = service.clubListDetail(interest_detail_no,cri);
			model.addAttribute("clubDetailList", clubDetailList);
			log.info("clubDetailList : "+clubDetailList);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.totalCntDetail(interest_detail_no));
			model.addAttribute("interest_detail_no",interest_detail_no);
			log.info(pageMaker+"");
			model.addAttribute("pm", pageMaker);
			

			
			ResponseEntity<List<ClubTotalBean>> entity = null;
			
			try {
				entity = new ResponseEntity<List<ClubTotalBean>>(clubDetailList, HttpStatus.OK);
			} catch (Exception e) {
				entity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		
		return entity;
		
	}
	
	@RequestMapping(value="/{club_no}/meeting/end", method = RequestMethod.GET)
	public ResponseEntity<Integer> clubMeetingDateREST(
			@PathVariable("club_no") Integer club_no, Model model,HttpSession session) {
		
		log.info("clubMeetingDateREST 호출");

		ResponseEntity<Integer> entity = null;
		Integer MeetingDate= service.checkMeetingDate(club_no);
		
		try {
			entity = new ResponseEntity<Integer>(MeetingDate, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	
}
