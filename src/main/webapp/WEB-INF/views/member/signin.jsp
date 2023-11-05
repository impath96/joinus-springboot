<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ include file=".././include/header.jsp"%>
<body>      
<script type="text/javascript">
	if('${error}'){
		alert('${error}');
	}
</script>
<style>
	#email::placeholder,
	#password::placeholder {
		color : currentColor;
		font-size : 1.5rem;
	}
</style>
 <!-- Contact Start -->
    <div class="container-fluid bg-light overflow-hidden px-lg-0" >
        <div class="container contact px-lg-0" style="width: 60%">
            <div class="row g-0 mx-lg-0">
            	<div class="p-lg-5 ps-lg-0" align="center" >
                	<h6 class="text-primary">JOINUS</h6>
                     <h1 class="mb-4">로그인</h1>
                     <form action="/member/signin" method="post">
                         <div class="col-12" >
                             <div class="form-floating w-50">
                                 <input type="email" class="form-control p-2 fs-2" id="email" name="email" placeholder="이메일 주소를 입력해주세요.">
                             </div>
                         </div><br>
                         <div class="col-12">
                             <div class="form-floating w-50">
                                 <input type="password" class="form-control p-2 fs-2" id="password" name="password" placeholder="비밀번호를 입력해주세요.">
                             </div>
                         </div>
                         <br>
                         <div class="col-12" >
                         	<input type="submit" value="로그인" class="btn btn-primary w-50 fs-2">
                         </div>
                         <br>
                     </form>
                     	<hr>
 						<div class="col-12">
							<a href="https://kauth.kakao.com/oauth/authorize?client_id=e56b53633c44d91056a98f83b04e7bfe&redirect_uri=http://localhost:8088/oauth/kakao&response_type=code" >
								<img alt="카카오 로그인" src="${pageContext.request.contextPath }/resources/img/kakao_login.png">
							</a>
							<a href="https://accounts.google.com/o/oauth2/v2/auth?access_type=offline&prompt=consent&response_type=code&client_id=463761723189-9objhtfnhck66j6bbvi2h0b7hmgnpsf9.apps.googleusercontent.com&redirect_uri=http://localhost:8088/oauth/google&scope=https://www.googleapis.com/auth/userinfo.email">
								<img alt="구글 로그인" src="${pageContext.request.contextPath }/resources/img/google_login.png">
							</a>
						</div>
                         <div class="col-12">
                     		<p class="mb-4 fs-3" align="center"> 아직 회원이 아니신가요? <a href="${pageContext.request.contextPath }/member/signup">회원가입하기</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<%@ include file=".././include/footer.jsp"%>
