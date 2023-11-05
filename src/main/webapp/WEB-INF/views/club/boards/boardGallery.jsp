<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
 
<%@ include file="../../include/header.jsp"%>
<%@ include file="../../include/club_header.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script type="text/javascript">
// 	alert("모임원 체크 : "+${checkMember});
	
	// 모임원만 상세보기 가능
	function linkContent(club_board_no){
		if(${checkMember == 0}){
			alert('모임가입을 해주세요.');
			return false;
		} else if(${checkMember == -1}){
			location.href = "/member/signin";
		} else {
			location.href= "/club/${club_no}/boards/"+club_board_no+"?page=1";
		}
	}

</script>


<!-- Projects Start -->
<div class="container-xxl py-5">
	<div class="container">
		<div class="row g-4 portfolio-container wow fadeInUp" data-wow-delay="0.5s">
			<c:forEach var="image" items="${imageList }">
				<!-- 이미지 크기 355.99*237.32이 템플릿기본(노트북) -->
				<!-- 이미지 크기 407.98*237.31이 템플릿기본(데스크탑) -->
				<div class="col-lg-4 col-md-6 portfolio-item first">
				    <div class="portfolio-img rounded overflow-hidden">
				        <img style="width: 407.98px; height: 237.31px;" class="img-fluid" src="${PageContext.request.contextPath }/resources/upload/boards/${image.club_board_image}">
				        <div class="portfolio-btn">
				            <a class="btn btn-lg-square btn-outline-light rounded-circle mx-1" href="#" onclick="return linkContent(${image.club_board_no});"><i class="fa fa-link"></i></a>
				        </div>
				    </div>
				</div>
			</c:forEach>
		</div>
        
    </div>
</div>
<!-- Projects End -->
    
    
<%@ include file="../../include/footer.jsp"%>
