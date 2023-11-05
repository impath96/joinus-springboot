<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/club_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body> 
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"> </script>


<!-- 클럽 회원리스트 -->
<div class="container px-4 py-5" id="icon-grid">
	<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
    	<h2 class="pb-2 border-bottom">[${clubInfo[0].club_name}] 모임 멤버</h2>
    	<c:if test="${result == 1 || result == 2}">
        <h5 class="text-end col-md-6 ms-auto"><a href="${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/leave ">모임 나가기 <i class="fa fa-arrow-right ms-3"></i></a> </h5>
		</c:if>
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
	      	</div>
	     </c:forEach>         
    </div>
</div>


<script type="text/javascript">

	var check = '${check}';
	if(check == "BANOK"){
		alert('해당 회원을 강퇴시켰어요!');
	}
	if(check == "AUTHOK"){
		alert('모임장이 변경되었어요!');
	}
	if(check == "LEAVEOK"){
		alert('모임을 탈퇴했어요!')
	}
	if(check == "LEAVENOT"){
		alert('모임장을 양도해야 탈퇴할 수 있어요!')
	}
	if(check == "MeetingNew"){
		alert('정모가 생성되었어요!')
	}
</script>
<!-- 모임회원리스트 -->

<%@ include file="../include/footer.jsp"%>
