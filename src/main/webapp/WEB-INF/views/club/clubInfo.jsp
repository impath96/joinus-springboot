<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/club_header.jsp"%>
<link href="${PageContext.request.contextPath }/resources/css/ksm.css" rel="stylesheet">
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ce8d060125bcc89e0c25ee69f6b5c7b0&libraries=services"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script type="text/javascript">
	
		 $(document).ready(function(){
	
			 // 모임가입
			$('#joinClub').click(function(){
				
				var member_no = ${member_no};
						
				if( member_no == null ){
					alert('회원만 모임에 가입할 수 있습니다');
					return false
				}else if(member_no != null){
					
				
				 if(confirm("모임에 가입하시겠어요?") == true){
					
					$.ajax({
						url:'${pageContext.request.contextPath}/club/${clubvo.club_no}',
						type:'POST',
						data: {member_no},
						dataType: 'json',
						success: function(){
						},
						fail: function(data){
				                alert('failed');
							location.reload();

				        }

				        });
							alert(' 저희 모임에 오신걸 환영합니다! ');
							location.reload();
				    
				}else{
				        return false;
				    } 
				}				

				});
		
			
				// 별점
				$('#rate1,#rate2,#rate3,#rate4,#rate5').click(function(){
					
						var rate = $(this).val();
						
					 if(confirm(" 우리 모임에 "+rate+"점을 주시겠어요? ") == true){
						
						$.ajax({
							
							url:'${pageContext.request.contextPath}/club/${clubvo.club_no}/grade',
							type:'POST',
							data: {
								'club_no' : '${clubvo.club_no}',
								'member_no' : '${member_no}',
								'club_grade_rate' : rate
								},
							dataType: 'json'
						});
							alert('참여해주셔서 감사합니다');
							location.reload();
					 }else{
					        return false;
					    } 
					 
				});
			
		
				// 찜
				$('#Dip').click(function(){
					
					var member_no = ${member_no};
					
					if( member_no == null ){
						alert('회원만 찜기능을 이용할 수 있습니다');
						return false
					}else{
						
					if(confirm(" 우리모임을 찜하시겠어요? ") == true){
						
						$.ajax({
							
							url:'${pageContext.request.contextPath}/club/${clubvo.club_no}/dip',
							type:'POST',
							data: {
								'club_no' : '${clubvo.club_no}',
								'member_no' : member_no
								},
							dataType: 'json'
							
						});
							alert('우리 모임을 찜하셨습니다');
							location.reload();
					 }else{
					        return false;
					    } 
					
					
					}
					
				});
				
				
				// 찜취소
				$('#DDone').click(function(){
					
					var member_no = ${member_no};
					
					if(confirm(" 찜하기를 취소하시겠어요? ") == true){
						
						$.ajax({
							
							url:'${pageContext.request.contextPath}/club/${clubvo.club_no}/dipX',
							type:'POST',
							data: {
								'club_no' : '${clubvo.club_no}',
								'member_no' : member_no
								},
							dataType: 'json'
							
						});
							alert('찜하기가 취소되었습니다');
							location.reload();
					 }else{
					        return false;
					    } 
				
				 });
				
		 
		 // 정모참석
			$("[id='JoinMeeting']").on('click',function(){
				
			var meeting_no = $(this).prop('value');
			 console.log("정모번호: "+ meeting_no);
				
			if( ${member_no} == null ){
					alert('회원만 정모에 참석할 수 있습니다');
					return false
				}else if(${member_no} != null){
					
				
				 if(confirm("정모에 참석하시겠어요?") == true){
					
					$.ajax({
						url:'${pageContext.request.contextPath}/club/${clubvo.club_no}/meeting/'+meeting_no+'/join',
						type:'POST',
						data: {
							'club_no' : '${clubvo.club_no}',
							'member_no' : '${member_no}',
							'club_meeting_no' : meeting_no
							},
						dataType: 'json',
						success: function(data){
								
							if(data == true){
							alert(' 정모참석 신청이 완료되었습니다! ');
								 console.log("정모신청완료");
								 location.reload();
							}
							if(data == false){
								alert('죄송합니다 인원초과로 정모가 마감되었습니다');
							}
						},
						fail: function(){
				              alert('정모참석 신청이 마감되었습니다');
							location.reload();

				        }

				        });
							location.reload();
				    
				}else{
				        return false;
				    } 
				}			 

				});
				
			 // 정모참석 취소
			$("[id='outMeeting']").click(function(){
				
				
			var meeting_no = $(this).prop('value');
			 console.log("정모번호: "+ meeting_no);
				
			if( ${member_no} == null ){
					alert('회원만 정모에 참석할 수 있습니다');
					return false
				}else if(${member_no} != null){
					
				
				 if(confirm("정모참석을 취소하시겠어요?") == true){
					
					$.ajax({
						url:'${pageContext.request.contextPath}/club/${clubvo.club_no}/meeting/'+meeting_no+'/leave',
						type:'POST',
						data: {
							'club_no' : '${clubvo.club_no}',
							'member_no' : '${member_no}',
							'club_meeting_no' : meeting_no
							},
						dataType: 'json',
						success: function(){
						},
						fail: function(data){
				              alert('failed');
							location.reload();

				        }

				        });
							alert(' 정모 참석 신청이 취소되었습니다! ');
							location.reload();
				    
				}else{
				        return false;
				    } 
				}			 
				});
			});
</script>
<body>
 <div class="container-xxl py-5 ">
        <div class="container"> 
    			<!-- About Start  모임 설명 -->
    			<div class="row g-4 text-center justify">
                    
		                     
                 	    <c:if test="${!empty clubvo.club_image }">
							<img src="${PageContext.requeset.contextPath }/resources/upload/clubs/${clubvo.club_image}" id="club_image" class="shadow"><br>
						</c:if>
                  			  <!-- 찜기능 -->	
							 <c:if test="${!empty member_no}">	
		                        <c:if test="${empty dipMember }">
			                      	  <p class="joinusBox3"><img src="../resources/img/heart.png" id="Dip" ><p>
		                        </c:if>
		                       <c:forEach var="dip" items="${dipMember}">
		                        <c:if test="${dip eq member_no }">
			                     	   <p class="joinusBox3"><img src="../resources/img/heartt.png"  id="DDone" ></p>
		                        </c:if>
		                       </c:forEach>
		                     </c:if>
                    
                        <h1 class="mb-4 my-sm-5" id="club_name">${clubvo.club_name }
                        </h1>
    			 </div>
    		
               
               <!--  모임멤버면 별점, 별점 후 평균값 / 멤버가 아니면 가입하기 버튼 /  --> 
				
			<c:if test="${!empty member_no}"><!-- 회원인 경우만 출력 -->
					<c:set var="loop_flag" value="false" />
					<c:forEach var="items" items="${ban }" varStatus="status">
						<c:if test="${not loop_flag }">
							<c:if test="${ graded eq '0' &&  clubmember eq '0' && items ne member_no}">
								<div class="clubInfo_tag" >
									<div class="clubInfo_Box marginForInfo_join" id="joinClub">모임가입하기</div>
								</div>
								<c:set var="loop_flag" value="true" />
							</c:if>
						</c:if>
						</c:forEach>
				
					<!-- 모임X 벤 당한 회원은 가입버튼X -->
						
					
					
					 <!-- 모임O / 별점X  -->
                	<c:if test="${ graded eq '0' &&  clubmember eq member_no}">
					<div class="clubInfo_tag" >                	
						<div id="grade" align="center" class="marginForInfo_join clubInfo_Box" style="width:350px;">
		             		<form class="mb-0" name="myform" id="myform" method="post" >
								<fieldset>
									<h6 class="fontGreen">우리 모임의 별점을 선택해주세요</h6>
									<input type="radio" name="reviewStar" value="5" id="rate1"><label for="rate1">&#11088;</label>
									<input type="radio" name="reviewStar" value="4" id="rate2"><label for="rate2">&#11088;</label>
									<input type="radio" name="reviewStar" value="3" id="rate3"><label for="rate3">&#11088;</label>
									<input type="radio" name="reviewStar" value="2" id="rate4"><label for="rate4">&#11088;</label>
									<input type="radio" name="reviewStar" value="1" id="rate5"><label for="rate5">&#11088;</label>
								</fieldset>
							</form>
						 </div>
					</div>
					</c:if>
					
					 <!-- 모임O / 별점O -->
              		<c:if test="${ graded ne '0' && clubmember ne '0'}">
              		  <!-- 찜기능 -->	
              		  <div class="clubInfo_tag" > 
						<h6 class="marginForInfo fontGreen clubInfo_Box " style="width:350px;">
							우리모임 평균별점은? <br> (참여자수 : ${gradeAvgCnt[0].cnt}명)
							
							<c:choose>
								<c:when test="${gradeAvgCnt[0].avg eq '1'}">
									<div class="p-2 fs-4"> &#11088; </div>
								</c:when>
								<c:when test="${gradeAvgCnt[0].avg eq '2'}">
								<div class="p-2 fs-4"> &#11088; &#11088; </div>
								</c:when>
								<c:when test="${gradeAvgCnt[0].avg eq '3'}">
									<div class="p-2 fs-4"> &#11088; &#11088; &#11088; </div>
								</c:when>
								<c:when test="${gradeAvgCnt[0].avg eq '4'}">
									<div class="p-2 fs-4"> &#11088; &#11088; &#11088; &#11088; </div>
								</c:when>
								<c:when test="${gradeAvgCnt[0].avg eq '5'}">
									<div class="p-2 fs-4"> &#11088; &#11088; &#11088; &#11088; &#11088; </div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							</h6> 
					</div>
					</c:if>
					
					
                </c:if>
         
                <div class="row g-0 mx-lg-0 shadow-sm" >
                 	      
                 	      	<div class="clubDetailBox" style="width:636px;">
							<h4 class="greenColor shadow-sm" > 모임 정보 </h4>
		                     	
		                     	<div class="clubInfo_a" align="center">
		                     	
		                     	<table class="clubInfoTable">
		                     		<tr>
		                     			<td class="borderRight text-sm-start px-sm-4 fs-5"> &#128173; 정원</td>
		                     			<td class="text-sm-start ps-4 fs-5"> ${clubvo.club_capacity } 명</td>
		                     		</tr>
		                     		<tr>
		                     			<td class="borderRight text-sm-start px-sm-4 fs-5"> &#128173; 모임 가입자수</td>
		                     			<td class="text-sm-start ps-4 fs-5"> ${clubMembercnt } 명</td>
		                     		</tr>
		                     		<tr>
		                     			<td class="borderRight text-sm-start px-sm-4 fs-5"> &#128173; 관심사</td>
		                     			<td class="text-sm-start ps-4 fs-5"> ${interDetail }</td>
		                     		</tr>
		                     		<tr>
		                     			<td class="borderRight text-sm-start px-sm-4 fs-5"> &#128173; 활동지역</td>
		                     			<td class="text-sm-start ps-4 fs-5"> ${clubvo.club_location }</td>
		                     		</tr>
		                     	
		                     	</table>
		                 	    </div>
                 	      	</div>
                 	      	
                 	      	<div class="clubDetailBox" style="width:636px;" >
							<h4 class="greenColor shadow-sm" > 모임 소개 </h4>
								<div class="clubInfo_a" align="center">
         	               <h4 id="club_content" style="white-space: pre-wrap;">${clubvo.club_content }</h4>
         	               		</div>
						 </div>
				</div>
          </div>
          </div>      
                
 	<!-- 정모 -->
 	 <div class="container-xxl py-5 ">
        <div class="container"> 			

                 <div class="text-center mx-auto mb-5 marginTOP ">
                <h6 class="text-primary">${clubvo.club_name } </h6>
                <h1 class="mb-4">정모</h1>
           
	                <!-- 정모만들기 모임장만 보일 수 있도록  -->
	                <c:if test="${clubmemberRole == 'admin'}">
	                <a class="small fw-medium" href="${pageContext.request.contextPath}/club/${clubvo.club_no}/meeting/new">모임장 정모만들기<i class="fa fa-arrow-right ms-2"></i></a>
	                </c:if>
           			 </div><hr><br><br>
           		
   		  <!-- 정모 정보 출력 -->
           <c:if test="${!empty meetings}" >
 			<div class="row g-4 text-center justify" >
 			
 				<!-- 정모 갯수만큼 반복 -->
			    <c:forEach var ="i" begin="0" end="${meetingSize-1}" step="1">
			    
	               <div class="col-md-6 col-lg-4 wow fadeInUp d-flex flex-column " data-wow-delay="0.1s" >
				       
				       <div class="rounded shadow clubDetailBox" onclick="location.href='${PageContext.request.contextPath }/club/${clubvo.club_no}/meeting/${meetings[i].club_meeting_no }'" 
				       		style="cursor: pointer;">
					
		                   <h3 class="greenColor shadow-sm">${meetings[i].club_meeting_title }</h3>
		                   <p class="MainTextSub"><b> &#128694; 인원 </b>${meetings[i].club_meeting_capacity }명</p>
		                   <p class="MainTextSub"><b> &#128176; 회비 </b>${meetings[i].club_meeting_dues }</p>
		                   <p class="MainTextSub"><b> &#128198; 날짜 </b>${meetings[i].club_meeting_date }</p>
		                   <p class="MainTextSub"><b> &#128205; 장소 </b>${meetings[i].club_meeting_location }</p>
			      
			        	
			        <!-- 모임가입시에만 참석여부 버튼 출력 -->
			        <c:if test="${clubmember ne '0' and !empty member_no}">
			        		
			        	<!-- 정모 인원이 다 차면 두 값을 비교하여 참석마감출력 -->
			        	<c:set var="meetingFull" value="${meetingCnt[i].cntNo}" />
			        	<c:set var="meetingCapa" value="${meetings[i].club_meeting_capacity}" />
			        	
		       		 <c:choose>	
							<c:when test="${empty meetingMbrs}">
									<c:if test="${meetingCapa eq meetingFull or meetingCapa < meetingFull }">
			        					<div style="color: red; padding: 6px; font-size: 1.2em;">참석마감</div>
			        				</c:if>
			        				<c:if test="${meetingCapa > meetingFull}">
			        				<button id="JoinMeeting" class="btn btn-primary" value="${meetings[i].club_meeting_no}">참석하기</button>
			        				</c:if>
               				</c:when>
							<c:otherwise>
						  	  <c:set var="tmp"  value="0"/>
					    	    <c:forEach var="mm" items="${meetingMbrs}">
					    	      <c:set var="tmp" value="${tmp + ((meetings[i].club_meeting_no eq mm.club_meeting_no)? '1':'0')}" />
		                  	 	</c:forEach>
	                    	
                         		<c:choose>
								<c:when test="${tmp >= 1}">
	                    			 <button id="outMeeting" class="btn btn-primary" value="${meetings[i].club_meeting_no}">참석취소하기</button>
                    			</c:when>
	                    		<c:when test="${tmp < 1}">
	                    			<c:if test="${meetingCapa eq meetingFull}">
			        					<div style="color: red; padding: 6px; font-size: 1.2em;">참석마감</div>
			        				</c:if>
			        				<c:if test="${meetingCapa ne meetingFull}">
	                    			  <button id="JoinMeeting" class="btn btn-primary" value="${meetings[i].club_meeting_no}">참석하기</button>
	                    			</c:if>
	                    		</c:when>
		                    	<c:otherwise>
		                    	   ERR
		                    	</c:otherwise>
				       	 		</c:choose>
                   			 </c:otherwise>	
	                 </c:choose>
	                 	
	                 	
                    </c:if>
                 	
                 	
               			 </div>
               		 </div>
                   </c:forEach>
                     </div>
                </c:if>
                
                
                <c:if test="${empty meetings}" >
                 <div class="g-5" align="center" >
               	 	<div class="col-md-6 col-lg-3 wow fadeIn clubInfoMeetingSize" data-wow-delay="0.1s" >
               	 		 <div class="align-items-center mb-4" >
               	  			<h4 class="mainGr"> 생성된 정모가 없습니다 </h4>
                		 </div>
             	    </div>
                 </div>
                </c:if>
                
               </div> 
                
           

	<div class="container-xxl py-5 marginTOP">
	    <div class="text-center mx-auto mb-5 wow fadeInUp " data-wow-delay="0.1s">
		    <h6 class="text-primary">주 활동 지역</h6>
		    	<h1 class="mb-4">자주 만나는 위치</h1>
				<div><hr><br><br>

				<div id="map" style="width:80%;height:500px; border-radius: 40px;" class="m-auto marginTOP"></div>
				
			</div> 
		
		</div>
    </div>

    <!-- 주활동 지역 -->

    <!-- 사진 -->
          <div class="text-center mx-auto mb-5 marginTOP ">
                <h6 class="text-primary">${clubvo.club_name }</h6>
                <h1 class="mb-4">활동사진</h1>
            </div><hr><br><br>
            
         <c:if test="${!empty boards}" >
        
         <div class="row g-4 text-center justify">
                <c:forEach var="b" items="${boards }">
                <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.3s">
                    <div class="team-item rounded overflow-hidden">
                            <img src="${PageContext.requeset.contextPath }/resources/upload/boards/${b.club_board_image}" 
                            onclick="location.href='${PageContext.requeset.contextPath }/club/${clubvo.club_no}/boards/${b.club_board_no}?page=1'" 
                            id="club_board_image">
                    </div>
                </div>
                </c:forEach>
            </div>
            </c:if>
             <c:if test="${empty boards}" >
               	 <div class="g-5" align="center" >
               	 	<div class="col-md-6 col-lg-3 wow fadeIn clubInfoMeetingSize" data-wow-delay="0.1s" >
               	 		 <div class="align-items-center mb-4" >
               	  	<h4 class="mainGroup"> 등록된 모임사진이 없습니다 </h4>
                		 </div>
                	 </div>
                </div>
             </c:if>
    </div>
    
    <!-- Team End -->
    
    <script type="text/javascript">
	
</script>
    
<script type="text/javascript">

//마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(); 

// 키워드로 장소를 검색합니다
ps.keywordSearch('${clubvo.club_location}청', placesSearchCB); 

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            displayMarker(data[i]);    
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }       

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    
    // 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });
}

</script>


    
    </body>
	

    
<%@ include file="../include/footer.jsp"%>