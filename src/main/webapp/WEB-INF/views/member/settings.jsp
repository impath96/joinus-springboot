<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<style>

	.file-input {
		height: 100%;
		left : 0;
		opacity : 0;
		outline : none;
		position: absolute;
		top : 0;
		width: 100%;
	}
	.file-label {
		cursor : pointer;
		overflow : hidden;
	}
	.file-cta{
	    width: 200px;
    	height: 200px;
    	border-radius: 50%;
    	padding: 0;
    	border: none;	
    	white-space: nowrap;
    	vertical-align: top;
    	position: relative;
    	display : inline-flex;
    	justify-content : flex-start;
    	line-height: 1.5;
    	align-items : center;
    	flex-direction : column;
	}
	img {
		max-width : 100%;
		display : block;
	}
	.thumbnail_image{
		width : inherit;
		height : inherit;
		object-fit : cover;
		border-radius : 50%;
		padding : 0;
		border : none;
	}
	.input-label {
		font-size : 2rem;
		font-weight : 500;
		color : #929292;
		display : block;
		margin-bottom : 1rem;
	}
	.input {
		padding : 0.5rem 0.5rem;
		height : 60px;
		background: white;
		border : 0;
		border-radius : 10px;
		margin-top : 10px;
		display : inline-flex;
		font-size : 1.5rem;
		justify-content : flex-start;
		line-height : 1.5;
		position : relative;
		vertical-align : top;
		width : 100%;
		max-width : 100%;
	}
	
	.form-control, .form-select {
		font-size : 1.5rem;
	}
	
	
	#location::placeholder {
		color : 9B9B9B;
	}
</style>

<body>
	<!-- Contact Start -->
	<div class="container-fluid bg-light overflow-hidden px-lg-0">
		<div class="container px-lg-0">
			<div class="row g-0 mx-lg-0">
			<div class="col-2 p-0 mt-5 pt-5">
				<aside class="sidebar_left ">
					<div class="menu">
						<p class="text-secondary fs-3">HOME</p>
						<ul class="menu-list m-0 p-0 mb-3" style="list-style:none;">
							<li><a href="${pageContext.request.contextPath}/member/mypage" class="text-dark fs-3"><span>마이페이지</span></a></li>
							<c:if test="${sessionScope.member.member_authority eq 'admin' }">
								<li><a href="${pageContext.request.contextPath }/admin" class="text-dark fs-3"><span>관리자페이지</span></a></li>
							</c:if>
						</ul>
						<p class="text-secondary fs-3">모임 관리</p>
						<ul class="menu-list m-0 p-0 mb-3" style="list-style:none;">
							<li><a href="${pageContext.request.contextPath}/member/my-clublist" class="text-dark fs-3"><span>전체 모임</span></a></li>
							<li><a href="" class="text-dark fs-3"><span>내가 찜한 모임</span></a></li>
							<li><a href="" class="text-dark fs-3"><span>최근 본 모임</span></a></li>
						</ul>
						<p class="text-secondary fs-3">설정</p>
						<ul class="menu-list m-0 p-0 mb-3" style="list-style:none;">
							<li><a href="${pageContext.request.contextPath}/settings/member" class="text-dark fs-3"><span>프로필</span></a></li>
						</ul>
					</div>
				</aside>
			</div>
			<div class="col-10">
			<div class="row">
				<div class="col-lg-10 py-5 wow fadeIn mx-auto"
					data-wow-delay="0.5s"
					style="visibility: visible; animation-delay: 0.5s; animation-name: fadeIn;">
					<div class="p-lg-5 pe-lg-0">
						<h1 class="text-center mb-5">프로필 설정</h1>
						<form action="/settings/profile" id="profile-form" method="post" enctype="multipart/form-data" class="d-flex flex-column justify-content-center align-items-center p-4 my-4 border border-dark ">
							<div class="d-flex align-items-stretch justify-content-start position-relative">
								<label class="file-label d-flex flex-column align-items-stretch justify-content-start position-relative ">
									<input type="file" class="file-input" name="profile_image">
									<span class="file-cta">
										<c:if test="${sessionScope.member.member_image.contains(':') }">
											<img alt="" class="thumbnail_image" src="${member.member_image }">
										</c:if>
										<c:if test="${!sessionScope.member.member_image.contains(':') }">
											<img alt="" class="thumbnail_image" src="${pageContext.request.contextPath }/resources/upload/members/${member.member_image }">
										</c:if>
									</span>
								</label>
							</div>
							<div style="width:100%;" class="mt-3">
								<label for="name" class="input-label">
									<span>닉네임</span>
									<input id="name" type="text" class="input form-control" name="member_name"
											value="${member.member_name }" placeholder="변경할 닉네임을 입력해주세요">
								</label>
							</div>
							<div class="col-12 mb-3">
								<label for="location_name" class="input-label">
									<span>지역</span>
									<input type="text" class="input form-control" id="location" name="location_name"
										placeholder="주소를 입력해주세요" value="${sessionScope.member.member_location }" onclick="searchLocation()" >
								</label>
							</div>
							<input type="submit" class="btn btn-primary fs-3" style="width:100%;" value="저장하기"/>
						</form>
						<div class="p-4 my-4 border border-dark">
							<div class="col-12">
								<div class="form-floating">
									<select name = "interest" id="interest" class="form-select form-select-lg py-2 mb-3" aria-label="interest">
			  							<option selected>관심사 선택</option>
			  							<c:forEach var="interest" items="${interestList}">
											<option value="${interest.interest_no }" <c:if test="${interest.interest_no == memberInterest.interest_no }">selected</c:if>>${interest.interest_name }</option>
			  							</c:forEach>
									</select>
								<button type="button" class="e-interest-save btn btn-primary fs-3 w-100">저장하기</button>
								</div>
							</div>
						</div>
						<c:if test="${sessionScope.member.member_signup_type == 'joinus' }">
						<form class="mt-4 p-4 my-4 border border-dark password-edit" method="post">
							<div class="row g-3 form-floating">
								<div class="col-12 col-sm-12">
									<input type="password" class="form-control border-0" id="password" name="password"
										placeholder="현재 비밀번호" style="height: 55px;">
								</div>
								<div class="col-12 col-sm-12">
									<input type="password" class="form-control border-0" id="new-password" name="new-password"
										placeholder="새 비밀번호" style="height: 55px;">
								</div>
								<div class="col-12 col-sm-12">
									<input type="password" class="form-control border-0" id="new-password-confirm" name="new-password-confirm"
										placeholder="새 비밀번호 확인" style="height: 55px;">
								</div>
								<div class="col-12 col-sm-12">
									<input type="submit" class="btn btn-primary fs-3 e-password-edit" style="width:100%;" value="저장하기"/>
								</div>
							</div>
						</form>
						</c:if>
						<div class="col-12 col-sm-12">
							<input type="button" class="btn btn-primary fs-3 e-leave" style="width:100%;" value="탈퇴하기"/>
						</div>
					</div>
				</div>
				</div>
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
                document.getElementById("location").value = data.jibunAddress;
                
                var guideTextBox = document.getElementById("guide");
            }
        }).open();
    }
</script>
<script>
	$(function(){
		
		// 패스워드 변경 동작 수행
		$('.e-password-edit').click(function(e){
			// 기본 submit 동작 이벤트 제거
			e.preventDefault();
			
			const newPassword = $('#new-password').val();
			const newPasswordConfirm = $('#new-password-confirm').val();
			const currentPassword = $('#password').val();
			
			const resetPasswordInfo = {
					'currentPassword' : currentPassword,
					'newPassword' : newPassword,
					'newPasswordConfirm':newPasswordConfirm
			}
			
			$.ajax({
				url: "/member/reset-pass",
				type: "POST",
				data : JSON.stringify(resetPasswordInfo),
				contentType: "application/json; charset=UTF-8",
				success: function(data){
					alert(data);
					location.reload();
				},
				error : function(error){
					alert(error.responseText);
					location.reload();
				}
			})
		}) // 패스워드 변경 수행
		
		// 탈퇴 동작 수행
		$('.e-leave').click(function(){
			
			if(confirm("정말 탈퇴하시겠습니까??")){
				// 탈퇴할 경우에만 동작하도록 구현
				$.ajax({
					url: "/member/leave",
					type: "POST",
					success: function(data){
						alert(data);
						location.reload();
					}
				})
			}
		}); // 탈퇴 동작 수행 
	})
</script>
	<%@ include file="../include/footer.jsp"%>