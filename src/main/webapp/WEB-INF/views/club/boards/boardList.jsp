<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../../include/header.jsp"%>
<%@ include file="../../include/club_header.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script type="text/javascript">
// 	alert("접속한 member_no : "+${sessionScope.member_no});
// 	alert("모임원 체크 : "+${checkMember});
	
	$(document).ready(function(){
		
		$('#all').click(function(){
			$(location).attr('href','/club/${club_no}/boards');
		});
		$('#notice').click(function(){
			$(location).attr('href','/club/${club_no}/boards/type/3');
		});
		$('#free').click(function(){
			$(location).attr('href','/club/${club_no}/boards/type/1');
		});
		$('#review').click(function(){
			$(location).attr('href','/club/${club_no}/boards/type/2');
		});
		$('#write').click(function(){
			$(location).attr('href','/club/${club_no}/boards/new');
		});
		
	});
	
	// 모임원만 상세보기 가능
	function linkContent(club_board_no){
		if(${checkMember == 0}){
			alert('모임가입을 해주세요.');			
			return false;
		} else if(${checkMember == -1}){
			if(confirm('로그인 후 이용가능합니다.')){
				location.href = "/member/signin";
			}
		} else {
// 			location.href= "/club/${club_no}/boards/"+club_board_no;
			// 페이지정보 들고가기
			var url = document.location.href;
			console.log(url);
			var query = window.location.search;
			console.log(query);
			var param = new URLSearchParams(query);
			var page = param.get('page');
			console.log(page);
// 			console.log(page == null);
			if(page == null){
				page = 1;
			}
			location.href= "/club/${club_no}/boards/"+club_board_no+"?page="+page;
			
		}
	}
	
	// 삭제메세지
	if(${check == "DELOK" }){
		alert("글이 정삭적으로 삭제되었습니다.");
	}

</script>

<!-- Feature Start -->
<div class="container-xxl py-5">
	<div class="container">
		<div class="row g-5">
		
			<div style="margin-bottom: 2em;">
				<button type="button" class="btn btn-primary" id="all">전체</button>
				<button type="button" class="btn btn-primary" id="notice">공지사항</button>
				<button type="button" class="btn btn-primary" id="free">자유글</button>
				<button type="button" class="btn btn-primary" id="review">정모후기</button>
				<c:if test="${checkMember == 1 }">
					<button type="button" class="btn btn-primary" id="write" style="float: right;">글쓰기</button>
				</c:if>
			</div>


			<c:forEach var="board" items="${boardList }">
				<div class="wow fadeIn" onclick="return linkContent(${board.clubBoardsVo.club_board_no});" style="cursor: pointer; margin-top: 1em;">
				<c:if test="${board.clubBoardsVo.club_board_image != null }">
					<img src="${PageContext.request.contextPath }/resources/upload/boards/sm_${board.clubBoardsVo.club_board_image }" class="clubBoardList_smImage" style="height: 200px;">
				</c:if>
					<h3 class="mb-3" style="display: inline-block;">${board.clubBoardsVo.club_board_title }</h3><br>
<%-- 					<p class="clubBoardList_content">${board.clubBoardsVo.club_board_content }</p> --%>
					<div class="clubBoardList_content">${board.clubBoardsVo.club_board_content }</div>
					<div style="margin-bottom: 1em; display: table;">
						<div style="display: inline-block; vertical-align: middle;">
							<c:if test="${!board.membersVo.member_image.contains(':') }">
								<img class="boardContent_writeImage" src="${PageContext.request.contextPath }/resources/upload/members/${board.membersVo.member_image }">
							</c:if>
							<c:if test="${board.membersVo.member_image.contains(':') }">
			                    <img alt="" class="boardContent_writeImage" src="${board.membersVo.member_image }" style="padding: 0; border: none;">
			                </c:if>
						</div>
						<span style="display: table-cell;" class="font">
							<span class="clubBoardList_writer">
								${board.membersVo.member_name }
<%-- 								<c:if test="${board.clubMembersVo.club_member_role == 'admin' }">&#128081;</c:if> --%>
							</span>
							
							<c:if test="${board.clubBoardsVo.club_board_updatedate == null }">
								<span><fmt:formatDate value="${board.clubBoardsVo.club_board_date }" pattern="yy.MM.dd HH:mm"/> </span>
							</c:if>
							<c:if test="${board.clubBoardsVo.club_board_updatedate != null }">
								<span><fmt:formatDate value="${board.clubBoardsVo.club_board_updatedate }" pattern="yy.MM.dd HH:mm"/></span>
							</c:if>
						</span>
					</div>
					<div class="font">
						<i class="bi bi-heart"></i> <span class="clubBoardList_likeCnt">${board.clubBoardsVo.club_board_likecnt }</span>
						<i class="fa fa-comments fa-fw"></i> <span class=clubBoardList_commentCnt>${board.clubBoardsVo.club_board_commentcnt }</span>
					</div>
				</div>
				<hr style="margin-top: 1em;">
			</c:forEach>
			
			<!-- 페이징블럭 -->
			<div class="d-flex justify-content-center py-3 mt-5">
				<nav aria-label="Page navigation" class="">
	        		<ul class="pagination justify-content-end mb-0">
	                	<c:if test="${pm.prev }">
		                	<li class="page-item">
		                    	<a class="page-link p-0 d-inline-flex align-items-center" style="height: 100%;" href="${PageContext.request.contextPath }/club/${club_no }/boards?page=${pm.startPage-1}" aria-label="Previous">
		                      		<span aria-hidden="true"><i class="fa-solid fa-angles-left px-3 py-3"></i></span>
		                    	</a>
		                  	</li>
		                </c:if>
		                <c:forEach var="idx" begin="${pm.startPage }" end="${pm.endPage }">			
	                 		<li class="page-item <c:out value="${pm.cri.page == idx?'active':'' }"/>" 
	                 				<c:out value="${pm.cri.page == idx?'aria-current = page':'' }"/>
	                 				>
	                 			<a class="page-link d-inline-block p-3" href="${PageContext.request.contextPath }/club/${club_no }/boards?page=${idx}">${idx }</a>
	                 		</li>
						</c:forEach>
						<c:if test="${pm.next && pm.endPage > 0 }">
		                  	<li class="page-item">
		                    	<a class="page-link p-0 d-inline-flex align-items-center" style="height: 100%;" href="${PageContext.request.contextPath }/club/${club_no }/boards?page=${pm.endPage+1}" aria-label="Next">
		                      		<span aria-hidden="true"><i class="fa-solid fa-angles-right px-3 py-3"></i></span>
		                    	</a>
		                  	</li>
						</c:if>
	                </ul>
	        	</nav>
	        </div>
			
			
		</div>
		
	
	</div>
</div>


<%@ include file="../../include/footer.jsp"%>