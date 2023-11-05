<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
    
<%@ include file="include/header.jsp"%>
<link href="${PageContext.request.contextPath }/resources/css/ksm.css" rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>


<body>
    
   <!-- Projects Start -->
    <div class="container-xxl py-5 ">
        <div class="container" style="background-color:#F6F7F8; "> 
        
            <div class="text-center mx-auto mb-5 py-5" >
                <img alt="" src="../resources/img/joinusPage.png" style="width: 700px; height: 500px;">
                <h6 class="MainTextSub">세상의 모든 취미</h6>
                <h3 class="greenFont"> JOINUS에서 나와 같은 관심사, 나와 같은 지역의 멤버들과 함께해요!</h3>
                <hr><br>
               <p class="MainSubTitle"> JOINUS는 지역별 관심사별 오프라인 모임을 할 수 있는 커뮤니티 플랫폼서비스입니다.<br>
				함께 책을 읽고 토론하는 독서모임부터, 서핑을 사랑하는 서퍼모임, UMF·Spectrum·WDF등<br>
				페스티벌 마니아 모임, 함께 모여 배우는 공예 핸드메이드 모임, 주말엔 봉사활동하는 봉사모임,<br>
				서로 배우고 시너지를 내는 창업모임 같은 다양한 모임들이 활동하고 있습니다.<br>
				
				JOINUS 크루와 함께 취미생활을 하며 워라밸을 찾고, 소소하지만 확실한 행복을 경험해보세요.<br>
				JOINUS 팀은  ‘커뮤니티가 많아질수록 세상은 더 행복해진다’는 확고한 VISION을 갖고,<br>
				누구나 쉽게 자신의 지역과 관심사에 맞는 모임을 찾을 수 있도록 최선을 다하고 있습니다. </p>
            </div>
          
		</div>
		
     	<div class="row g-4" >
    	       <h1 class="greenFont marginTOP text-center mb-5"> JOINUS는 저희와 만들어갑니다! </h1><hr>
				
				<img alt="" src="../resources/img/pic1.png" 
					style="width: 250px; height: 250px; border-radius: 300px; margin-right: 10px;" class="shadow-sm">
				<img alt="" src="../resources/img/pic2.png" 
					style="width: 250px; height: 250px; border-radius: 300px; margin-right: 10px;" class="shadow-sm">
				<img alt="" src="../resources/img/pic3.png"
					 style="width: 250px; height: 250px; border-radius: 300px; margin-right: 10px;" class="shadow-sm">
				<img alt="" src="${pageContext.request.contextPath }/resources/upload/members/ksb.jpg"
					style="width: 250px; height: 250px; border-radius: 300px; margin-right: 10px;" class="shadow-sm">	
				<img alt="" src="../resources/img/pic5.png" 
					style="width: 250px; height: 250px; border-radius: 300px; " class="shadow-sm">	
				<div style="width: 260px; height: 250px;" class="text-center">
					<p class="joinusGreenBox">Kang SeongMin</p>
					<p class="joinusGreenBox2"> xoxomini4@kakao.com</p>
				</div>
				<div style="width: 260px; height: 250px;"  class="text-center">
					 <p class="joinusGreenBox">Goh Eunbi</p>
					 <p class="joinusGreenBox2">3unbbb@gmail.com</p>
				</div>
				<div style="width: 260px; height: 250px;"  class="text-center">
					<p class="joinusGreenBox">Kim Minho</p>
					<p class="joinusGreenBox2">impath96@gmail.com</p>
				</div>
				<div style="width: 260px; height: 250px;"  class="text-center">
					 <p class="joinusGreenBox">Kim SeongBin</p>
					 <p class="joinusGreenBox2">rkawk0206@gmail.com</p>
				</div>
				<div style="width: 260px; height: 250px;"  class="text-center">
					 <p class="joinusGreenBox">Heo Subin</p>
					 <p class="joinusGreenBox2">anmedi3623@naver.com</p>
				</div>
		</div>
        </div>
        
</body>
	

    
<%@ include file="include/footer.jsp"%>