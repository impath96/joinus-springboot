<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../../include/header.jsp"%>
<%@ include file="../../include/club_header.jsp" %>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ce8d060125bcc89e0c25ee69f6b5c7b0&libraries=services"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"> </script>


<body> 
<!-- 정모 -->
<div class="container px-5 py-5">
	<div class="row g-5 py-5">
	
		<!-- 참가인원 -->
	  <div class="col-md-5 col-lg-4 order-md-last">
        <h4 class="d-flex justify-content-between align-items-center mb-1">
          <span class="text-primary py-5">🌟 현재 참가인원</span>
        </h4>
       <c:forEach var = "vo" items="${meetingMember }" varStatus="status">
        <ul class="list-group mb-3">
          <li class="list-group-item d-flex justify-content-between lh-sm" >
              <h6 class="my-2">${vo.membersVo.member_name }</h6> <br>
          </li>
        </ul>
        </c:forEach>
        
      <div>
      	<c:set var="meetingMemberStatus" value="${meetingMemberStatus }"/>
      	<c:set var="maxMember" value="${meetingList[0].club_meeting_capacity }"/>
      	<c:set var="nowMember" value="${meetingMemberCnt }"/>
      	<c:set var="result" value="${result }"/>

      	<c:if test="${meetingStatus eq '모집중' || meetingStatus eq '마감'}">
      	
			<c:if test ="${meetingMemberStatus eq 0 &&(result eq 1 || result eq 2) && meetingStatus eq '모집중'}">
				<c:if test ="${maxMember gt nowMember}">
					<div class="btn-group">
						<button type="submit" class="btn btn-success btn-flat" id ="join">참가하기</button>
					</div>
 				</c:if>	
			</c:if>
			
			<c:if test ="${meetingMemberStatus eq 1 &&(result eq 1 || result eq 2)}">
				<div class="btn-group">
					<button type="submit" class="btn btn-success btn-flat" id ="cancel">참가 취소하기</button>
				</div>
			</c:if>
			<c:if test ="${result eq 0}">
				<div class="" style="border: 1px;" onclick="location.href='${PageContext.request.contextPath }/club/${club_no}'">
				<br>
					<h5>모임에 가입하면 정모에 참가할 수 있어요!</h5>
					<h5><a>모임에 가입하러 가기 👉</a></h5>
				<br>
				</div>
			</c:if>
		</c:if>	
      </div>
      </div>
      <!-- 참가인원 -->
      
      <div class="col-md-7 col-lg-8">
        <form class="needs-validation" action ="" role ="form" method="get">
        <h4 class="mb-5">${meetingList[0].club_meeting_title}</h4>
          <div class="row g-3">
            <div class="col-12">
              <h4><label for="address" class="form-label">🗓️ 날짜 & 시간</label></h4> 
              <input type="text" class="form-control" id="club_meeting_date" value='${meetingList[0].club_meeting_date}' disabled="disabled">
              </div>
         
            
            <div class="col-12">
              <h4><label for="address" class="form-label">🙋 정원</label></h4>
              <input type="number" class="form-control" name="club_meeting_capacity" value="${meetingList[0].club_meeting_capacity}" disabled="disabled">
            </div>
            
            <div class="col-12">
              <h4><label for="address" class="form-label">🏩 장소</label></h4>
 
              		<input type="text" class="form-control mb-2" id="club_meeting_location" value="${meetingList[0].club_meeting_location}" disabled="disabled">

              		<input type="text" class="form-control" id="club_meeting_address" value="${meetingList[0].club_meeting_address}" disabled="disabled">

            </div>
            
			<div>
				
				<div id="map" style="width:100%;height:350px;"></div>
				
			</div> 
            <div class="col-12">
              <h4><label for="address2" class="form-label">💲 참가비 <span class="text-muted">(선택)</span></label></h4>
              <input type="number" class="form-control" name="club_meeting_dues" value="${meetingList[0].club_meeting_dues}" disabled="disabled">
            </div>

          </div>

          <hr class="my-4">

          <h4 class="mb-3">📝 메모</h4>

          <div class="row gy-3">
            <div class="col-12">
              <input type="text" class="form-control" name="club_meeting_content" value="${meetingList[0].club_meeting_content}" disabled="disabled" required="">
              <div class="invalid-feedback">
                Name on card is required
              </div>
            </div>
          </div>


     </form>
          <hr class="my-4">
	
			<div class="margin">
			<c:set var="result" value="${result }"/>
			<c:set var="meetingStatus" value="${meetingStatus }"/>
			
			<c:if test ="${result eq 2 && (meetingStatus eq '모집중' || meetingStatus eq '마감')}">
			<div class="btn-group">
			<button type="submit" class="btn btn-secondary btn-flat" id ="modify">수정하기</button>
			</div>
			
			<div class="btn-group">
			<button type="submit" class="btn btn-danger btn-flat" id ="delete">삭제하기</button>
			</div>
			
			

			
			<c:if test="${meetingStatus eq '모집중'}">
			<div class="btn-group">
			<button type="submit" class="btn btn-success btn-flat" id ="close">마감하기</button>
			</div>
			</c:if>
			
			<c:if test="${meetingStatus eq '마감'}">
			<div class="btn-group">
			<button type="submit" class="btn btn-success btn-flat" id ="reopen">오픈하기</button>
			</div>
			</c:if>
			
			<c:if test="${meetingStatus eq '마감' || meetingStatus eq '모집중'}">
			<div class="btn-group">
			<button type="submit" class="btn btn-success btn-flat" id ="end">완료</button>
			</div>
			</c:if>
			</c:if>
			</div>
			
			<br>
			
			<div class="btn-group">
			<button type="submit" class="btn btn-success btn-flat" id ="back">모임페이지로 돌아가기</button>
			</div>


      </div>
    </div>
</div>
<!-- 정모 -->

			<script>
			
$(function(){
	
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = {
		        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		        level: 3 // 지도의 확대 레벨
		    };  
		
		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption); 
		var location = $('input#club_meeting_location').val();
		
		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();
		
	$.ajax({
		
		url : '/club/${club_no}/meeting/${club_meeting_no}/address',
		type : 'GET',
		success : function(data){
			console.log(data);
		
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(data, function(result, status) {
		
		    // 정상적으로 검색이 완료됐으면 
		     if (status === kakao.maps.services.Status.OK) {
		
		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
		
		        // 결과값으로 받은 위치를 마커로 표시합니다
		        var marker = new kakao.maps.Marker({
		            map: map,
		            position: coords
		        });
		
		        // 인포윈도우로 장소에 대한 설명을 표시합니다
		        var infowindow = new kakao.maps.InfoWindow({
		            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+location+'</div>'
		        });
		        infowindow.open(map, marker);
		
		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		        map.setCenter(coords); 
		        
		    } //if
		  });//search 
		}	
	});//ajax
	
	var formObj = $('form[role="form"]')
	
	$('#modify').click(function(){

		formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/modify");
		formObj.submit();
	});
	
	$('#delete').click(function(){
		var meetingdelete = confirm("정말 정모를 삭제하시겠어요?");
		
		if(meetingdelete){
		formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/delete");
		formObj.submit();
		}else{
			
		}
	});
	
	$('#close').click(function(){
		formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/close");
		formObj.submit();
	});
	
	$('#reopen').click(function(){
		formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/reopen");
		formObj.submit();
	});
	
	$('#join').click(function(){
	 	formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/join");
		formObj.submit(); 
	
		/*
			if(confirm("정모에 참석하시겠어요?") == true){
				
				$.ajax({
					url:'${pageContext.request.contextPath}/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/join',
					type:'POST',
					data: {
						'club_no' : '${clubInfo[0].club_no}',
						'member_no' : '${member_no}',
						'club_meeting_no' : '${meetingList[0].club_meeting_no}'
						},
					dataType: 'json',
					success: function(data){
							
						if(data == true){
							alert(' 정모참석 신청이 완료되었습니다! ');
							 console.log("정모신청완료");
						}
						if(data == false){
							alert('죄송합니다 인원초과로 정모가 마감되었습니다');
						}
					},
					fail: function(){
			              alert('정모참석 신청이 마감되었습니다');
			        }

			        });
			    
			}else{
			        return false;
			    } 
		*/
	
			});
		
	
	$('#cancel').click(function(){
		formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/cancel");
		formObj.submit();
	});
	
	$('#back').click(function(){
		formObj.attr("action", "/club/${clubInfo[0].club_no}");
		formObj.submit();
	});
	
	$('#end').click(function(){
		var end = confirm("정모 완료는 취소할 수 없어요! 정말 완료로 변경하시겠어요?");
		
		if(end){
			formObj.attr("action", "/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/end");
			formObj.submit();
		}else{
			
		}
		
	});
	
});//jquery


var check ='${check}';
if(check == "Close"){
	alert('정모가 마감됐어요!');
}
if(check == "Reopen"){
	alert('정모가 다시 열렸어요!');
}
if(check == "JOIN"){
	alert('정모에 참가했어요!');
}
if(check == "CANCEL"){
	alert('정모 참석을 취소했어요!');
}
if(check == "END"){
	alert('정모가 완료 됐어요!');
}





</script>



<%@ include file="../../include/footer.jsp"%>
