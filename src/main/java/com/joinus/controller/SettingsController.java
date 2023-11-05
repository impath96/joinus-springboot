package com.joinus.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import com.joinus.domain.InterestsVo;
import com.joinus.domain.MembersVo;
import com.joinus.service.InterestService;
import com.joinus.service.MemberService;
import com.joinus.util.FileUtils;

@Controller
@RequestMapping("/settings/*")
public class SettingsController {
	@Autowired
	MultipartResolver multipartResolver;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	InterestService interestService;
	
	private static final Logger log = LoggerFactory.getLogger(SettingsController.class);
	
	
	@Autowired
	public SettingsController(MemberService memberService) {
		this.memberService = memberService;
	}

	// 회원 계정 설정 페이지
	@GetMapping("member")
	public String settings(HttpSession session, Model model) {
		
		// 1) 세션에 회원 정보가 없으면 메인 페이지로 리다이랙트
		MembersVo member = (MembersVo)session.getAttribute("member");
		if(member == null) {
			return "redirect:/";
		}
//		MembersVo findMember = memberService.findMemberByNo(member.getMember_no());
//		session.setAttribute("member", findMember);
		log.info("/settings/member -> member : {}", member);
		// 2) mypage 화면에 필요한 데이터 : 관심사, 회원정보(이미지, 닉네임, 비밀번호), 해당 회원의 관심사 정보
		List<InterestsVo> interestList = interestService.selectInterestAll();
		// 해당 사용자의 관심사 정보 출력
		InterestsVo memberInterest = interestService.selectInterestByMemberNo(member.getMember_no());
		log.info("{} 회원의 관심사 : {}", member.getMember_no(), memberInterest);
		model.addAttribute("interestList", interestList);
		model.addAttribute("memberInterest", memberInterest);
		return "/member/settings";
	}
	
	@PostMapping("/profile")
	public String profile(
			@RequestParam("profile_image") MultipartFile file
			, @RequestParam("member_name") String memberName
			, @RequestParam("location_name") String locationName
			, HttpServletRequest request
			, HttpSession session) throws Exception {

		MembersVo member = (MembersVo)session.getAttribute("member");
		log.info("member : {}", member);
		log.info("닉네임 : {}", memberName);
		log.info("지역 : {}", locationName);
		if(member == null) {
			return "redirect:/member/signin";
		}
		String parsedLocation = parseLocation(locationName);

		if(file.getSize() != 0) {
			// 업로드된 파일이 없을 경우 이미지는 변경하지 않는 것으로 간주.
			log.info("upload Post ... originalName={}", file.getOriginalFilename());
			log.info("upload Post ... size={}", file.getSize());
			log.info("upload Post ... contentType={}", file.getContentType());
			
			ServletContext ctx = request.getServletContext();
			String realPath = ctx.getRealPath("/resources/upload/members");
			log.info("실제 파일 저장 경로 : {}", realPath);
			// 실제로 저장된 시스템에서의 파일명 
			// ex) 실제 파일명 : aaa.png
			//     시스템에 저장되는 파일명 : adjbjiasjjaaa_aaa.png(UUID_파일명 형식으로 할 예정)
			// 시스템에 중복되는 파일명을 갖는 파일이 있을 가능성이 있기 때문!!
			
			// realPath 경로에 file을 업로드 한 후 업로드한 파일의 이름을 return
			String savedFileName = FileUtils.uploadFile(realPath, file);
			log.info("savedFileName : {}", savedFileName);
			// DB members 테이블에 member_image 업데이트 - 
			memberService.updateImage(savedFileName, member.getMember_no());
			
		}
		
		// 닉네임은 전달받은 닉네임을 통해 무조건 update 되도록 하기
		memberService.updateName(memberName, member.getMember_no());
		memberService.updateLocation(parsedLocation, member.getMember_no());
		MembersVo updateMember = memberService.findMemberByNo(member.getMember_no());
		session.setAttribute("member", updateMember);
		
		return "redirect:/settings/member";
	}
	
	// 시, 구, 동만 저장하도록 파싱작업 수행
	private String parseLocation(String location_name) {
		String[] part = location_name.split(" ");
		String savedLocation = part[0];
		for (int i = 1; i < 3; i++) {
			savedLocation += " " + part[i];
		}
		return savedLocation;
	}

	// 2명의 사람이 동시에 동일한 파일명의 파일을 올릴 경우 하나의 파일명이 덮어쓰기가 되어진다.
	// 이러한 상황을 고려해서 동기화를 해주는 것이 좋다.
	// 동작 : 전달받은 파일을 특정 위치에 저장
	//	private String uploadFile(String realPath, MultipartFile file) throws IOException {
	//		// UUID_파일명 형식
	//		String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	//		// 경로는 어떤걸로 하지??
	//		File target = new File(realPath, savedFileName); // 어느 경로 : dir, 어떤 파일 : savedFileName
	//		
	//		FileCopyUtils.copy(file.getBytes(), target); // 내용을 파일에 쓰는 동작
	//		return savedFileName;
	//	}
}
