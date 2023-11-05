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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="${PageContext.request.contextPath }/resources/css/jquery.datetimepicker.css">
<script src="${PageContext.request.contextPath }/resources/js/jquery.datetimepicker.full.min.js"></script>

<style>
.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:500px;}
#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#fff;}
#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}
</style>

<body> 
<!-- 정모 -->
<div class="container px-5">
	<div class="row g-5">
	
	<!-- 예약 정보 -->
	  <div class="col-md-5 col-lg-4 order-md-last">
        <h4 class="d-flex justify-content-between align-items-center mb-1">
          <span class="text-primary">예약 정보</span>
        </h4>
          <small class="text-mute">버튼을 누르면 자동으로 적용 됩니다.</small> 
       <c:forEach var = "vo" items="${rentalList }" varStatus="status">
        <ul class="list-group mb-3">
          <li class="list-group-item d-flex justify-content-between lh-sm" >
            <div>
            <input type="hidden" id="rental_no_${status.index }" name="rental_no_${status.index }" value="${vo.rentalPlacesVo.rental_places_no }">
              <h6 class="my-2">${vo.rentalPlacesVo.rental_date }</h6> <brㄴ>
              <h6 class="my-2">${vo.partnerPlacesVo.partner_place_name }</h6> <br>
            </div>
              <input type="button" id="${vo.rentalPlacesVo.rental_places_no }" name="rental_${status.index }" class="btn btn-secondary my-4" value='적용하기'>
          </li>
        </ul>
        </c:forEach>
      </div>
    <!-- 예약 정보 -->  
      <div class="col-md-7 col-lg-8">
        <h4 class="mb-3">일정 수정하기</h4>
        <form class="needs-validation" action ="${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/meeting/${meetingList[0].club_meeting_no}/modify" method="post">
          <div class="row g-3">
          
            <div class="col-12">
             <h4> <label for="firstName" class="form-label">제목</label></h4>
              <input type="text" class="form-control" name="club_meeting_title" value="${meetingList[0].club_meeting_title}"  required="">
              <div class="invalid-feedback">
                Valid first name is required. 
              </div>
            </div>
            
            <div class="col-12">
              <h4><label for="address" class="form-label">🗓️ 날짜 & 시간</label></h4>
              <input type="text" class="form-control" id="datetimepicker" name="club_meeting_date" value='${meetingList[0].club_meeting_date}'  required="">
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
            </div>
            
            <div class="col-12">
              <h4><label for="address" class="form-label">🙋 정원</label></h4>
              <input type="number" class="form-control" name="club_meeting_capacity" value="${meetingList[0].club_meeting_capacity}"  required="">
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
            </div>
            
            
            <div>
            <div class="col-12">
              <h4><label for="address" class="form-label">🏩 장소</label></h4>
              	<div class="input-group py-2">
              		<input type="text" class="form-control" id="club_meeting_location" name="club_meeting_location" value="${meetingList[0].club_meeting_location}" >
             		<button type="button" class="btn btn-secondary" id ="search">검색하기</button>
            	</div>
            	<!-- 주소 -->
            	<input type="text" class="form-control py-2" id="club_meeting_address" name="club_meeting_address"  value="${meetingList[0].club_meeting_address}">
				<!-- 지도 -->
				<div class="map_wrap py-2">
					<div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
				<!-- 검색목록 -->
				<div id="menu_wrap" class="bg_white border">
    			<div class="option">
    				<!-- form 태그위치 -->
    			</div>
		    <ul id="placesList"></ul>
		    <div id="pagination"></div>
			</div>
				</div>
			</div>	
            </div>
            
            <div class="col-12">
             <h4> <label for="address2" class="form-label">💲 참가비 <span class="text-muted"></span></label></h4>
              <input type="number" class="form-control" name="club_meeting_dues" value="${meetingList[0].club_meeting_dues}"  required="">
            </div>

          </div>

          <hr class="my-4">

          <h4 class="mb-3">📝 메모</h4>

			<div class="row gy-3">
	            <div class="col-12">
	              <input type="text" class="form-control" id="club_meeting_content" name="club_meeting_content" value="${meetingList[0].club_meeting_content}" placeholder="" required="">
	              <div class="invalid-feedback">
	                Name on card is required
	              </div>
	            </div>
	          </div>
          <hr class="my-4">
	
			<div class="margin">
			
			<div class="btn-group">
			<button type="submit" class="btn btn-secondary btn-flat">수정하기</button>
			</div>
			
			</div>

        </form>
      </div>
    </div>
</div>
<!-- 정모 -->

<script type="text/javascript">
 
$(function(){
	
var location = $('input#club_meeting_location').val();
	
	var markers = [];

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };  

	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 

	// 장소 검색 객체를 생성합니다
	var ps = new kakao.maps.services.Places();  

	// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({zIndex:1});
	
	// 키워드 검색을 요청하는 함수입니다
	function searchPlaces() {

	    var keyword = document.getElementById('club_meeting_location').value;

	    if (!keyword.replace(/^\s+|\s+$/g, '')) {
	        alert('키워드를 입력해주세요!');
	        return false;
	    }

	    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
	    ps.keywordSearch( keyword, placesSearchCB); 
	}

	// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
	function placesSearchCB(data, status, pagination) {
	    if (status === kakao.maps.services.Status.OK) {

	        // 정상적으로 검색이 완료됐으면
	        // 검색 목록과 마커를 표출합니다
	        displayPlaces(data);

	        // 페이지 번호를 표출합니다
	        displayPagination(pagination);

	    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

	        alert('검색 결과가 존재하지 않습니다. 정확한 상호명을 검색해주세요!');
	        return;

	    } else if (status === kakao.maps.services.Status.ERROR) {

	        alert('검색 결과 중 오류가 발생했습니다.');
	        return;
	    }
	}

	// 검색 결과 목록과 마커를 표출하는 함수입니다
	function displayPlaces(places) {

	    var listEl = document.getElementById('placesList'), 
	    menuEl = document.getElementById('menu_wrap'),
	    fragment = document.createDocumentFragment(), 
	    bounds = new kakao.maps.LatLngBounds(), 
	    listStr = '';
	    
	    // 검색 결과 목록에 추가된 항목들을 제거합니다
	    removeAllChildNods(listEl);

	    // 지도에 표시되고 있는 마커를 제거합니다
	    removeMarker();
	    
	    for ( var i=0; i<places.length; i++ ) {

	        // 마커를 생성하고 지도에 표시합니다
	        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
	            marker = addMarker(placePosition, i), 
	            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

	        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
	        // LatLngBounds 객체에 좌표를 추가합니다
	        bounds.extend(placePosition);

	        // 마커와 검색결과 항목에 mouseover 했을때
	        // 해당 장소에 인포윈도우에 장소명을 표시합니다
	        // mouseout 했을 때는 인포윈도우를 닫습니다
	        (function(marker, title) {
	            kakao.maps.event.addListener(marker, 'mouseover', function() {
	                displayInfowindow(marker, title);
	            });

	            kakao.maps.event.addListener(marker, 'mouseout', function() {
	                infowindow.close();
	            });

	            itemEl.onmouseover =  function () {
	                displayInfowindow(marker, title);
	            };

	            itemEl.onmouseout =  function () {
	                infowindow.close();
	            };
	            
	            itemEl.onclick =  function () {
		            // alert('클릭');
		            // console.log(places[$(this).length]);
		            //console.log($(this).text());
		            console.log($(this).text().split('   ')[2]);
					club_meeting_address = $(this).text().split('   ')[2];
					//alert(club_meeting_address);
					$('#club_meeting_address').attr('value',club_meeting_address);
		            };
		            
	        })(marker, places[i].place_name);

	        fragment.appendChild(itemEl);
	    }

	    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
	    listEl.appendChild(fragment);
	    menuEl.scrollTop = 0;

	    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
	    map.setBounds(bounds);
	    
		}

		// 검색결과 항목을 Element로 반환하는 함수입니다
		function getListItem(index, places) {
	
		    var el = document.createElement('li'),
		    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
		                '<div class="info">' +
		                '   <h5>' + places.place_name + '</h5>';
	
		    if (places.road_address_name) {
		        itemStr += '    <span>' + places.road_address_name + '</span>' +
		                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
		    } else {
		        itemStr += '    <span>' +  places.address_name  + '</span>'; 
		    }
		                 
		      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
		                '</div>';           
	
		    el.innerHTML = itemStr;
		    el.className = 'item';
	
		    return el;
		}
	
		// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
		function addMarker(position, idx, title) {
		    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
		        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
		        imgOptions =  {
		            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
		            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
		            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
		        },
		        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
		            marker = new kakao.maps.Marker({
		            position: position, // 마커의 위치
		            image: markerImage 
		        });
	
		    marker.setMap(map); // 지도 위에 마커를 표출합니다
		    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
	
		    return marker;
		}
	
		// 지도 위에 표시되고 있는 마커를 모두 제거합니다
		function removeMarker() {
		    for ( var i = 0; i < markers.length; i++ ) {
		        markers[i].setMap(null);
		    }   
		    markers = [];
		}
	
		// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
		function displayPagination(pagination) {
		    var paginationEl = document.getElementById('pagination'),
		        fragment = document.createDocumentFragment(),
		        i; 
	
		    // 기존에 추가된 페이지번호를 삭제합니다
		    while (paginationEl.hasChildNodes()) {
		        paginationEl.removeChild (paginationEl.lastChild);
		    }
	
		    for (i=1; i<=pagination.last; i++) {
		        var el = document.createElement('a');
		        el.href = "#";
		        el.innerHTML = i;
	
		        if (i===pagination.current) {
		            el.className = 'on';
		        } else {
		            el.onclick = (function(i) {
		                return function() {
		                    pagination.gotoPage(i);
		                }
		            })(i);
		        }
	
		        fragment.appendChild(el);
		    }
		    paginationEl.appendChild(fragment);
		}
	
		// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
		// 인포윈도우에 장소명을 표시합니다
		function displayInfowindow(marker, title) {
		    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
	
		    infowindow.setContent(content);
		    infowindow.open(map, marker);
		}
	
		 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
		function removeAllChildNods(el) {   
		    while (el.hasChildNodes()) {
		        el.removeChild (el.lastChild);
		    }
		}
	
	$("input[name^='rental']").on('click', function(e){
		
			//console.log($(this).attr('id')); 
			var rental_places_no = $(this).attr('id');
		
		$.ajax({
			
			url : '${PageContext.request.contextPath}/club/${clubInfo[0].club_no}/meeting/rental_no/'+rental_places_no,
			type : 'GET',
			contentType : "application/json",
			success : function(data){
			//alert('갔다옴');
				//console.log(data);
				//console.log(data[0].partnerPlacesVo.partner_place_name);
				//console.log(data[0].rentalPlacesVo.rental_date);
				
				var rental_date = data[0].rentalPlacesVo.rental_date;
				var rental_place = data[0].partnerPlacesVo.partner_place_name;
				var rental_address = data[0].partnerPlacesVo.partner_place_address;
				var rental_time = data[0].rentalPlacesVo.rental_time;
				
				$('#datetimepicker').attr('value',rental_date);
				$('#club_meeting_location').attr('value',rental_place);
				$('#club_meeting_address').attr('value',rental_address);
				
				location = $('input#club_meeting_address').val();
				
				searchPlaces();
				
				}
		});//ajax
	
	});//적용하기 버튼 클릭
	
	
	$("button[id^='search']").on('click',function(e){
		//alert('버튼 클릭');

		// 키워드로 장소를 검색합니다
		searchPlaces();
	});
	
});//jquery


</script>

<script>
$(function(){
    	
  jQuery('#datetimepicker').datetimepicker({
		format:'Y-m-d H:i',
	 	lang:'ko',
		minDate: 0,
		step : 30 
	  });
  });
  
	jQuery.datetimepicker.setLocale('kr');


</script>

<script>
var max = ${clubInfo[0].club_capacity }; 
//console.log(max);

$(document).on("keyup", "input[name^=club_meeting_capacity]", function() {
    var val= $(this).val();

    if(val.replace(/[0-9]/g, "").length > 0) {
        alert("숫자만 입력해 주세요.");
        $(this).val('');
    }

    if(val < 1 || val > max) {
        alert("모임의 정원까지만 만날 수 있어요! \n회원님의 모임 정원은 "+max+"명 이예요!");
        $(this).val('');
    }
}); 
</script>

<%@ include file="../../include/footer.jsp"%>
