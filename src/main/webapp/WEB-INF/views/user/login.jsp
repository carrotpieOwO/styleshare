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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
  </head>
  <body>
    <div class="form-content py-5 px-5">
      <h1>StyleShare</h1>
      <h5>로그인</h5>
       <form>
      <div class="row join my-5 mx-3 justify-content-center">
        <div class="my-auto px-4 ">
          <input
            type="username"
            class="form-control"
            placeholder="ID"
            name="username"
            id="username"
          />
          <input
            type="password"
            class="form-control mt-2"
            placeholder="비밀번호"
            name="password"
            id="password"
          />
   		<p id="login-check" class="text-danger" style="font-size: 11px;"></p>
          
          <button id="login-submit" type="button" class="btn btn-dark mt-3">로그인</button>
        </div>
        </form>
        <div class="form-right my-auto px-2">
          <div id="naver_id_login"></div>
        </div>
      </div>
      <div class="login-toggle">
        <strong>ID가 없으세요? </strong>&nbsp;
        <a href="/user/join"> 여기서 가입</a>
      </div>
    </div>

    <script type="text/javascript">

      $('#login-submit').on('click', function(e) {
  		e.preventDefault();
  		

  		var data = {
  			username : $('#username').val(),
  			password : $('#password').val(),
  			/* rememberMe : $('#remember').val() */
  		};
  		$.ajax({
  			type : 'POST',
  			url : '/user/login',
  			data : data,
  			contentType : 'application/x-www-form-urlencoded',
  			dataType : 'json'
  		}).done(function(r) {
  	  		alert('로그인 성공');
  			location.href = '/';
  		}).fail(function(r) {
  			$('#login-check').html('아이디 혹은 비밀번호를 확인하세요.');
  		});
  	});
    </script>
  </body>
</html>
