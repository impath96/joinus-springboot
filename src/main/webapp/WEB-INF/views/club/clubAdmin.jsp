<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/club_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body> 
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"> </script>
<!-- 클럽 정보 -->
<div class="container px-4 py-5">
	<div class="row featurette py-5">
		<div class="col-md-7 order-md-2">
        	<h1 class="featurette-headin py-3">${clubInfo[0].club_name} </h1>
        	<pre><h3 class="featurette-heading">${clubInfo[0].club_content}</h3></pre>
      	</div>
	    <div class="col-md-5 order-md-1">
	    	<img src="${PageContext.requeset.contextPath }/resources/upload/clubs/${clubInfo[0].club_image}" class="w-100 py-auto"> 
	    </div>
    </div>
    <!-- 클럽정보 수정하기 -->
    <div class="text-end col-md-6 ms-auto">
    	<c:if test="${result == 2}">
    	<button class="btn btn-primary rounded-pill py-3 px-5" type="submit"
    	onclick="location.href='${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/modify';">정보 수정하기</button>
    	</c:if>
    </div>
    <!-- 클럽정보 수정하기 -->
</div>
<!-- 클럽 정보 -->

<!-- 클럽 회원리스트 -->
<div class="container px-4 py-5" id="icon-grid">
	<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
    	<h2 class="pb-2 border-bottom">[${clubInfo[0].club_name}] 모임 멤버</h2>
	</div>
	
    <div class="row row-cols-1 row-cols-md-3 mb-3 py-5">
   		<c:forEach var="vo" items="${clubMemberList }" varStatus="status">
      		<div class="col d-flex align-items-start px-2 py-5">
		      	<div style="height: 105; width: 105;">
		      	<c:if test="${!vo.membersVo.member_image.contains(':') }">
	        	<img src="${PageContext.requeset.contextPath }/resources/upload/members/${vo.membersVo.member_image}" style="height: 105; width: 105;" name = image>
	        	</c:if>
	        	<c:if test="${vo.membersVo.member_image.contains(':') }">
                    <img alt="" class="thumbnail_image" src="${vo.membersVo.member_image}" style="height: 105; width: 105;">
                </c:if>
	        	<script type="text/javascript">
	        	</script>
	        	</div>
	        	<div class="px-3">
	          		<h4 class="d-flex gap-2 w-100 justify-content-between">${vo.membersVo.member_name }</h4>
			        <br>
	          		<input type="hidden" name="ban_member_no_${status.index}" value="${vo.membersVo.member_no }">
	          			<div>
	          			<c:choose>
	          				<c:when test="${vo.clubMembersVo.club_member_role == 'admin'}">
		          				<small class="opacity-50 text-nowrap">&#128081;모임장</small><br>
	          				</c:when>
	          				<c:when test="${vo.clubMembersVo.club_member_role == 'common'}">
		          				<small class="opacity-50 text-nowrap">일반회원</small><br>
	          				</c:when>
	          			</c:choose>
					        <small class="opacity-50 text-nowrap">모임가입일 : ${vo.clubMembersVo.club_member_regdate }</small>
	          			</div>
	        	</div>
	        	<!-- 모임장 권한 -->
	       		<c:if test="${result == 2}">
			        <div class="text-end md-auto ms-auto">
			        <button type="button" class="btn btn-warning px-2 py-1 m-1"
			      		 	onclick="location.href='${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/clubMembers/auth?member_no=${vo.membersVo.member_no }';">모임장 양도</button>
			      	<br>
			        <button type="button" class="btn btn-danger px-2 py-1 m-1"
			        		onclick="location.href='${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/clubMembers/ban?member_no=${vo.membersVo.member_no }';">강퇴</button>
	        	<!-- 모임장 권한 -->
	        		</div>
	       		</c:if>	
	      	</div>
	     </c:forEach>         
    </div>
</div>


<script type="text/javascript">

	var check = '${check}';
	if(check == "BANOK"){
		alert('해당 회원을 강퇴시켰습니다.');
	}
	if(check == "AUTHOK"){
		alert('모임장이 변경되었습니다.');
	}
	if(check == "LEAVEOK"){
		alert('모임을 나가셨습니다.')
	}
	if(check == "MeetingNew"){
		alert('정모생성을 완료했습니다.')
	}
</script>
<!-- 모임회원리스트 -->

<%@ include file="../include/footer.jsp"%>
