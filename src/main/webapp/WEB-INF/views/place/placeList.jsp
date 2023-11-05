<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ include file="../include/header.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$('#guSelect').change(function(){
			var gu = $(this).val();
			location.href = "${pageContext.request.contextPath}/place/placeList?location="+gu;
		});
	});
</script>

<div class="container-xxl py-5">
	<div class="container">
		<div class="row g-5">
				
			<select class="form-select" id="guSelect">
					<option value="0">비제휴 전체</option>
					<c:forEach var="gu" items="${guList }">
						<option value="${gu.location_gu_name }" 
							<c:if test="${gu.location_gu_name == location }">
								selected
							</c:if>
						><c:out value="${gu.location_gu_name }"/></option>
					</c:forEach>
			</select>
			
			<div class="row g-4">
				<c:forEach var="place" items="${placeList }">
	                <div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s" onclick=" location.href='/place/${place.place_no}'; " style="cursor: pointer;">
	                    <div class="service-item rounded overflow-hidden shadow" style="height: auto;">
	                        <img src="${PageContext.requeset.contextPath }/resources/upload/place/${place.place_image}" class="w-100 py-auto" style="height: 225px;">
	                        <div class="position-relative p-4 pt-0">
	                            <h4 class="mb-3 py-2">${place.place_name }</h4>
	                            <div>
		                            <span>${place.place_address }</span>
		                            <div style="float: right;"><fmt:setLocale value="ko_KR"/><fmt:formatNumber type="currency" value="${place.place_price }" /><small> 원/시간</small></div>
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