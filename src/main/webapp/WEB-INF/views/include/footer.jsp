<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <div class="container-fluid bg-dark text-body footer mt-5 pt-2 wow fadeIn" data-wow-delay="0.1s" style="visibility: visible; animation-delay: 0.1s; animation-name: fadeIn;">
        <div class="container py-5">
            <div class="row g-5">
                <div class="col-lg-50 col-md-6">
                    <h5 class="text-white mb-4">About</h5>
                    <p class="mb-2"><i class="fa-brands fa-github me-3"></i>https://github.com/impath96/joinus.git</p>
                    <p class="mb-2"><i class="fa fa-map-marker-alt me-3"></i>ITWILL-BUSAN</p>
                    <p class="mb-2"><i class="fa fa-phone-alt me-3"></i>051 803 0909</p>
                    <div class="d-flex pt-2">
                        <a class="btn btn-square btn-outline-light btn-social" href=""><i class="fab fa-twitter"></i></a>
                        <a class="btn btn-square btn-outline-light btn-social" href=""><i class="fab fa-facebook-f"></i></a>
                        <a class="btn btn-square btn-outline-light btn-social" href=""><i class="fab fa-youtube"></i></a>
                        <a class="btn btn-square btn-outline-light btn-social" href=""><i class="fab fa-linkedin-in"></i></a>
                    </div>
                </div>
                <div class="col-lg-50 col-md-6">
                    <h5 class="text-white mb-4">Contact Us</h5>
                    <i class="fa-solid fa-envelope me-3"></i> Kang SeongMin - xoxomini4@kakao.com <br>
                    <i class="fa-solid fa-envelope me-3"></i> Goh Eunbi - 3unbbb@gmail.com <br> 
                    <i class="fa-solid fa-envelope me-3"></i> Kim Minho - impath96@gmail.com <br>
                    <i class="fa-solid fa-envelope me-3"></i> Kim SeongBin - rkawk0206@gmail.com <br>
                    <i class="fa-solid fa-envelope me-3"></i> Heo Subin - anmedi3623@naver.com <br>
                </div>

            </div>
        </div>
        <div class="container">
            <div class="copyright">
                <div class="row">
                    <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                        © <a href="http://localhost:8088/">JoinUs</a>, All Right Reserved.
                    </div>
                    <div class="col-md-6 text-center text-md-end">
                        <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                         Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                        <br>Distributed By <a href="https://themewagon.com" target="_blank">ThemeWagon</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
	<a href="#" class="btn btn-lg btn-primary btn-lg-square rounded-circle back-to-top"><i class="bi bi-arrow-up"></i></a>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/wow/wow.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/easing/easing.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/waypoints/waypoints.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/counterup/counterup.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/isotope/isotope.pkgd.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/lib/lightbox/js/lightbox.min.js"></script>
    <script src="${PageContext.request.contextPath }/resources/js/main.js"></script>
    <div id="lightboxOverlay" class="lightboxOverlay" style="display: none;"></div>
    <div id="lightbox" class="lightbox" style="display: none;"><div class="lb-outerContainer"><div class="lb-container"><img class="lb-image" src="data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="><div class="lb-nav"><a class="lb-prev" href=""></a><a class="lb-next" href=""></a></div><div class="lb-loader"><a class="lb-cancel"></a></div></div></div><div class="lb-dataContainer"><div class="lb-data"><div class="lb-details"><span class="lb-caption"></span><span class="lb-number"></span></div><div class="lb-closeContainer"><a class="lb-close"></a></div></div></div></div>
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
						const previewImage = document.querySelector('.thumbnail_image');
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
  </body>
 </html>