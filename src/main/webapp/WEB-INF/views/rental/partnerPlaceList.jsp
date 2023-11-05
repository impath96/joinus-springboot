<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ include file="../include/header.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script>
	
	$(document).ready(function(){
		
		$('#typeSelect').change(function(){
			var type = $(this).val();
			var gu = $('#guSelect').val();
			location.href="${pageContext.request.contextPath}/rental/partnerPlaceList?type="+type+"&location="+gu;
		});
		
		$('#guSelect').change(function(){
			var type = $('#typeSelect').val();
			var gu = $(this).val();
			location.href = "${pageContext.request.contextPath}/rental/partnerPlaceList?type="+type+"&location="+gu;
		});
		
		
	});
	
</script>


<div class="container-xxl py-5">
	<div class="container">
		<div class="row g-5">
		
			<select class="form-select" id="typeSelect">
				<option value="0">전체</option>
				<option value="음악연습실" 
					<c:if test="${type == '음악연습실' }">
						selected
					</c:if>
				>음악연습실</option>
				<option value="공유주방" 
					<c:if test="${type == '공유주방' }">
						selected
					</c:if>
				>공유주방</option>
				<option value="스터디룸" 
					<c:if test="${type == '스터디룸' }">
						selected
					</c:if>
				>스터디룸</option>
				<option value="운동시설" 
					<c:if test="${type == '운동시설' }">
						selected
					</c:if>
				>운동시설</option>
				<option value="카페" 
					<c:if test="${type == '카페' }">
						selected
					</c:if>
				>카페</option>
			</select>

			<select class="form-select" id="guSelect">
					<option value="0">전체</option>
					<c:forEach var="gu" items="${guList }">
						<option value="${gu.location_gu_name }" 
							<c:if test="${gu.location_gu_name == location }">
								selected
							</c:if>
						><c:out value="${gu.location_gu_name }" /></option>
					</c:forEach>
			</select>
			
						
			<div class="row g-4">
				<c:if test="${partnerPlaceList.isEmpty() }">
					<div style="margin-left: 6px;">
						<h2><span style="color: #198754;">${type }</span> <span style="color: #198754;">${location }</span> 에 해당하는 검색결과가 없습니다.</h2>
					</div>
				</c:if>
				<c:forEach var="partnerPlace" items="${partnerPlaceList }">
	                <div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s" onclick=" location.href='/rental/partnerPlaces/${partnerPlace.partner_place_no}'; " style="cursor: pointer;">
	                    <div class="service-item rounded overflow-hidden shadow" style="height: auto;">
	                        <img src="${PageContext.requeset.contextPath }/resources/upload/partner_place/${partnerPlace.partner_place_image}" class="w-100 py-auto" style="height: 225px;">
	                        <div class="position-relative p-4 pt-0" style="height: 8.5em;">
	                            <h4 class="mb-3 py-2">${partnerPlace.partner_place_name }</h4>
	                            <div>
		                            <span>${partnerPlace.partner_place_address }</span>
		                            <div style="float: right;"><fmt:setLocale value="ko_KR"/><fmt:formatNumber type="currency" value="${partnerPlace.partner_place_price }" /><small> 원/시간</small></div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </c:forEach>
        	</div>       
			
			
			
		</div>
	</div>
</div>
    
    
    
    
<%@ include file="../include/footer.jsp"%>