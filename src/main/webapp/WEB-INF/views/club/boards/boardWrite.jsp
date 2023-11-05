<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../../include/header.jsp"%>
<%@ include file="../../include/club_header.jsp" %>
<style>
  .ck-editor__editable { height: 400px; }
</style>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>

<script type="text/javascript">
	
	var count = 0;
	
	// jQuery
	$(document).ready(function() {

		// 파일추가버튼
		$('#addImage').click(function() {
			
			count++;
			
			var str = "<span><input type='file' name='file' id='file' style='display: inline; margin-bottom:8px;'></span><br>";
			
			$('#imageGroup').append(str);
			$('#addImage').hide();

		});	// 파일추가
		
		
		// 등록버튼
		$('#subBtn').click(function(){
			
			if(document.fr.board_type_no.value == ''){
				document.fr.board_type_no.focus();
				return false;
			}
			if(!document.fr.club_board_title.value){
				document.fr.club_board_title.focus();
				return false;
			}
//			if(!document.fr.club_board_content.value){
//				document.fr.club_board_content.focus();
//				return false;
//			}
			
			$('#count').val(count);
			
			
			if(count == 0){
				// 파일 X
				$('form').attr({
					action: '${pageContext.request.contextPath }/club/${club_no}/boards/new',
					enctype: ''
				});
				
			} else {
				// 파일 O
// 				alert(document.getElementById("file").files[0].name);
				$('form').attr({
					action: '${pageContext.request.contextPath }/club/${club_no}/boards/fileNew'
				});
				
			}
			
		}); //등록버튼
		

	});	// jQuery
	
	// JS
	
	
</script>


<div class="container-fluid bg-light overflow-hidden px-lg-0">
	<div class="container contact px-lg-0" style="width: 60%">
		<div class="row g-0 mx-lg-0">
			<!-- 			<div class="col-lg-6 contact-text py-5 wow fadeIn" data-wow-delay="0.5s"> -->
			<div class="p-lg-5" align="center">
				<h6 class="text-primary">JoinUs</h6>
				<h1 class="clubWrite_mb-4">게시글</h1>

				<form name="fr" action="" method="post" enctype="multipart/form-data">
<!-- 					<input type="hidden" name="count" id="count" value=""> -->
					<input type="hidden" name="club_no" value="${club_no }">
					<input type="hidden" name="member_no" value="${sessionScope.member.member_no }">
					<div class="row g-3">
						<div class="col-md-4">
							<select class="form-select" style="height: 55px; color: black;"
								id="board_type_no" name="board_type_no">
								<option selected value="">게시글 카테고리</option>
								<option value="1">자유글</option>
								<option value="2">정모후기</option>
								<option value="3">공지사항</option>
							</select>
						</div>
						<div class="col-md-8">
							<div class="form-floating">
								<input type="text" style="color: black;" class="form-control" id="club_board_title"
									name="club_board_title" placeholder="title"> <label
									for="club_board_title">제목</label>
							</div>
						</div> 
						<div class="col-12">
							<div class="form-floating">
								<textarea class="form-control" placeholder="내용"
									id="club_board_content" name="club_board_content"
									style="height: 100px"></textarea>
								<!-- <label for="club_board_content">내용</label> -->
							</div>
						</div>




						<!-- <span style="text-align: left;" class="clubWrite_fileSpan"> -->
						<span class="clubWrite_imageSpan">
							<button type="button" class="btn btn-primary" id="addImage">파일추가</button>
						</span>
						<span id="imageGroup" class="clubWrite_imageGroup"> </span>
						
						
						
						<div class="col-12 clubWrite_buttonMargin">
							<input type="submit" class="btn btn-primary rounded-pill py-3 px-5" id="subBtn" value="등록">
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