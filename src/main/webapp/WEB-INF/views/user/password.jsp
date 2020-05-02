<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/nav.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
      <!--디테일 -->
    <section>
      <div class="row" style="margin-top:150px">
        <div class="container col-md-2 mt-5">
          <ul class="float-right my-2">
          <li class="mb-4"><a href="/user/profile/${principal.id}"><i class="fas fa-user"></i> 프로필 수정</a></li><hr/>
          <li><i class="fas fa-lock"></i> 비밀번호 변경</li>
        </ul>
        </div>

     
      <div class="container profile-box col-md-8">
        <div class="container pt-4 float-left">
         
          
          <div class="row mt-4 justify-content-center ">
            <div class="col-3 ml-1 pl-1">
            <p class="float-right">현재비밀번호:</p></div>
            <div class="col-6">
            <input type="password" class="form-control text-left" id="current"></input>
    		<p id="current-check" class="text-danger" style="font-size: 11px;"></p>        
          </div>
          </div>

          <div class="row mt-4 justify-content-center ">
            <div class="col-3 ">
            <p class="float-right">새비밀번호:</p></div>
            <div class="col-6">
            <input type="password" class="form-control text-left" id="password"></input>
       		<p id="password-check" class="text-danger" style="font-size: 11px;"></p>        
          </div>
          </div>
          
          <div class="row mt-1 justify-content-center ">
            <div class="col-3 ">
            <p class="float-right">비밀번호 확인:</p></div>
            <div class="col-6">
            <input type="password" class="form-control text-left" id="pwd-confirm"></input>
	 		<p id="pwd-confirm-check" class="text-danger" style="font-size: 11px;"></p>        
          </div>
          </div>

              <div class="row mt-5">
                <button type="button" id="update-submit" class="btn btn-lg btn-dark mx-auto mb-4">완료</button>
              </div>
          </div>
        </div>
         </div>
    </section>
       
       <script>

     //현재 비밀번호 인증
    $('#current').on('propertychange change keyup paste input',function(){
	var username = '${principal.username}';
	var data = {
			username : username,
 			password : $('#current').val()
 	};	
	$.ajax({
		type : 'POST',
		url : '/user/password/certify',
		data : JSON.stringify(data), 
 		contentType : 'application/json; charset=utf-8',
		dataType : 'json'
	}).done(function(r){
		if(r.statusCode==200){
			$('#current-check').html('OK');
		}else{
			$('#current-check').html('Error');
		}
	}).fail(function(r){
		$('#current-check').html('패스워드가 틀렸습니다.');
	});	 
});

     //password 검증
     $('#password').on('propertychange change keyup paste input',function(){
     	var data = {
     			password : $('#password').val()
     	};	
     	$.ajax({
     		type:'POST',
     		url: '/user/password',
     		data : JSON.stringify(data), 
     		contentType : 'application/json; charset=utf-8',
     		dataType:'json'
     	}).done(function(r){
     		if(r.statusCode==200){
     			$('#password-check').html('OK');
     		}else{
     			$('#password-check').html('Error');
     		}
     	}).fail(function(r){
     		$('#password-check').html(r.responseJSON.password);
     	});	 
     });

   //비밀번호 확인
     $('#pwd-confirm').on('propertychange change keyup paste input',function(){
     	var pwd_confirm = $('#pwd-confirm').val();
     	var password = $('#password').val();

     	if(pwd_confirm == password){
     		$('#pwd-confirm-check').html('OK');
     	}else{
     		$('#pwd-confirm-check').html('패스워드가 다릅니다.');
     	}
     });

     //비밀번호 업데이트
	$('#update-submit').on("click", function() {
		if($('#password').val()!=$('#pwd-confirm').val()||$('#current-check').html()!='OK'){
			alert('패스워드를 확인하세요.');
		}
		else{
		var username = '${principal.username}'
		var data = {
			username : username,
			password : $('#password').val()
		};
		$.ajax({
			type : 'PUT',
			url : '/user/password',
			data : JSON.stringify(data),
			contentType : 'application/json; charset=utf-8',
			dataType : 'json'
		}).done(function(r) {
			if (r.statusCode == 200) {
				alert('패스워드 변경 완료');
				location.href = '/';
			} else {
				alert('패스워드 변경 실패');
			}
		}).fail(function(r) {
			console.log(r);
			$('#current-check').html(r.responseJSON.password);
		});
		}
	});
</script>
</body>
</html>