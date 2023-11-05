<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../../include/header.jsp"%>
<%@ include file="../../include/club_header.jsp" %>


<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		// 등록버튼
		$('#subBtn').click(function(){
			
// 			if(document.fr.board_type_no.value == ''){
// 				document.fr.board_type_no.focus();
// 				return false;
// 			}
			if(!document.fr.club_board_title.value){
				document.fr.club_board_title.focus();
				return false;
			}
//			if(!document.fr.club_board_content.value){
//				document.fr.club_board_content.focus();
//				return false;
//			}
			
		}); //등록버튼
		
		// 취소버튼
		$('#cancelBtn').click(function(){
			location.href = '/club/${club_no}/boards/${club_board_no}';
		});
		
	});
	
</script>


<div class="container-fluid bg-light overflow-hidden px-lg-0">
	<div class="container contact px-lg-0" style="width: 60%">
		<div class="row g-0 mx-lg-0">
			<!-- 			<div class="col-lg-6 contact-text py-5 wow fadeIn" data-wow-delay="0.5s"> -->
			<div class="p-lg-5" align="center">
				<h6 class="text-primary">JoinUs</h6>
				<h1 class="clubWrite_mb-4">게시글</h1>

				<form name="fr" action="" method="post">
<!-- 					<input type="hidden" name="count" id="count" value=""> -->
					<input type="hidden" name="club_no" value="${club_no }">
<%-- 					<input type="hidden" name="member_no" value="${sessionScope.member_no }"> --%>
					<div class="row g-3">
						<div class="col-md-4">
<%-- 						카테고리 번호 : ${board.board_type_no } --%>
							<select class="form-select" style="height: 55px;" id="board_type_no" name="board_type_no">
<!-- 								<option value="">게시글 카테고리</option> -->
								<option value="1" 
									<c:if test="${board.clubBoardsVo.board_type_no == 1 }">selected</c:if>
								>자유글</option>
								<option value="2" 
									<c:if test="${board.clubBoardsVo.board_type_no == 2 }">selected</c:if>
								>정모후기</option>
								<option value="3" 
									<c:if test="${board.clubBoardsVo.board_type_no == 3 }">selected</c:if>
								>공지사항</option>
							</select>
						</div>
						<div class="col-md-8">
							<div class="form-floating">
								<input type="text" class="form-control" id="club_board_title"
									name="club_board_title" placeholder="title" value="${board.clubBoardsVo.club_board_title }"> <label
									for="club_board_title">제목</label>
							</div>
						</div> 
						<div class="col-12">
							<div class="form-floating">
								<textarea class="form-control" placeholder="내용"
									id="club_board_content" name="club_board_content"
									style="height: 100px">${board.clubBoardsVo.club_board_content }</textarea>
								<!-- <label for="club_board_content">내용</label> -->
							</div>
						</div>
						
						<div class="col-12 clubWrite_buttonMargin">
							<input type="submit" class="btn btn-primary rounded-pill py-3 px-5" id="subBtn" value="등록">
							<input type="button" class="btn btn-primary rounded-pill py-3 px-5" style="margin-left: 2em;" id="cancelBtn" value="취소">
						</div>
					</div>
				</form>


			</div>
		</div>
	</div>
</div>
<script src="https://cdn.ckeditor.com/ckeditor5/34.2.0/classic/ckeditor.js"></script>
<script>
	ClassicEditor
		.create(document.querySelector('#club_board_content'))
		.catch(error=>{
			console.error(error);
	});
</script>


<%@ include file="../../include/footer.jsp"%>