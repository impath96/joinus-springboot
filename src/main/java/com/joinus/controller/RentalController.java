package com.joinus.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.MembersVo;
import com.joinus.domain.PartnerPlacesVo;
import com.joinus.domain.PaymentsVo;
import com.joinus.domain.RentalPlacesVo;
import com.joinus.service.ClubService;
import com.joinus.service.RentalService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller
@RequestMapping("/rental/*")
public class RentalController {
	
	@Inject
	private ClubService clubService;
	
	@Inject
	private RentalService rentalService;
	
	private static final Logger log = LoggerFactory.getLogger(ClubController.class);
	
	private IamportClient api;
	
	public RentalController() {
		// REST API 키와 REST API secret
		this.api = new IamportClient("4450940620010058",
				"cd721e413ac18a65fe657ac002c45a9427ca9cb46aa7cca4e0600788bf9c7b4d1de7fcb996d24ccd");
	}
	
	
	
	//================================================================================================
	
	// http://localhost:8088/rental/partnerPlace
	// 장소 유형 선택
	@RequestMapping(value = "/partnerPlace", method = RequestMethod.GET)
	public void placeTypeGet() {
		log.info(" placeTypeGet() 호출 ");
	}
	
	
	// http://localhost:8088/rental/partnerPlaceList?type=음악연습실&location=동래구
	// 대관리스트
	@RequestMapping(value = "/partnerPlaceList", method = RequestMethod.GET)
	public String placeListGet(Model model, HttpSession session, @ModelAttribute("type") String type, @ModelAttribute("location") String location) {
		log.info(" placeListGet() 호출 ");
		
		if(type.isEmpty()) {
			type = "0";
		}
		log.info("type : "+type);
		log.info("location : "+location);
//		log.info(location.isEmpty()+"");
		
		List<PartnerPlacesVo> partnerPlaceList = null;
		
		// 위에서 type  location 값이 없으면 전체가 출력될 수 있게 0으로 값을 줬음
		// 1. 장소유형 선택 후 리스트 화면으로 이동 => type에 값이 있는 상태
		if(!type.equals("0") && (location.isEmpty() || location.equals("0"))) {
			// 장소유형이 선택된 경우(기본- location은 아직 아무것도 선택X -> empty)
			
			// 로그인 세션값이 있으면 모임장인지 확인하고 모임장의 주소가 있는지 확인
			if(session.getAttribute("member") != null) {
				// 회원
				MembersVo member = (MembersVo) session.getAttribute("member");
				Integer member_no = member.getMember_no();
				Integer result = clubService.checkClubAdmin(member_no);
				
				// result 값이 0보다 크면 모임장
				if(result > 0) {
					// 모임장
					String clubAdminAddr = clubService.getClubAdminAddr(member_no);
					log.info("@@@@@@@@@clubAdminAddr : "+clubAdminAddr);
					
					// 모임장의 주소가 있을 경우("구"만 뽑아오기)
					if(clubAdminAddr != null) {
						// 모임장 - 주소 O
						
						String[] adminAddrArr = clubAdminAddr.split(" ");
						
						if(location.isEmpty()) {
							location = adminAddrArr[1];
							log.info("모임장의 location(구) : "+location);
							log.info("모임장O 주소가 있을 경우 - 장소유형, 위치 정보가 다 있음");
							partnerPlaceList = rentalService.getPartnerPlaceList(type, location);
//							log.info(partnerPlaceList+"");
							
						} else if(location.equals("0")) {
							// 모임장의 주소가 있지만 위치를 전체로 선택한 경우
							partnerPlaceList = rentalService.getTypePartnerPlaceList(type);
//							log.info(partnerPlaceList+"");
						}
						
					} else {
						// 모임장 - 주소 X
						location = "0";
						log.info("모임장O 주소가 없을 경우");
						partnerPlaceList = rentalService.getTypePartnerPlaceList(type);
//						log.info(partnerPlaceList+"");
					}
					
				} else {
					// result 값이 0이면 모임장X => location 은 그대로 0
					// 모임장 X 일반 회원
					location = "0";
					log.info("모임장X 일반회원/장소유형이 선택된 경우");
					partnerPlaceList = rentalService.getTypePartnerPlaceList(type);
//					log.info(partnerPlaceList+"");
				}
				
				
			} else {
				// 비회원
				// 장소유형이 선택된 경우 (location 값이 0)
				location = "0";
				log.info("비회원/장소유형이 선택된 경우");
				partnerPlaceList = rentalService.getTypePartnerPlaceList(type);
//				log.info(partnerPlaceList+"");
			}
			
		} else if(!type.equals("0") && !location.equals("0")) {
			// 유형과 위치 둘 다 선택했을 경우
			log.info("유형과 위치 둘 다 선택했을 경우");
			partnerPlaceList = rentalService.getPartnerPlaceList(type, location);
//			log.info(partnerPlaceList+"");
			
		} else if(type.equals("0") && !location.equals("0")) {
			// 장소유형을 전체로 바꿀 경우(위치는 선택)
			log.info("장소유형을 전체로 바꿀 경우(위치는 선택)");
			partnerPlaceList = rentalService.getCityPartnerPlaceList(location);
//			log.info(partnerPlaceList+"");
			
		} else if(type.equals("0") && location.equals("0")) {
			// 둘 다 전체
			log.info("둘 다 전체");
			partnerPlaceList = rentalService.getAllPartnerPlaceList();
//			log.info(partnerPlaceList+"");
			
		}
		
		
		// 리스트에서 제휴시설주소는 (시 구)까지만 보이게
		for(PartnerPlacesVo vo :partnerPlaceList) {
			String[] addressArr = vo.getPartner_place_address().split(" ");
			// 부산광역시, 부산 이렇게 두가지가 존재하는데 부산 00구로 출력되도록
			String address = addressArr[0].substring(0, 2) + " " + addressArr[1];
			vo.setPartner_place_address(address);
		}
//		log.info("주소 자른 결과 : "+partnerPlaceList);
		model.addAttribute("partnerPlaceList", partnerPlaceList);
		
		// select박스 - (구)리스트
		model.addAttribute("guList", rentalService.getBusanGuList());
		
		model.addAttribute("type", type);
		model.addAttribute("location", location);
		
		return "/rental/partnerPlaceList";
		
	}
	
	
	// http://localhost:8088/rental/partnerPlaces/{partner_place_no}
	// http://localhost:8088/rental/partnerPlaces/1
	// 대관상세
	@RequestMapping(value = "/partnerPlaces/{partner_place_no}", method = RequestMethod.GET)
	public String partnerPlaceContentGet(@PathVariable("partner_place_no") Integer partner_place_no, Model model, HttpSession session) {
		log.info(" partnerPlaceContentGet() 호출 ");
		
		// 세션에 저장된 member_no가 모임장이 아니면 결제하기 버튼 막기
		// 모임장인지 체크(result > 0 : 모임장)
		int result = 0;
		
		if(session.getAttribute("member") != null) {
			MembersVo member = (MembersVo) session.getAttribute("member");
			Integer member_no = member.getMember_no();
			result = clubService.checkClubAdmin(member_no);
		}
		
		model.addAttribute("partnerPlace", rentalService.getPartnerPlaceContent(partner_place_no));
		model.addAttribute("checkClubAdmin", result);
		List<RentalPlacesVo> rentalPlaceDate = rentalService.getRentalPlaceDate(partner_place_no);
		log.info("rentalPlaceDate : "+rentalPlaceDate);
		model.addAttribute("rentalPlaceDate", rentalService.getRentalPlaceDate(partner_place_no));
		
		return "/rental/partnerPlaceContent";
	}
	
	@ResponseBody
	@RequestMapping(value = "/partnerPlaces/{partner_place_no}/dateCheck", method = RequestMethod.GET)
	public List<Integer> rentalDateGet(@PathVariable("partner_place_no") Integer partner_place_no, 
			@RequestParam("rental_date") Date rental_date){
		log.info(" rentalDateGet() 호출 ");
		
		List<Integer> rentalTimeList = rentalService.getRentalTime(rental_date, partner_place_no);
		log.info(rental_date+"에 해당하는 예약시간리스트 : "+rentalTimeList);
		
		return rentalTimeList;
	}
	
	
	@RequestMapping(value = "/partnerPlaces/{partner_place_no}", method = RequestMethod.POST)
	public String partnerPlaceContentPost(PartnerPlacesVo partnerplacevo ,PaymentsVo paymentvo, 
			Model model, @RequestParam("rental_time_no") Integer rentaltimeno,
			@RequestParam("rental_date") Date rental_date, HttpSession session,@RequestParam("payment_price") Integer payment_price) {
		log.info(" partnerPlaceContentPost() 호출");

		rentalService.insertPlaceBeforePay(rental_date, rentaltimeno);
		
		String ppname = partnerplacevo.getPartner_place_name();
		model.addAttribute("ppname", ppname);
		model.addAttribute("payment_price", payment_price);
		log.info("장소이름,결제금액: "+ppname+payment_price);
		
		MembersVo vo = (MembersVo)session.getAttribute("member");
		model.addAttribute("members", vo);
		model.addAttribute("payment", paymentvo);
		model.addAttribute("rental_time_no", rentaltimeno);
		log.info("결제정보: "+paymentvo);
		
		// 결제 후 예약정보저장
		return "/rental/payment";
		
	}
		
	@ResponseBody
	@RequestMapping(value="/verifyIamport/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(
			Model model, Locale locale, HttpSession session
			, @PathVariable("imp_uid") String imp_uid ) throws IamportResponseException, IOException{	
			log.info("아임포트 결제 호출");
			return api.paymentByImpUid(imp_uid);
			
	}
		
	@ResponseBody
	@RequestMapping(value ="/partnerPlaces/{partner_place_no}/payment",method=RequestMethod.POST)
	public PaymentsVo payment( Model model, 
			@RequestParam("partner_place_price") Integer partner_place_price,
			@RequestParam("payment_price") Integer payment_price,
			@RequestParam("rental_time_no") Integer rental_time_no,
			@PathVariable("partner_place_no") Integer partner_place_no,
			PaymentsVo paymentvo,HttpSession session){
		log.info(" 결제 정보 저장시작 ");
		log.info(" 결제 정보 저장 호출 " + paymentvo);
		
		log.info("받아온 vo정보 . paymentvo : "+paymentvo);

			
		// 주문번호 생성 (날짜-place_no)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
	
		String rsNum = sdf.format(date)+"-"+partner_place_no+"-"+rental_time_no;
		log.info("예약번호(날짜+장소번호+예약시간): "+rsNum);
		
		paymentvo.setReservation_no(rsNum);
		MembersVo mvo = (MembersVo)session.getAttribute("member");
		paymentvo.setMember_no(mvo.getMember_no());
		paymentvo.setPartner_place_no(partner_place_no);
		paymentvo.setPartner_place_price(partner_place_price);
		paymentvo.setPayment_status(1);
		
		Integer pay = rentalService.pay(paymentvo);
		
		if(pay == 1) {
			log.info("결제 정보 저장 성공");
		}else {
			log.info("결제 정보 저장 실패..");
		}
		
		// update로 rentalPlace 정보 저장
		log.info("rentalplace 저장시작");
		// 가장 최근에 저장한 RentalPlacesVo 꺼내기
		RentalPlacesVo LatelyrentalPlace = rentalService.getLatelyRentalPlace();
		log.info("LatelyrentalPlace : "+LatelyrentalPlace);
		// rentalPlace 정보 저장
		String reservation_no = rsNum;
		Integer member_no = mvo.getMember_no();
		Integer payment_no = paymentvo.getPayment_no();
		Integer rental_places_no = rentalService.getRentalPlaceCnt();
		rentalService.updateLatelyRentalPlace(reservation_no, member_no, partner_place_no, payment_no, rental_places_no);
		log.info("rentalPlace 저장완료");
		
		return paymentvo;
		
		//form으로 받은 정보들로 결제 후 결제완료 페이지 출력
		
	}
		
		
		
	
	
	
}
	


