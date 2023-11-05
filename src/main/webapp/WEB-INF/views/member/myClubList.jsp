<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>


<body>
	<!-- Contact Start -->
	<h2 class="text-center fs-1 my-5">내 모임 페이지</h2>
	<div class="container-xxl py-5">
		<div class="container">
		<div class="row g-4">
			<div class="col-2 p-0">
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
		<div class="row g-4">
		<c:forEach var="myClub" items="${myClubList }">
			<div class="col-lg-6 col-xl-4 wow fadeInUp" data-wow-delay="0.1s">
				<div class="service-item rounded overflow-hidden shadow">
					<!-- 클럽 대표 이미지 -->
					<img
						src="${PageContext.requeset.contextPath }/resources/upload/clubs/${myClub.club_image}"
						class="w-100 py-auto">
					<div class="position-relative p-4 pt-0">
						<div class="service-icon">
							<!-- 클럽관심사 아이콘  -->
							<img
								src="${PageContext.requeset.contextPath }/resources/upload/interests/${myClub.interest_icon }"
								class="w-75 py-auto">
						</div>
						<h4 class="mb-3 py-2">${myClub.club_name }</h4>
						<p>${vo.clubsVo.club_content }</p>
						<a class="small fw-medium"
							href="${PageContext.request.contextPath }/club/${myClub.club_no}">클럽
							상세 페이지<i class="fa fa-arrow-right ms-2"></i>
						</a>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
		 </div>
	</div>
		</div>
	</div>
	

	<%@ include file="../include/footer.jsp"%>