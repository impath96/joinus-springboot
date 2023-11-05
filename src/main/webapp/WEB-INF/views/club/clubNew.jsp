<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link href="${PageContext.request.contextPath }/resources/css/ksm.css" rel="stylesheet">
<style>
  .ck-editor__editable { height: 400px; }
</style>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	
	//다른관심사출력
	$('#inters').click(function(){
		$('#select,#inters,#hideInter').hide();
		$('#select2').fadeIn();
	});
	
	
	//상세관심사 출력 ajax
	//관심사 텍스트를 클릭하면
	$('#inter,#inter1,#inter2,#inter3,#inter4,#inter5,#inter6,#inter7,#inter8').click(function(){
		
			
			var item = $(this).text();
			var itemNum;
			
			$('#interest').val(item); // 관심사 자동입력
			
			//관심사 넘버로 변환 후 
			if(item == "요리/제조"){ itemNum = 1; }
			else if(item == "봉사활동"){ itemNum = 2;}
			else if(item == "운동/스포츠"){ itemNum = 3;}
			else if(item == "게임/오락"){ itemNum = 4;}
			else if(item == "음악/악기"){ itemNum = 5;}
			else if(item == "아웃도어/여행"){ itemNum = 6;}
			else if(item == "외국/언어"){ itemNum = 7;}
			else if(item == "문화/공연/축제"){ itemNum = 8;}
		
			$('#inter,#inter1,#inter2,#inter3,#inter4,#inter5,#inter6,#inter7,#inter8').unbind('click'); //중복클릭 막기
				
			//상세관심사 텍스트를 출력
		  $.ajax({
			url:'${pageContext.request.contextPath}/club/getdetail',
			data: {itemNum:itemNum},
			datatype: 'json',
			type:'GET',
			success:function(data){
				
				for(i=0;i<data.length;i++){
						if(i == 3 || i == 7){ //한줄에 4개씩
				$('.newForm').append("<li onclick='select(this);'' id='newDetail' class='Inters_Detail'>"+data[i].interest_detail_name+"</li><br>");
						}else{
				$('.newForm').append("<li onclick='select(this);'' id='newDetail' class='Inters_Detail'>"+data[i].interest_detail_name+"</li>");
						}
				}
				
				$('.detail').slideDown(); //숨겨진 detail 출력
				$('html,body').animate({ scrollTop: $('#newDetail').offset().top }, 200); //포커스이동
				
				}
		});
	}); 
	
	// 모임이름 중복체크
	$('#clubName').keyup(function(){
	    var name = $('#clubName').val();
	    var maxLen = 10;
	    console.log(name);
	    $.ajax({
	        data : {
	        	club_name : name
	        },
	        url : "${pageContext.request.contextPath}/club/check_clubname",
	        success : function(result) {
	            if(result== '1') {
	                $("#retry").text("이미 만들어진 모임이름입니다. 다른 이름을 입력하세요.");
	                $("#retry").css("color","red");
	                $('#clubBtn').attr("disabled",true); //등록버튼 비활성화
	            } 
	            if(result == '0') {
	                $("#retry").text("사용가능한 모임이름입니다.");
	                $("#retry").css("color","#32C36C");
	                $('#clubBtn').attr("disabled",false);
	            }
	            if(name == ''){
	            	 $("#retry").text("모임이름을 입력해주세요.");
		             $("#retry").css("color","red");
		             $('#clubBtn').attr("disabled",true);
	            }
	            if(name.length > maxLen){
	            	 $("#retry").text("모임이름은 10자까지 입력가능합니다.");
		             $("#retry").css("color","red");
		             $('#clubBtn').attr("disabled",true);
	            }
	        }
	    });
	    });
	
	
	//유효성체크
	$('#clubBtn').click(function(){
		 // 모임이름, 정원을 필수값으로
		 
		 if($('#clubName').val() == ""){
			 alert('모임의 이름을 입력해주세요');
			 $('#clubName').focus();
			 return false;
		 }
		 if($('#clubcapacity').val() == ""){
			 alert('모임의 정원을 입력해주세요');
			 $('#club_capacity').focus();
			 return false;
		 }
		 
		 
	});
	
	
	$('#clubcapacity').keyup(function(){
		if($('#clubcapacity').val() > 51){
			 $("#retry2").text("모임 인원은 최대 50명입니다.");
			 $("#retry2").css("color","red");
			 $('#clubBtn').attr("disabled",true);
			 $('#club_capacity').focus();
		}
		if($('#clubcapacity').val() < 2){
			 $("#retry2").text("모임 인원은 최소 3명입니다.");
			 $("#retry2").css("color","red");
			 $('#clubBtn').attr("disabled",true);
			 $('#club_capacity').focus();
		}
		if($('#clubcapacity').val() > 2 && $('#clubcapacity').val() < 50 ){
		     $("#retry2").text(" 등록가능한 인원수입니다. ");
		     $("#retry2").css("color","#32C36C");
			 $('#clubBtn').attr("disabled",false);
		}
	});
	 
	
});

  
function select(item){
	  var item = $(item).text();
	  $('.detail2').slideDown();
	  $('#interest_detail').val(item);
	  $('html,body').animate({ scrollTop: $('#location').offset().top }, 200);
} 




</script>


<body>
 <div class="container-xxl py-5 ">
        <div class="container"> 
        
            
    <!-- Contact Start -->
                
        <div id="NewTitle" >
             <h6 class="GreenBox" onclick="window.location.href=window.location.href">모임등록하기</h6> 
        </div>
		<div class="row g-4 text-center justify py-5 shadow" style="background-color: #F6F7F8; border-radius: 50px;">
             
                  <p class="sub2"> 1. 관심사를 선택해주세요 </p><br>   
                   	 	<hr>
            	  <div id="hideInter">
                  <p class="sub3">다음은 ${membervo.member_name}님이 선택하신 관심사입니다 </p>   
                  </div>
                  <div id="select" >
                  			<ul class="interTable ">
                  			<li id="inter" class=" Inters_Detail">${interest.interest_name }</li>
                  			</ul>
                  </div>
                  <div id="select2" >
                  			<ul class="interTable ">
                  			<li id="inter1" class="Inters_Detail ">요리/제조</li>
                  			<li id="inter2" class="Inters_Detail ">봉사활동</li>
                  			<li id="inter3" class="Inters_Detail ">운동/스포츠</li>
                  			<li id="inter4" class="Inters_Detail ">게임/오락</li>
                  			<li id="inter5" class="Inters_Detail ">음악/악기</li>
                  			<li id="inter6" class="Inters_Detail ">아웃도어/여행</li>
                  			<li id="inter7" class="Inters_Detail ">외국/언어</li>
                  			<li id="inter8" class="Inters_Detail ">문화/공연/축제</li>
                  			</ul>
                  </div>
                 
                  <p class="sub" id="inters">  &#128161; 다른 관심사로 모임을 만들고 싶으신가요?</p>   
                  
               </div>     	 	
                  	 	
		           <div class="detail marginTOP" >
		              <div id="NewTitle" >
		        	     <h6 class="GreenBox" onclick="window.location.href=window.location.href">모임등록하기</h6> 
		     			 </div>   
          		    <div class="row g-4 text-center justify py-5 shadow" style="background-color: #F6F7F8; border-radius: 50px;"> 
		                   	 	
			                 <p class="sub2"> 2. 세부관심사를 선택해주세요 </p><br>   
			                 <hr>
			                 <ul class="interTable newForm">
			                	</ul>
			              </div>
			              </div>
	                
		     
		     
		     
                  <div class="detail2 marginTOP" >
               				<br><br>
                 <div id="NewTitle" >
        	     <h6 class="GreenBox" onclick="window.location.href=window.location.href">모임등록하기</h6> 
     			 </div>        
          		  <div class="row g-4 text-center justify py-5 shadow" style="background-color: #F6F7F8; border-radius: 50px;">
		                 <p class="sub2"> 3. 모임의 정보를 작성해주세요 </p>
		                 <br><hr>     
	                  	   <div class="row g-4 text-center justify" >
                         <form action="" method="post" enctype="multipart/form-data" class="form_" >    
                         <input type="hidden" value="${membervo.member_no}" name="member_no" >                      
        
	               			 
	               			  <div class="col-md-6 mb-sm-4">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" name="member_location" readonly="readonly" value="${membervo.member_location}" id="location">
                                        <label for="name">지역</label>
                                    </div>
                                </div>
                                
                                <div class="col-md-6 mb-sm-4">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="interest" name="interest_no" readonly="readonly">
                                        <label for="name">관심사</label>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-sm-4">
                                    <div class="form-floating" >
                                        <input type="text" class="form-control" id="interest_detail" name="interest_detail_name" readonly="readonly">
                                        <label for="name">세부관심사</label>
                                    </div>
                                </div>
                                
                                  <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="clubName" name="club_name" >
                                        <label for="subject">모임이름</label>
                                    </div>
                                    <p id="retry" class="GreenP"></p>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-sm-4">
                                        <textarea class="form-control" name="club_content" id="clubContent" style="height: 100px;"></textarea>
                                       <!--  <label for="message">모임의 소개글을 입력하세요</label> -->
                                    </div>
                                </div>
                                <div class="col-md-6" >
                                    <div class="form-floating"  align="left">
                                        <input type="number" class="form-control" name="club_capacity" id="clubcapacity" min="3" max="50"
                                        onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
                                        <label for="name">정원</label>
                                    </div>
                                    <p id="retry2" class="GreenP"></p>
                                </div>
                              
                           
                              	 <div class="col-12">
                                    <div class="form-floating">
                                        <input type="file" class="btn py-2 position-absolute top-0 end-0 mt-2 me-2"  id="input-image"name="file">
                                    </div>
                                </div><br><br>
                                <div class="col-12">
                                    <div class="form-floating">
                                        <p class="GreenP">대표이미지를 올려주세요(선택사항)</p>
                                    </div>
                                    </div> 
                                    <hr> 
                                 <div class="col-12">
                                    <button class="btn btn-primary rounded-pill py-3 px-5" type="submit" id="clubBtn" >모임만들기</button>
                                </div>
                        </form>
                             </div>
                             </div>
                             </div>
                        
        </div>
   </div>
     
  <!-- Contact End -->
    
    </body>
	
<script src="https://cdn.ckeditor.com/ckeditor5/34.2.0/classic/ckeditor.js"></script>
<script>
	ClassicEditor
		.create(document.querySelector('#clubContent'))
		.catch(error=>{
			console.error(error);
	});
</script>
    
<%@ include file="../include/footer.jsp"%>