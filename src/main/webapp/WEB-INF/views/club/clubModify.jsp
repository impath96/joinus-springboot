<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/club_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
	.ck-editor__editable { height: 400px; }
	.file-input {
		height: 100%;
		left : 0;
		opacity : 0;
		outline : none;
		position: absolute;
		top : 0;
		width: 100%;
	}
	.file-label {
		cursor : pointer;
		overflow : hidden;
	}
	.file-cta{
    	border-radius: 50%;
    	padding: 0;
    	border: none;	
    	white-space: nowrap;
    	vertical-align: top;
    	position: relative;
    	display : inline-flex;
    	justify-content : flex-start;
    	line-height: 1.5;
    	align-items : center;
    	flex-direction : column;
	}
	img {
		max-width : 100%;
		display : block;
	}

  
  
</style>

<body> 


<!-- 클럽 정보 -->
<div class="container px-4">
	<form action ="${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/modify" method ="post" enctype="multipart/form-data">
	<div class="container">
	    <div class=" align-items-stretch justify-content-start position-relative">
	    	<%-- <img src="${PageContext.requeset.contextPath }/resources/upload/clubs/${clubInfo[0].club_image}" class="w-100 py-auto">
	    	<br>
	    	<input type="file" class="btn btn-secondary btn-default float-right py-2 my-4" name="file"> --%>
	    	<label class="file-label d-flex flex-column align-items-stretch justify-content-start position-relative ">
				<input type="file" class="file-input" name="file">
				<span class="file-cta">
						<img alt="" id="club_image" class="w-75 pb-5" src="${PageContext.requeset.contextPath }/resources/upload/clubs/${clubInfo[0].club_image}">
						<input type="hidden" name="null_image" value="${clubInfo[0].club_image}">

				</span>
			</label>
	    </div>
		<div class="">
		<input type="text" class="form-control border-1 py-3 md-3" style="height: 55px;" value = "${clubInfo[0].club_name}" name="club_name">
		<br>
		<textarea class="form-control border-1" style="resize: none;" rows="15" name="club_content" id="editor_textarea">${clubInfo[0].club_content}</textarea>
      	<br> 
      	</div>
    </div>
    <!-- 클럽정보 수정하기 -->
    <div class="text-end col-md-6 ms-auto">
    	<button class="btn btn-primary rounded-pill py-3 px-5" type="submit">수정하기</button>
    </div>
    </form>
    <!-- 클럽정보 수정하기 -->
</div>
<!-- 클럽 정보 -->


<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"> </script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.2.0/classic/ckeditor.js"></script>

<script>
	ClassicEditor
		.create(document.querySelector('#editor_textarea'))
		.catch(error=>{
			console.error(error);
		});
	
</script>

<script type="text/javascript">
			function readImage(input){
				// input 태그에 파일이 있을 경우
				if(input.files && input.files[0]){
					// 1) 이미지 파일인지 검사
					// 2) FileReader 인스턴스 생성
					const reader = new FileReader();
					// 3) 이미지가 load가 된 경우
					reader.onload = e => {
						if(!checkImageType(input.files[0].name)){
							alert("이미지 파일이 아닙니다.");							
							e.preventDefault();
							return false;
						}
						const previewImage = document.querySelector('#club_image');
						previewImage.src = e.target.result;
					}
					reader.readAsDataURL(input.files[0]);
				}
			}
			
			// jpg, png, gif 파일만 가능하도록 체크
			function checkImageType(fileName) {
			    let pattern = /(.png|.jpg|.jpeg|.gif)$/i;
			    return fileName.match(pattern);
			}
			
			const inputImage = document.querySelector(".file-input");
			if(inputImage){
				inputImage.addEventListener("change", function(e) {
					return readImage(e.target);
				})			
			}
			
</script>

<%@ include file="../include/footer.jsp"%>
