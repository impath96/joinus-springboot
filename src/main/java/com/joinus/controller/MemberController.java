package com.joinus.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joinus.domain.ClubsVo;
import com.joinus.domain.InterestsVo;
import com.joinus.domain.MembersVo;
import com.joinus.domain.MyClubDto;
import com.joinus.domain.PasswordCheckDto;
import com.joinus.service.ClubService;
import com.joinus.service.InterestService;
import com.joinus.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/member/*")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private InterestService interestService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ClubService clubService;

	// 회원가입 페이지 이동
	@GetMapping("signup")
	public String signup(Model model, HttpSession session) {

		model.addAttribute("member", new MembersVo());
		
		return "/member/signup";
	}

	// 다른 곳에서 동일한 기능 사용 -> 추후 Util 클래스로 분리
	private String parseLocation(String location_name) {
		
		String[] part = location_name.split(" ");
		String savedLocation = part[0];
		for (int i = 1; i < 3; i++) {
			savedLocation += " " + part[i];
		}
		return savedLocation;
	}

	// 전달 받아야 하는 데이터 : 이메일, 이름, 전화번호, 소셜로그인 타입, 주소, 사진URL, 비밀번호
	@PostMapping(value = "/signup")
	public String signupPost(HttpSession session, @ModelAttribute("member") @Valid MembersVo member,
			BindingResult result) throws Exception {
		
		// 만약 데이터 유효성을 통과하지 못할 경우 다시 회원가입 페이지로 이동
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {

				log.info("error message : {}", error.getDefaultMessage());
			}
			return "/member/signup";
		}
				
		// 실제로 저장할 주소 (뒤에 상세 주소는 제거)
		String parsedLocation = parseLocation(member.getMember_location());
		member.setMember_location(parsedLocation);
		
		// DB에서 회원_no를 auto_increment했기 때문에 이 정보를 포함한 회원 정보를 session에 담아야 한다.
		MembersVo joinMember = memberService.join(member);
		
		// 세션에 사용자 정보 저장
		session.setAttribute("member", joinMember);

		// 관심사 입력을 위해 추가정보 입력 페이지로 이동
		return "redirect:/member/more-info";

	}

	// 이메일 인증 - 따로 분리?
	@ResponseBody
	@PostMapping(value = "/emailAuth")
	public String emailAuth(String email) {
		
		// 1) 6자리 랜덤값 새성
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;

		// 2) 이메일 보내기
		String setFrom = "joinusject@naver.com"; 		// 인증 번호를 전송할 이메일
		String toMail = email; 					 		// 인증 번호를 수신할 이메일
		String title = "회원가입 인증 이메일 입니다.";  // 이메일 제목
		String content = "JoinUs 홈페이지를 방문해주셔서 감사합니다." + "<br><br>" + "인증 번호는 [" + checkNum + "] 입니다." + "<br>"
				+ "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; // 이메일 내용

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Integer.toString(checkNum);

	}

	// 마이 페이지로 이동
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model, @CookieValue(value = "recentViewClub", required = false) Cookie cookie) {
		
		MembersVo member = (MembersVo) session.getAttribute("member");

		// 1) 세션값이 존재하지 않으면 로그인 페이지로 이동
		if (member == null) {
			return "redirect:/member/signin";
		}
		
		if(cookie != null) {
			// 1) String[] 배열을 Integer[] 배열로 변환
			Integer[] array = Arrays.stream(cookie.getValue().split("&"))
					.mapToInt(Integer::parseInt)
					.boxed()
					.toArray(Integer[]::new);
			
			// 2) Integer[] 배열을 List<Integer> 로 변환
			List<Integer> recentViewClub = new ArrayList<Integer>(Arrays.asList(array));
			
			List<ClubsVo> recentViewClubList = clubService.getClubList(recentViewClub);
			
			model.addAttribute("recentViewClubList", recentViewClubList);
		}
		
		// 내 모임 리스트 - 제한 5개
		List<ClubsVo> clubList = clubService.getClubListByMemberNo(member.getMember_no(), 5);
		log.info("clubList : {}", clubList);
		
		// 내가 만든 모임 리스트 - 제한 5개
		List<ClubsVo> myClubList = clubService.getMyClubList(member.getMember_no(), 5);
		log.info("myClubList : {}", myClubList);
		
		model.addAttribute("clubList", clubList);
		model.addAttribute("myClubList", myClubList);
		
		return "/member/mypage";
	}

	// 추가 정보 입력 페이지로 이동
	@GetMapping(value = "/more-info")
	public String moreInfo(Model model, HttpSession session) {

		//
		List<InterestsVo> interestList = interestService.selectInterestAll();
		log.info("관심사 목록 : {}", interestList);

		model.addAttribute("interestList", interestList);
		return "/member/moreInfo";

	}
	
	@PostMapping(value="/more-info")
	public String moreInfoPost(HttpSession session, Model model, @RequestParam("interest") int interest, @RequestParam("location_name") String locationName) {
		
		MembersVo member = (MembersVo)session.getAttribute("member");
		memberService.addInterest(member.getMember_no(), interest);

		// 소셜 로그인으로 회원가입한 회원일 경우 주소 정보를 추가로 등록
		if(locationName != null) {
			MembersVo returnMember = memberService.addLocation(locationName, member.getMember_no());
			session.setAttribute("member", returnMember);
			
		}
		
		return "redirect:/";
	}
	

	// 로그인 페이지로 이동
	// 단순 이메일 로그인을 했을 경우 해당 유저 정보를 세션에 저장
	@GetMapping(value = "/signin")
	public String signInPage() {
		return "/member/signin";
	}

	// 로그인 동작 처리
	// 일반적인 이메일 방식의 로그인
	@PostMapping(value = "/signin")
	public String signIn(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, Model model, RedirectAttributes rattr) {
		
		// 1) 입력받은 정보를 통해 실제 가입된 회원인지 확인
		// 회원찾기에서 비밀번호 암호화까지 다 하자.
		MembersVo findMember = memberService.signIn(email, password);

		// 만약 findMember가 null -> 이메일이나 비밀번호가 틀렸다는 의미
		if (findMember == null) {
			rattr.addFlashAttribute("error", "이메일 또는 비밀번호를 잘못 입력했습니다.");
			return "redirect:/member/signin";
		}

		// 존재하는 회원일 경우 session에 해당 회원정보 저장
		session.setAttribute("member", findMember);

		// 그리고 메인 페이지로 이동
		return "redirect:/";
	}
	
	@ResponseBody
	@PostMapping(value="/reset-pass",  produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> resetPassword(@RequestBody PasswordCheckDto passwordCheckDto, HttpSession session) {
		
		MembersVo member = (MembersVo)session.getAttribute("member");
		ResponseEntity<String> response = null;
		
		if(!passwordCheckDto.equalsPassword(member.getMember_pass())) {
			response = new ResponseEntity<String>("비밀번호를 확인해 주세요", HttpStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		if(!passwordCheckDto.equalsNewPasswordAndConfirm()) {
			response = new ResponseEntity<String>("비밀번호가 일치하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR);

			return response;
		}
		response = new ResponseEntity<String>("정상적으로 처리", HttpStatus.OK);
		MembersVo resetMember = memberService.resetPassword(member.getMember_no(), passwordCheckDto.getNewPassword());
		
		session.setAttribute("member", resetMember);
		return response;
	}
	
	@ResponseBody
	@PostMapping(value = "/leave", produces = "application/text; charset=UTF-8")
	public String leave(HttpSession session) {
		
		session.invalidate();
		
		return "탈퇴완료";
	}
	
	@GetMapping(value = "/signout")
	public String signout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/my-clublist")
	public String myClubList(HttpSession session, Model model) {
		
		MembersVo member = (MembersVo)session.getAttribute("member");

		if(member == null) {
			return "member/signin";
		}
		
		List<MyClubDto> list = memberService.getMyClubList(member.getMember_no());
		model.addAttribute("myClubList", list);
		
		return "/member/myClubList";
	}

}
