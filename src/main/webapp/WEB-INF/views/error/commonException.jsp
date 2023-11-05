<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<body>
<div class="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s" style="visibility: visible; animation-delay: 0.1s; animation-name: fadeInUp;">
        <div class="container text-center">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <i class="bi bi-exclamation-triangle display-1 text-primary"></i><br>
                    <img src="../../resources/img/joinus.png">
                    <h1 class="display-1">404</h1>
                    <h1 class="mb-4">페이지를 찾을 수 없습니다.</h1>
                    <p class="mb-4">10초 후 홈으로 이동합니다!</p>
                    <a class="btn btn-primary rounded-pill py-3 px-5" href="javascript:window.history.back()">돌아가기<i class="fa fa-arrow-right ms-3"></i></a>
<!--                     <a class="btn btn-primary rounded-pill py-3 px-5" href="http://localhost:8088/">돌아가기<i class="fa fa-arrow-right ms-3"></i></a> -->
                </div>
            </div>
        </div>
    </div>
</body>
<%
      response.setHeader("Refresh", "10; url='/'"); 
%>
<%@ include file="../include/footer.jsp"%>
