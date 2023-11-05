<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../include/header.jsp"%>

<div class="container-xxl py-5">
	<div class="container">
		<div class="row g-5">
			<div class="row g-4">
				
				<div style="margin-left: 6px;">
					<h2 class="partnerPlaceH2">어떤 공간을 찾고 있나요?</h2>
				</div>
				
				<hr class="placeHr" style="height: 10px;">
			
				
				<div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div id="placeTypeSelect" class="service-item">
                    	<a href="${PageContext.request.contextPath}/rental/partnerPlaceList?type=음악연습실">
	                        <div class="position-relative p-4 pt-0" style="text-align: -webkit-center;">
	                            <div class="service-icon" style="margin-left: 1.5em;">
	                            	<div>
	                                	<img src="${PageContext.requeset.contextPath }/resources/img/음악연습실.png" class="w-100 py-auto">
	                                </div>
	                            </div>
	                            <h4 class="mb-3" style="margin-top: 1em;">음악연습실</h4>
	                        </div>
                        </a>
                    </div>
                </div>
                
				<div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div id="placeTypeSelect" class="service-item">
                    	<a href="${PageContext.request.contextPath}/rental/partnerPlaceList?type=공유주방">
	                        <div class="position-relative p-4 pt-0" style="text-align: -webkit-center;">
	                            <div class="service-icon" style="margin-left: 1.5em;">
	                                <img src="${PageContext.requeset.contextPath }/resources/img/공유주방.png" class="w-100 py-auto">
	                            </div>
	                            <h4 class="mb-3" style="margin-top: 1em;">공유주방</h4>
	                        </div>
                        </a>
                    </div>
                </div>
                
				<div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div id="placeTypeSelect" class="service-item">
                    	<a href="${PageContext.request.contextPath}/rental/partnerPlaceList?type=스터디룸">
	                        <div class="position-relative p-4 pt-0" style="text-align: -webkit-center;">
	                            <div class="service-icon" style="margin-left: 1.5em;">
	                                <img src="${PageContext.requeset.contextPath }/resources/img/스터디룸.png" class="w-100 py-auto">
	                            </div>
	                            <h4 class="mb-3" style="margin-top: 1em;">스터디룸</h4>
	                        </div>
                        </a>
                    </div>
                </div>
                
				<div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div id="placeTypeSelect" class="service-item">
                    	<a href="${PageContext.request.contextPath}/rental/partnerPlaceList?type=운동시설">
	                        <div class="position-relative p-4 pt-0" style="text-align: -webkit-center;">
	                            <div class="service-icon" style="margin-left: 1.5em;">
	                                <img src="${PageContext.requeset.contextPath }/resources/img/운동시설.png" class="w-100 py-auto">
	                            </div>
	                            <h4 class="mb-3" style="margin-top: 1em;">운동시설</h4>
	                        </div>
                        </a>
                    </div>
                </div>
                
				<div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div id="placeTypeSelect" class="service-item">
                    	<a href="${PageContext.request.contextPath}/rental/partnerPlaceList?type=카페">
	                        <div class="position-relative p-4 pt-0" style="text-align: -webkit-center;">
	                            <div class="service-icon" style="margin-left: 1.5em;">
	                                <img src="${PageContext.requeset.contextPath }/resources/img/카페.png" class="w-100 py-auto">
	                            </div>
	                            <h4 class="mb-3" style="margin-top: 1em;">카페</h4>
	                        </div>
                        </a>
                    </div>
                </div>
                
				<div class="col-md-6 col-lg-4 wow fadeInUp" data-wow-delay="0.1s">
                    <div id="placeTypeSelect" class="service-item">
                    	<a href="${PageContext.request.contextPath}/place/placeList">
	                        <div class="position-relative p-4 pt-0" style="text-align: -webkit-center;">
	                            <div class="service-icon" style="margin-left: 1.5em;">
	                                <img src="${PageContext.requeset.contextPath }/resources/img/기타시설.png" class="w-100 py-auto">
	                            </div>
	                            <h4 class="mb-3" style="margin-top: 1em;">비제휴시설</h4>
	                        </div>
                        </a>
                    </div>
                </div>

			
			</div>
		</div>
	</div>
</div>

	
<%@ include file="../include/footer.jsp"%>