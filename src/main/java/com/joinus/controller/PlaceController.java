package com.joinus.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.joinus.domain.PlacesVo;
import com.joinus.service.PlaceService;

@Controller
@RequestMapping("/place/*")
public class PlaceController {
	
	private static final Logger log = LoggerFactory.getLogger(PlaceController.class);
	
	@Autowired
	private PlaceService placeService;
	
	
	// 원데이클래스 리스트 크롤링
	// http://localhost:8088/place/classList	
	@RequestMapping(value = "/classList", method = RequestMethod.GET)
	public void classListGET(Model model) throws IOException {
		log.info(" classListGET() 호출 ");
		
		JSONArray classList = placeService.classList();

		model.addAttribute("classList", classList);
	}
	
	
//	// 원데이클래스 본문 크롤링
//	@RequestMapping(value = "/class/${class_number}", method = RequestMethod.GET)
//	public void classContentGET(@PathVariable("class_number") int class_number, Model model) {
//		log.info(" classContentGET() 호출");
//		
//		JSONArray classContent = placeService.classList();
//		
//		model.addAttribute("classContent", classContent);
//	}
	
	
	// 시설 목록(비제휴)
	// http://localhost:8088/place/placeList
	@RequestMapping(value = "/placeList", method = RequestMethod.GET)
	public String placeListGET(Model model, @ModelAttribute("location") String location) {
		log.info(" placeListGET() 호출 ");
		
		List<PlacesVo> placeList = null;
		location = "0";
//		log.info(" location(지역정보) : "+location);
		if(location.equals("0")) {
//			log.info(" [location == 0] => 시설목록(비제휴) 전체 출력 ");
			placeList = placeService.getPlaceList();
		} else if (!location.equals("0")) {
			placeList = placeService.getCityPlaceList(location);
//			log.info("placeList : "+placeList);
		}
		
		for(PlacesVo vo : placeList) {
			String[] addressArr = vo.getPlace_address().split(" ");
			String address = addressArr[0].substring(0, 2) + " " + addressArr[1];
			vo.setPlace_address(address);
		}
		
		model.addAttribute("placeList", placeList);
		// 부산(구) 목록
		model.addAttribute("guList", placeService.getBusanGuList());
		model.addAttribute("location", location);
		
		return "/place/placeList";
	}
	
	
	
	// http://localhost:8088/place/1
	// 시설상세(비제휴)
	@RequestMapping(value = "/{place_no}", method = RequestMethod.GET)
	public String placeContentGet(@PathVariable("place_no") int place_no, Model model) {
		log.info(" placeContentGet() 호출 ");
		
		model.addAttribute("place", placeService.getPlaceContent(place_no));
		
		return "/place/placeContent";
	}
	
	  
	   
	
	
	
	
}
