<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../include/header.jsp"%>

<script type="text/javascript">
	// 이메일 유효성 체크
	function CheckEmail(str) {                                                 
	     var reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	     if(!reg_email.test(str)) {                            
	          return false;         
	     }                            
	     else {                       
	          return true;         
	     }                            
	}  
	
	// 비밀번호 길이 제한 체크
	function checkPasswordLength(str){
		if(str.length >= 8 && str.length <= 20){
			return true;
		}
		return false;
	}
	
	// 비밀번호 유효성 체크
	function checkPassword(str) {
		const pattern1 = /[0-9]/; // 숫자
		const pattern2 = /[a-zA-Z]/; // 문자
		const pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
		if(!pattern1.test(str) || !pattern2.test(str) || !pattern3.test(str)) {
			return false;
		} 
		return true;
	}
	//
	// jQuery 시작
	$(function() {
		let email_auth_cd = '';
		let isValidPassword = false;
		let isValidEmailAuth = false;
		$('#email_check_btn').click(function() {
			var email = $('#member_email').val(); //email값이 "member_email"인 입력란의 값을 저장
			if (email == '') {
				alert("이메일을 입력해주세요.");
				return false;
			}
			$.ajax({
				url : '/member/emailCheck', //Controller에서 인식할 주소
				type : 'post', //POST 방식으로 전달
				data : {
					email : email
				},
				success : function(cnt) {
					if (cnt != 1) { //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 이메일
						alert("사용가능한 이메일입니다.");
					} else { // cnt가 1일 경우 -> 이미 존재하는 이메일
						alert("이미 사용중인 이메일입니다.");
					}
				},
				error : function() {
					alert("에러입니다");
				}
			});
		});
		
		$('#pass_confirm').click(function() {
			const member_pass = $('#member_pass').val();
			const member_passwordConfirm = $('#member_passConfirm').val();
			if(member_pass === "") {
				alert("비밀번호를 입력해주세요");
				// 포커스 동작 수행
				return false;
			}
			if (member_passConfirm === "" || (member_pass !== member_passwordConfirm) ) {
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			} else {
				alert("비밀번호가 일치합니다.");
				isValidPassword = true;
				if(isValidEmailAuth && isValidPassword){
					$(".e-submit").removeAttr("disabled")
				}
				
			}
		});

		$("#member_pass").focus(function(){
			$(".password-error-check").removeClass("d-none");
		})
		
		$("#member_pass").keyup(function(){
			
			// 비밀번호 유효성 체크 
			const password = $(this).val();
			
			// 먼저 비밀번호 길이 체크 
			if(checkPasswordLength(password)){
				$(".password-length").removeClass("text-danger");
				$(".password-length").addClass("text-success");
			}else {
				$(".password-length").removeClass("text-success");
				$(".password-length").addClass("text-danger");
				isValidPassword = false;
				$(".e-submit").attr("disabled", "disabled")
			}
			
			// 비밀번호 유효성(정규식) 체크
			if(checkPassword(password)){
				$(".password-character").removeClass("text-danger");
				$(".password-character").addClass("text-success");
			}else {
				$(".password-character").removeClass("text-success");
				$(".password-character").addClass("text-danger");
				isValidPassword = false;
				$(".e-submit").attr("disabled", "disabled")
			}
			
		}); // 
		
		$("#member_passConfirm").focus(function(){
			$(".password-confirm-check").removeClass("d-none");
		})
		
		$("#member_passConfirm").keyup(function(){
			
			const passwordConfirm = $(this).val();
			const password = $("#member_pass").val();
			const $span = $("span.password-confirm");
			if(passwordConfirm !== "" && (passwordConfirm === password)){
				$span.removeClass("text-danger");
				$span.addClass("text-success");
				$span.html("비밀번호가 일치합니다.");
			}else {
				$span.removeClass("text-success");
				$span.addClass("text-danger");
				$span.html("비밀번호가 일치하지 않습니다.");
				isValidPassword = false;
				$(".e-submit").attr("disabled", "disabled")
			}
		})
		
		$('#email_auth_confirm').click(function() {
			if ($('#email_auth_key').val() == '') {
				alert("인증번호를 입력해주세요.");
			} else if ($('#email_auth_key').val() != email_auth_cd) {
				alert("인증번호가 일치하지 않습니다.");
				return false;
			} else {
				alert("인증번호가 일치합니다.");
				isValidEmailAuth = true;
				if(isValidEmailAuth && isValidPassword){
					$(".e-submit").removeAttr("disabled")
				}
			}
		});
		$("#email_auth_btn").click(function() {
			var email = $('#member_email').val();
			if (email == '') {
				alert("이메일을 입력해주세요.");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "/member/emailAuth",
				data : {
					email : email
				},
				success : function(data) {
					alert("인증번호가 발송되었습니다.");
					email_auth_cd = data;
				},
				error : function(data) {
					alert("메일 발송에 실패했습니다.");
				}
			}); // ajax끝
		}); // button
	}); // jquery
</script>
<body>
	<!-- Contact Start -->
	<div class="container-fluid bg-light overflow-hidden px-lg-0">
		<div class="container contact px-lg-0" style="width: 60%">
			<div class="row g-0 mx-lg-0">
				<div class="d-flex flex-column my-5 justify-content-center align-items-center">
					<h1 class="text-primary">JOINUS</h1>
					<h2 class="mb-4 fs-1" >회원가입</h2>
					<form:form modelAttribute="member" method="POST" action="/member/signup" class="w-75">
						<div class="col-12 my-3">
							<div class="pt-3">
								<label for="member_email" class="form-label">이메일</label>
								<div class="d-flex">
									<form:input path="member_email" type="email" class="form-control p-2 fs-2 me-3" id="member_email"/>
									<form:errors cssStyle="color: red;" path="member_email"/>
									<button class="btn btn-primary w-50 fs-3"
										type="button" id="email_auth_btn">인증번호 받기</button>
								</div>
							</div>
						</div>
						<div class="col-12 my-3">
							<div class="pt-3">
								<label for="member_email" class="form-label">이메일 인증</label>
								<div class="d-flex">
									<input type="text" class="form-control p-2 fs-2 me-3" id="email_auth_key"
										maxlength="6">
									<button class="btn btn-primary w-50 fs-3"
										type="button" id="email_auth_confirm">이메일 인증</button>
								</div>
							</div>
						</div>
						<div class="col-12 mt-3">
							<div class="py-3">
								<label for="member_email" class="form-label">비밀번호</label>
								<form:input path="member_pass" type="password" class="form-control p-2 fs-2 mb-2" id="member_pass" />
								<div class="password-error-check d-flex flex-column d-none">
				          			<span class="text-danger password-character">영문 대소문자/숫자/특수 문자 3가지 필수 조합</span>
				          			<span class="text-danger password-length">8자 이상 20자 이하 입력</span>
	        					</div>
								<form:errors cssStyle="color: red;" path="member_pass" />
							</div>
						</div>
						<div class="col-12 mb-3">
							<div class="pt-3">
								<label for="member_email" class="form-label">비밀번호 재확인</label>
								<input type="password" class="form-control px-2 pb-2 fs-2 mb-2" id="member_passConfirm">
								<div class="password-confirm-check d-flex flex-column d-none">
				          			<span class="text-danger password-confirm">비밀번호가 일치하지 않습니다.</span>
	        					</div>
								<button class="btn btn-primary px-5 w-100 mt-2 fs-3"
									type="button" id="pass_confirm">비밀번호 확인</button>
							</div>
						</div>
						<div class="col-12 my-3">
							<div class="pt-3">
								<label for="member_email" class="form-label">이름</label>
								<form:input path="member_name" type="text" class="form-control p-2 fs-2 mb-4" id="member_name"/>
								<form:errors cssStyle="color: red;" path="member_name" />
							</div>
						</div>
						<div class="col-12 my-3">
							<div class="pt-3">
								<label for="member_email" class="form-label">주소</label>
								<form:input path="member_location" type="text" class="form-control p-2 fs-2 mb-4" id="member_location" onclick="searchLocation()"/>
								<form:errors cssStyle="color: red;" path="member_location" />
							</div>
						</div>
						<div class="col-12 my-3">
							<input type="submit" value="회원가입"
								class="btn btn-primary mb-4 w-100 e-submit fs-3" disabled>
						</div>
						<hr>
 						<div class="col-12 my-3 d-flex">
							<a href="https://kauth.kakao.com/oauth/authorize?client_id=e56b53633c44d91056a98f83b04e7bfe&redirect_uri=http://localhost:8088/oauth/kakao&response_type=code" class="d-inline-flex w-50 p-4">
								<img alt="카카오 로그인" src="${pageContext.request.contextPath }/resources/img/kakao_login.png" class="w-100">
							</a>
							<a href="https://accounts.google.com/o/oauth2/v2/auth?access_type=offline&prompt=consent&response_type=code&client_id=463761723189-9objhtfnhck66j6bbvi2h0b7hmgnpsf9.apps.googleusercontent.com&redirect_uri=http://localhost:8088/oauth/google&scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email"
								class="d-inline-flex w-50 p-4">
								<img alt="구글 로그인" src="${pageContext.request.contextPath }/resources/img/google_login.png" class="w-100">
							</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
    function searchLocation() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("member_location").value = data.jibunAddress;
                
                var guideTextBox = document.getElementById("guide");
            }
        }).open();
    }
    </script>
	<%@ include file="../include/footer.jsp"%>