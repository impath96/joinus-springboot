<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>
<style>
		#location::placeholder {
		color : 9B9B9B;
	}
</style>
<body>
	<!-- Contact Start -->
	<div class="container-fluid bg-light overflow-hidden px-lg-0">
		<div class="container contact px-lg-0" style="width: 60%">
			<div class="row g-0 mx-lg-0">
				<div class="p-lg-5 ps-lg-0" align="center">
					<h6 class="text-primary">JOINUS</h6>
					<h1 class="mb-4">추가정보(필수)</h1>
					<form action="${pageContext.request.contextPath }/member/more-info" name="form" method="post">
						<div class="col-12">
							<div class="form-floating w-50">
								<select name = "interest" id="interest" class="form-select form-select-lg mb-3 pt-2" aria-label="interest">
		  							<option selected>관심사 선택</option>
		  							<c:forEach var="interest" items="${interestList}">
										<option value="${interest.interest_no }">${interest.interest_name }</option>
		  							</c:forEach>
								</select>
							</div>
						</div>
						<br>
						<c:if test="${sessionScope.member.member_signup_type != 'joinus' }">
							<div class="col-12 mb-5">
								<div class="form-floating w-50">
									<input type="text" class="input form-control p-3 fs-5" id="location" name="location_name"
											placeholder="주소를 입력해주세요" value="${sessionScope.member.member_location }" onclick="searchLocation()" >
								</div>
							</div>
						</c:if>
						<c:if test="${sessionScope.member.member_signup_type == 'joinus' }">
							<div class="col-12 mb-5">
								<div class="form-floating w-50">
									<input type="hidden" class="input form-control p-3 fs-5" id="location" name="location_name"
											placeholder="주소를 입력해주세요" value="${sessionScope.member.member_location }" onclick="searchLocation()" >
								</div>
							</div>
						</c:if>
						<div class="col-12">
							<input type="submit" value="다음"
								class="btn btn-primary rounded-pill py-3 px-5">
						</div>
					</form>
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
	<%@ include file="../include/footer.jsp"%>