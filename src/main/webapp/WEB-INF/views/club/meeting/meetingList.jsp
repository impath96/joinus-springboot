<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ include file="../../include/header.jsp"%>
<%@ include file="../../include/club_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body> 
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"> </script>
<!-- 정모 정보 -->
<div class="container px-5">
<h4>🐣 모집중 정모</h4>
<hr>           
	<div class="row row-cols-1 row-cols-md-3 mb-3 text-center">
		<c:forEach var="meetingList" items="${meetingList_ing }">
	      <div class="col" id="meetingList"  onclick="location.href='${PageContext.request.contextPath }/club/${club_no}/meeting/${meetingList.club_meeting_no }'">
	        <div class="card mb-4 rounded-3 shadow-sm">
	          <div class="card-header py-3">
	            <h4><a href="${PageContext.request.contextPath }/club/${club_no}/meeting/${meetingList.club_meeting_no }">
	            ${meetingList.club_meeting_title }</a></h4>
	          </div>
	          <div class="card-body">
	            <h6 class="card-title pricing-card-title">${meetingList.club_meeting_date }</h6>
	            <ul class="list-unstyled mt-3 mb-4">
	              <li>${meetingList.club_meeting_location }</li>
	              <li>${meetingList.club_meeting_content }</li>
	            </ul>
	            <button type="button" class="w-100 btn btn-lg btn-outline-primary" disabled="disabled">${meetingList.club_meeting_status }</button>
	          </div>
	        </div>
	      </div>
		</c:forEach>
	</div>	
	<br>
	<h4>🐥 마감된 정모</h4>
	<hr><br>
	<div class="row row-cols-1 row-cols-md-3 mb-3 text-center">	
		<c:forEach var="meetingList" items="${meetingList_close }">
	      <div class="col" id="meetingList" onclick="location.href='${PageContext.request.contextPath }/club/${club_no}/meeting/${meetingList.club_meeting_no }'">
	        <div class="card mb-4 rounded-3 shadow-sm">
	          <div class="card-header py-3">
	            <h4 class="my-0 fw-normal">
	            <a href="${PageContext.request.contextPath }/club/${club_no}/meeting/${meetingList.club_meeting_no }">
	            ${meetingList.club_meeting_title }</a></h4>
	          </div>
	          <div class="card-body">
	            <h6 class="card-title pricing-card-title">${meetingList.club_meeting_date }</h6>
	            <ul class="list-unstyled mt-3 mb-4">
	              <li>${meetingList.club_meeting_location }</li>
	              <li>${meetingList.club_meeting_content }</li>
	            </ul>
	            <button type="button" class="w-100 btn btn-lg btn-outline-warning" disabled="disabled">${meetingList.club_meeting_status }</button>
	          </div>
	        </div>
	      </div>
		</c:forEach>
	</div>	
	<br>
	<h4>🐤 완료된 정모</h4>
	<hr><br>
	<div class="row row-cols-1 row-cols-md-3 mb-3 text-center">
		<c:forEach var="meetingList" items="${meetingList_end }">
	      <div class="col" id="meetingList" onclick="location.href='${PageContext.request.contextPath }/club/${club_no}/meeting/${meetingList.club_meeting_no }'">
	        <div class="card mb-4 rounded-3 shadow-sm">
	          <div class="card-header py-3">
	            <h4><a href="${PageContext.request.contextPath }/club/${club_no}/meeting/${meetingList.club_meeting_no }">
	            ${meetingList.club_meeting_title }</a></h4>
	          </div>
	          <div class="card-body">
	            <h6 class="card-title pricing-card-title">${meetingList.club_meeting_date }</h6>
	            <ul class="list-unstyled mt-3 mb-4">
	              <li>${meetingList.club_meeting_location }</li>
	              <li>${meetingList.club_meeting_content }</li>
	            </ul>
	            <button type="button" class="w-100 btn btn-lg" disabled="disabled">${meetingList.club_meeting_status }</button>
	          </div>
	        </div>
	      </div>
	</c:forEach>
	</div>
</div>
<script type="text/javascript">

var result = ${result};
	
if(result == 3){
		alert('모임 가입 후 사용할 수 있습니다.');
		location.href= "/club/${club_no}";
	}
	
	if(result == 0){
		alert('로그인 후 사용할 수 있습니다.' );
		location.href = "/member/signin";
	}
	
$(function(){
	if(result ==2){
	//alert('ajax!');
	$.ajax({
		url : '${PageContext.request.contextPath}/club/${club_no}/meeting/end',
		type : 'GET',
		contentType : "application/json",
		success : function(data){
			//alert('갔다옴');
			//console.log(data);
			console.log(data.length);
			
			if(data > 0){
			alert("기한이 지난 정모가 있습니다! \n상세페이지에서 완료로 상태를 바꿔 주세요!");
			}
		
		}
		
	});//ajax	
	}//if
})	//jquery
	

</script>
<!-- 모임회원리스트 -->

<%@ include file="../../include/footer.jsp"%>
