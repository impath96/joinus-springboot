<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ include file="../include/header.jsp"%>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=688b30a5f6f90e57956268dc3f172b25&libraries=services"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<div class="container-xxl py-5">
	<div class="container" style="color: black;">
		<div class="row g-5">
			<h1 id="placeName">${place.place_name } <small>(비제휴)</small></h1>
<%-- 			<a href="${PageContext.request.contextPath }/place/placeList" style="color: black;">
	            <small>더 많은 제휴시설 보러가기 <i class="fa fa-arrow-right ms-3"></i> 
	        </small></a> --%>
			
			<div style="width: 70%;">
				<img style="width: 95%; max-height: 550px;" src="${PageContext.request.contextPath }/resources/upload/place/${place.place_image}">
				<div style="margin-top: 3em;" class="font">
					<pre class="boardContent">${place.place_content }</pre>
				</div>
				<hr class="partnerPlaceContentHr">
<%-- 				<div style="margin-bottom: 16px;">
					<i class="fa fa-phone-alt me-3" aria-hidden="true"></i>${place.place_tel }
				</div> --%>
				<div id="placeAddr" style="margin-bottom: 16px;">
					${place.place_address }
					<div class="map_wrap py-2" style="padding-right: 3em; margin-bottom:16px;">
						<div id="map" style="width:100%; height:400px;"></div>
					</div>
				</div>
				
			</div>
			
			<div style="width: 30%; border: 1px solid #32C36C; padding-top: 1em; height: 70%;">
				<form name="fr" action="" method="post">
					<input type="hidden" name="partner_place_no" value="${place_no }">
					<input type="hidden" name="partner_place_name" value="${place.place_name }">
				 	<input type="hidden" name="partner_place_price" value="${place.place_price }">
				
					<div>
						<div style="font-size: x-large; float: left;">
							${place.place_name }
						</div>
 						<div style="color: #32C36C; text-align: right; margin-bottom: 2em;">
 							<span style="font-size: x-large;">
								<fmt:setLocale value="ko_KR"/><fmt:formatNumber type="currency" value="${place.place_price }" />
								<br>
							</span>
							<span style="color: #9B9B9B;"> /시간</span>
						</div>
					</div>
					
					<div style="margin-bottom: 2em;">
						<i class="fa fa-phone-alt me-3" aria-hidden="true"></i>${place.place_tel }
					</div>
					
					<div style="margin-bottom: 2em;">
						날짜 선택
						<select class="form-select" id="rental_time" disabled="disabled">
							<option value="">해당 시설에 문의해주세요.</option>
						</select>
					</div>
					
					<div style="margin-bottom: 2em;">
						시간 선택
						<select class="form-select" id="rental_time" disabled="disabled">
							<option value="">해당 시설에 문의해주세요.</option>
						</select>
					</div>
					
					<div class="payBtn">
						<input type="submit" class="btn btn-primary rounded-pill py-3 px-5" id="subBtn" value="비제휴 시설입니다." disabled="disabled">
					</div>
				</form>
			</div>
			
		</div>
	</div>
</div>

<script>
	var placeAddr = document.getElementById("placeAddr").innerText;
	var placeName =  document.getElementById("placeName").innerText;
	var notice = "<br>해당업체에 문의 해주세요."
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
	    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	    level: 4 // 지도의 확대 레벨
	};  
	
	//지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption);
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(placeAddr, function(result, status) {

	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {

	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

 	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new kakao.maps.Marker({
//	            map: map,
	            position: coords
	        });
 	        
		    // 지도에 표시할 원을 생성합니다
	        var circle = new kakao.maps.Circle({
	            radius: 200, // 미터 단위의 원의 반지름입니다 
	            strokeWeight: 2, // 선의 두께입니다 
	            strokeColor: '#75B8FA', // 선의 색깔입니다
	            strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	            strokeStyle: 'dashed', // 선의 스타일 입니다
	            fillColor: '#CFE7FF', // 채우기 색깔입니다
	            fillOpacity: 0.7,  // 채우기 불투명도 입니다 
	            center : coords	// 위치의 가운데에 원 지정
	        }); 
 	        
	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new kakao.maps.InfoWindow({
	            content: '<div style="width:200px;text-align:center;padding:6px 0;word-break:keep-all;">'+placeName+notice+'</div>'
	        });
	        infowindow.open(map, marker);

	        // 지도에 원을 표시합니다 
	        circle.setMap(map); 
	        
	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	    }
	    
	});    

</script>

<%@ include file="../include/footer.jsp"%>