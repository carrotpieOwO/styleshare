<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    />
    <link href="/css/member_style.css" rel="stylesheet" type="text/css" />
    <script
      type="text/javascript"
      src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
      charset="utf-8"
    ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
  </head>
  <body>
    <div class="form-content py-5 px-5">
      <h1>StyleShare</h1>
      <h5>회원가입</h5>
      <form>
      <div class="row join my-5 mx-3">
        <div class="form-left my-auto px-4">
          <input
            type="username"
            class="form-control"
            placeholder="ID"
            name="username"
            id="username"
          />
    		<p id="username-check" class="text-danger" style="font-size:11px;"></p>
          <input
            type="email"
            class="form-control mt-2"
            placeholder="이메일"
            name="email"
            id="email"
          />
          <p id="email-check" class="text-danger" style="font-size:11px;"></p>
          <input
            type="password"
            class="form-control mt-2"
            placeholder="비밀번호 (최소4자)"
            name="password"
            id="password"
          />
          <p id="password-check" class="text-danger" style="font-size:11px;"></p>
          <input
            type="password"
            class="form-control"
            placeholder="비밀번호 확인"
            id="pwd-confirm"
          />
          <p id="pwd-confirm-check" class="text-danger" style="font-size:11px;"></p>
          <button type="button" id="join-submit" class="btn btn-dark mt-3 float-right">가입하기</button>
        </div>
        </form>
        <div class="form-right my-auto px-2">
          <div id="naver_id_login"></div>
        </div>
      </div>
      <div class="login-toggle">
        <strong>이미 계정을 갖고 계시다구요? </strong>&nbsp;
        <a href="/user/login"> 여기서 로그인</a>
      </div>
    </div>

<script type="text/javascript">
    //네이버로로그인(회원가입)
      var naver_id_login = new naver_id_login(
        "Z67LEXnLFW6Gxln_n7HU",
        "http://127.0.0.1:5501/"
      );
      var state = naver_id_login.getUniqState();
      naver_id_login.setButton("green", 3, 40);
      naver_id_login.setDomain("http://127.0.0.1:5501/login.html");
      naver_id_login.setState(state);
      naver_id_login.setPopup();
      naver_id_login.init_naver_id_login();

//자체회원가입
//ID검증
$('#username').on('propertychange change keyup paste input',function(){
	var data = {
			username : $('#username').val()
	};
	$.ajax({
		type : 'POST',
		url : '/user/username',
		data : JSON.stringify(data), 
		contentType : 'application/json; charset=utf-8',
		dataType : 'json'
	}).done(function(r){
		if(r.statusCode==200){
			$('#username-check').html('OK');
		}else{
			if(r.msg == '아이디중복'){
				$('#username-check').html('이미 사용중인 ID입니다.');
			}else{
				$('#username-check').html('Error');
			}
		}
	}).fail(function(r){
		$('#username-check').html(r.responseJSON.username);
	});	 
});
      
//email검증
$('#email').on('propertychange change keyup paste input',function(){
	var data = {
			email : $('#email').val()
	};	
	$.ajax({
		type:'POST',
		url: '/user/email',
		data : JSON.stringify(data), 
		contentType : 'application/json; charset=utf-8',
		dataType:'json'
	}).done(function(r){
		if(r.statusCode==200){
			$('#email-check').html('OK');
		}else{
			$('#email-check').html('Error');
		}
	}).fail(function(r){
		$('#email-check').html(r.responseJSON.email);
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

//회원가입-insert
$('#join-submit').on("click",function(){
	if($('#password').val()!=$('#pwd-confirm').val()){
		alert('패스워드를 확인하세요.');
	}else{
		var data = {
				username : $('#username').val(),
				password : $('#password').val(),
				email : $('#email').val(),
				address : $('#address').val()
			};
			$.ajax({
				type : 'POST',
				url : '/user/join/',
				data : JSON.stringify(data), 
				contentType : 'application/json; charset=utf-8',
				dataType : 'json'
			}).done(function(r) {
				if (r.statusCode == 200) {
					alert('회원가입 성공');
					location.href = '/user/login';
				} else {
					if (r.msg == '아이디중복') {
						$('#id-check').html('아이디가 중복되었습니다.');
					} else {
						alert('회원가입 실패');
					}
				}
			}).fail(function(r) {
				console.log(r);
				$('#id-check').html(r.responseJSON.username);
				$('#pw-check').html(r.responseJSON.password);
				$('#email-check').html(r.responseJSON.email);
			});
	}
});

        
    </script>
  </body>
</html>
