package com.joinus.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joinus.auth.AuthInfo;
import com.joinus.auth.AuthLogin;
import com.joinus.auth.GoogleLogin;
import com.joinus.auth.KakaoLogin;
import com.joinus.domain.MembersVo;
import com.joinus.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/oauth/*")
public class OauthController {
	
	@Autowired
	private AuthInfo kakaoAuthInfo;
	@Autowired
	private AuthInfo googleAuthInfo;
	@Autowired
	private MemberService memberService;
	
	/* 문제점
	 * 1) OauthController에 너무 많은 로직이 들어있다.
	 *    추후 OauthService를 만들어서 Controller보다 Service 쪽에 로직 구현하기
	 */
	
	// 소셜 로그인 처리
		@GetMapping(value = "/{service}")
		public String oauthSignIn(@PathVariable("service") String service
								, @RequestParam("code") String code
								, Model model
								, HttpSession session
								, RedirectAttributes rattr)throws IOException, InterruptedException, ExecutionException, NoSuchAlgorithmException {
			log.info("소셜 로그인 동작 처리");
			// 1) code를 통해 인증 서버에 access_token 발급 요청
			// 2) 요청 받은 access_token으로 사용자 정보 요청
			// 3) 전달받은 사용자 정보를 통해 필요한 데이터 추출
			// 4) 이메일을 통해 회원가입이 되어있는 사용자인지 판단
			// -1) 회원가입이 되어있지 않은 사용자일 경우 기존 사용자 정보(인증 서버로부터 전달받은 사용자 정보)를 포함하여 추가정보 입력 페이지로 이동
			// -2) 회원가입이 되어있는 사용자일 경우 바로 mypage로 이동 - 나중에 메인 페이지로 변경

			// 참고 1) 로그인 or 회원가입 완료시 사용자 정보를 session에 저장
			// 2) 로그인완료, 회원가입 완료시 access_token 정보를 cookie에 저장??
			// 3) 만약 access_token값이 있거나 session 정보가 있다면 바로 로그인처리
			// 4) 로그아웃을 했더라고 기존 사용자일 경우 바로 로그인 처리
			AuthInfo authInfo = null;
			AuthLogin authLogin = null;

			// 바로 로그인 처리 -> 이미 회원가입 되어있는 사용자라는 의미
			if (session.getAttribute("member") != null) {
				// 그런데 이제 access_token 만료 기간이 종료되었을 때도 고려해야하는데??
				return "redirect:/";
			}

			// 리팩토링 될거 같은데?
			if (service.equalsIgnoreCase("kakao")) {
				authInfo = kakaoAuthInfo;
				log.info("kakao 객체");
				authLogin = new KakaoLogin(authInfo);
			} else {
				authInfo = googleAuthInfo;
				log.info("google 객체");
				authLogin = new GoogleLogin(authInfo);
			}

			// 1),2),3) code를 통해 회원정보 획득
			MembersVo member = authLogin.getMemberInfo(code, session);
			log.info("code로 얻은 회원 정보 : {}", member);
			
			// 4) 전달받은 사용자 정보를 통해 가입자인지 아닌지 판단
			log.info("회원 이메일 주소 : {}", member.getMember_email());
			MembersVo findMember = memberService.findMemberByEmail(member.getMember_email());
			log.info("DB에서 찾은 정보 : {}", findMember);

			// 만약 가입자가 아니면 가입을 시킨다.
			if (findMember == null) {
				log.info("소셜로그인 가입자 정보(회원가입 전) : {}", member);
				MembersVo joinMember = memberService.join(member);
				log.info("회원가인 완료 후 가입자 정보 : {}", joinMember);
				session.setAttribute("member", joinMember);
				return "redirect:/member/more-info";
			}

			session.setAttribute("member", findMember);

			return "redirect:/";
		}

	
}
