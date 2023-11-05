<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ include file="../include/header.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>

<div class="container-xxl py-5">
	<div class="container">
		<div class="row g-5">
				
 			<select class="form-select" id="guSelect">
					<option value="0">부산 원데이 클래스</option>
			</select>
		
			<div class="row g-4">
				<c:forEach var="classList" items="${classList }">
	                <div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s" onclick=" location.href='localhost:8088/place/class/${classList.class_number}'"; style="cursor: pointer;">
	                    <div class="service-item rounded overflow-hidden shadow" style="height: 336px;">
	                        <img src="${classList.class_image}" class="w-100 py-auto" style="height: 225px;">
	                        <div class="position-relative p-4 pt-0">
	                            <h4 class="mb-3 py-2" style="word-break: keep-all;">${classList.class_title }</h4>
	                            <div>
		                            <div style="float: right; margin-bottom: 10px;"><fmt:setLocale value="ko_KR"/>
		                            	<fmt:formatNumber type="currency" value="${classList.class_price }" /><small> 원/시간</small>
		                            </div>
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