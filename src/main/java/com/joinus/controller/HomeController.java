package com.joinus.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joinus.domain.ClubsVo;
import com.joinus.domain.MembersVo;
import com.joinus.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	

	@Autowired
	private MainService service;
	
	@GetMapping(value = "/")
	public String home(Locale locale, Model model, HttpSession session) {
		
		List<ClubsVo> vo1 = service.getMostPopularClub();
		model.addAttribute("popular", vo1);
		List<ClubsVo> vo2 = service.getMostRecentClub();
		model.addAttribute("latest", vo2);
		
		if(vo2==null) {
			log.info("신규모임리스트 : ", vo2);
		}
		
		List<ClubsVo> vo3 = service.getMostNumerousClub();
		model.addAttribute("Numerous", vo3);
		
		MembersVo member = (MembersVo)session.getAttribute("member");
		if(member != null) {
			log.info("member : {}", member);
		}
		
		if(member != null) {
			
			//로그인시 내 지역으로 모임 출력
			
			String[] array = member.getMember_location().split("\\s");
			// 구 뽑아내기		    
			for(int i=0;i<array.length;i++) {
				String address = array[1];
				
				List<ClubsVo> vo4 = service.getMyClubs(address);
				model.addAttribute("my", vo4);
			}
			
			//로그인시 내 관심사로 모임 출력
			List<ClubsVo> vo5 = service.getMyInterestClub(member.getMember_no());
			model.addAttribute("Interests", vo5);
			
		}
		
		session.setAttribute("member", member);
		return "main";
	}
	
	@GetMapping(value = "/signup")
	public String signup() {
		
		return "/member/signup";
	}
	
	// 로그아웃 처리
	@GetMapping(value = "/signout")
	public String signout(HttpSession session) {
		session.invalidate();
		return "/";
	}
			
	@GetMapping(value = "/joinus")
	public String joinus(HttpSession session) {	
		
		return "/joinus";
	}
	
	
	
}
