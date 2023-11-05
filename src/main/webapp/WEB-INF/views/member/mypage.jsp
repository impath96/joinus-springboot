<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>


<body>
	<!-- Contact Start -->
	<div class="container-xxl  p-0 mt-5">
		<div class="container p-0">
			<div class="row">
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
			<div class="text-center mx-auto mb-5 wow fadeInUp"
				data-wow-delay="0.1s" style="max-width: 600px;">
				<h1 class="mb-4">마이 페이지</h1>
			</div>
			<div class="row g-4">
				<div class="col-md-12 col-lg-6 wow  fadeInUp" data-wow-delay="0.1s">
					<div class="service-item position-relative rounded overflow-hidden p-3 shadow">
						<div style="height:230px;">
							<div class="service-content d-flex flex-column">
								<h4 class="mb-2">${sessionScope.member.member_name }님 프로필</h4>
								<c:if test="${sessionScope.member.member_image.contains(':') }">
									<img alt="" class="thumbnail_image" src="${member.member_image }" width="160", height="160">
								</c:if>
								<c:if test="${!sessionScope.member.member_image.contains(':') }">
									<img alt="" class="thumbnail_image" src="${pageContext.request.contextPath }/resources/upload/members/${member.member_image }"
													width="160", height="160">
								</c:if>
								<div class="small fw-medium top-left-content" >
								
								<c:choose>
									<c:when test="${sessionScope.member.member_tel eq null}">
										&#128222; <small>등록된 전화번호가 없습니다.</small><br>
									</c:when>
									<c:otherwise>
										&#128222; ${sessionScope.member.member_tel }<br>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${sessionScope.member.member_location eq null}">
										&#128681; <small>등록된 주소가 없습니다.</small>
									</c:when>
									<c:otherwise>
										&#128681; ${sessionScope.member.member_location }
									</c:otherwise>									
								</c:choose>
								</div>
								
								<c:choose>
									<c:when test="${sessionScope.member.member_updatedate eq sessionScope.member.member_regdate}">
										<small>회원가입일 : ${sessionScope.member.member_regdate }</small>
									</c:when>
									<c:otherwise>
										<small>회원정보수정일 : ${sessionScope.member.member_updatedate}</small>
									</c:otherwise>
								</c:choose>
								
								<a class="small fw-medium bottom-right" href="/settings/member">프로필 수정하기<i
									class="fa fa-arrow-right ms-2"></i></a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-lg-6 wow  fadeInUp" data-wow-delay="0.1s">
					<div class="service-item position-relative rounded overflow-hidden px-3 py-2 shadow">
						<div style="height:250px;">
							<div class="service-content d-flex flex-column">
								<h4 class="mb-2">내 모임</h4>
								<div class="box-content">
									<c:forEach var="club" items="${clubList }">
									<a href="${pageContext.request.contextPath }/club/${club.club_no}" class="club_item d-flex align-items-center mb-1" style="color:currentColor;">
										<div class="club_name">&nbsp;&#183; ${club.club_name }</div>
									</a>
									</c:forEach>
								</div>
								<a class="small fw-medium bottom-right" href="${pageContext.request.contextPath }/member/my-clublist">내 모임 전체보기<i
									class="fa fa-arrow-right ms-2"></i></a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-lg-6 wow  fadeInUp" data-wow-delay="0.1s">
					<div class="service-item position-relative rounded overflow-hidden px-3 py-2 shadow">
						<div style="height:250px;">
							<div class="service-content d-flex flex-column">
								<h4 class="mb-2">최근 본 모임</h4>
								<div class="box-content">
									<c:forEach var="recentViewClub" items="${recentViewClubList }">
										<a href="" class="club_item d-flex align-items-center mb-1" style="color:currentColor;">
											<div class="club_name">&nbsp;&#183;${recentViewClub.club_name }</div>
										</a>
									</c:forEach>
								</div>
								<a class="small fw-medium bottom-right" href="">최근 본 모임 전체보기<i
									class="fa fa-arrow-right ms-2"></i></a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-lg-6 wow  fadeInUp" data-wow-delay="0.1s">
					<div class="service-item position-relative rounded overflow-hidden px-3 py-2 shadow">
						<div style="height:250px;">
							<div class="service-content d-flex flex-column">
								<h4 class="mb-2">내가 만든 모임</h4>
								<div class="box-content">
									<c:forEach var="myClub" items="${myClubList }">
									<a href="${pageContext.request.contextPath }/club/${myClub.club_no}" class="club_item d-flex align-items-center mb-1" style="color:currentColor;">
										<div class="club_name">&nbsp;&#183; ${myClub.club_name }</div>
									</a>
									</c:forEach>
								</div>
								<a class="small fw-medium bottom-right" href="/member/my-clublist">내 모임 전체보기<i
									class="fa fa-arrow-right ms-2"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="../include/footer.jsp"%>