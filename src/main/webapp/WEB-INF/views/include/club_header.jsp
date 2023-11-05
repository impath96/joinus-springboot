<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">

<div class="col-12 text-center" style="font-size: 1.7rem; font-weight: 800;">
            <ul class="list-inline m-5 py-5" id="portfolio-flters">
            <li class="mx-2" onclick="location.href='${PageContext.request.contextPath}/club/'+${club_no}">모임정보</li>
            <li class="mx-2"  onclick="location.href='${PageContext.request.contextPath}/club/'+${club_no}+'/boards'">모임게시판</li>
            <li class="mx-2"  onclick="location.href='${PageContext.request.contextPath}/club/'+${club_no}+'/gallery'">사진첩</li>
            <li class="mx-2"  onclick="location.href='${PageContext.request.contextPath}/club/'+${club_no}+'/meetingList'">정모기록</li>
            <li class="mx-2"  onclick="location.href='${PageContext.request.contextPath}/club/'+${club_no}+'/clubMembers'">모임멤버</li>
            <c:if test="${clubmemberRole == 'admin' or result == 2}">
            <li class="mx-2"  onclick="location.href='${PageContext.request.contextPath}/club/'+${club_no}+'/clubAdmin'">모임관리</li>
            </c:if>
            </ul>
</div>



    