<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/nav.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

  <!--사이드바 -->
    <section>
      <div class="row">
        <div class="container col-md-2 mt-5">
          <ul class="float-right my-2">
          <li class="mb-4"><i class="fas fa-user"></i> 프로필 수정</li><hr/>
          <li><a href="/user/password"><i class="fas fa-lock"></i> 비밀번호 변경</a></li>
        </ul>
        </div>

     
      <div class="container profile-box col-md-8">
      <!-- 폼 -->
      	<form:form action="/user/profile" method="PUT"
					enctype="multipart/form-data">					
      		<input type="hidden" name="id" value="${principal.id}">
       
       	<!-- 프로필 사진 -->
  	        <div class="container pt-4 float-left">
    	      <div class="user row justify-content-center">
        	    <img src="/media/${principal.profile}" id="img" class="rounded-circle" width="200" height="200"
        	    onError="javascript:this.src='/img/unknown.png'"/>
          	  </div>
	          <div class="row justify-content-center mt-2">
    	        <input type="file" id="input_img" name="profile" class="form-control-file border mt-1" style="width: 50%;"><br>
        	    <input type="hidden" id="deleteProfile" name="deleteProfile">
        	    <button type="button" id="profile-delete" class="btn btn-secondary btn-sm ml-2" style="width: 60px;">삭제</button>        	    
          	  </div>

         <!-- 자기소개 -->
          <div class="row mt-5 mx-0 justify-content-center">
            <div class="col-2 ">
            	<p class="float-right">자기소개:</p></div>
            <div class="col-9">
            	<textarea name="introduction" class="form-control text-left" rows="5">${principal.introduction}</textarea>
          	</div>
          </div>
          
          <div class="row mt-4 mx-0 justify-content-center ">
            <div class="col-2 ">
            <p class="float-right">이메일:</p></div>
            <div class="col-9">
            <input type="email" name="email" class="form-control text-left" id="email" value="${principal.email}"></input>
          </div>
          </div>
          
          <div class="row mt-3 mx-0 justify-content-center">
            <div class="col-2 ">
            <p class="float-right">키:</p></div>
            <div class="col-5 pr-1">
            <input type="text" name="height" class="form-control text-right" id="height" value="${principal.height}"></input>
          </div>

            <div class="col-4 pl-1 mt-auto">
              <p class="float-left mb-2 ">cm</p></div>
          </div>

          <div class="row mt-3 px-5">
            <div class="col-2 ">
              <p class="float-right">성별:</p></div>
            <div class="col-9">
          <div class="dropdown">
          <c:choose>
          <c:when test="${empty principal.gender}">
            <button type="button" id="dropdown-gender" class="btn btn-dark dropdown-toggle ml-3" data-toggle="dropdown">
              성별 선택
            </button>
            <input id="selected-gender" type="hidden" name="gender" value="선택안함"/>
          </c:when>
          <c:otherwise>
          	<button type="button" id="dropdown-gender" class="btn btn-dark dropdown-toggle ml-3" data-toggle="dropdown">
              ${principal.gender}
            </button>
            <input id="selected-gender" type="hidden" name="gender" value="${principal.gender}"/>
          </c:otherwise>
          </c:choose>  
            <div id="gender" class="dropdown-menu">
              <a class="dropdown-item" name="gender" value="men">남자</a>
              <a class="dropdown-item" name="gender" value="women">여자</a>
            </div>
          </div>
        </div>
        </div>

          <div class="row mt-3 px-5">
            <div class="col-2 ">
              <p class="float-right">연령대:</p></div>
            <div class="col-9">
          <div class="dropdown">
          <c:choose>
          <c:when test="${empty principal.age}">
            <button type="button" id="dropdown-age" class="btn btn-dark dropdown-toggle ml-3" data-toggle="dropdown">
              연령대 선택
            </button>
            <input id="selected-age" type="hidden" name="age" value="선택안함"/>          
           </c:when>
           <c:otherwise>
           	<button type="button" id="dropdown-age" class="btn btn-dark dropdown-toggle ml-3" data-toggle="dropdown">
              ${principal.age}
            </button>
            <input id="selected-age" type="hidden" name="age" value="${principal.age}"/>   
           </c:otherwise>
           </c:choose>
            
            <div id="age" class="dropdown-menu">
              <a class="dropdown-item" name="age" value="10대 미만">키즈</a>
              <a class="dropdown-item" name="age" value="10대">10대</a>
              <a class="dropdown-item" name="age" value="20대">20대</a>
              <a class="dropdown-item" name="age" value="30대">30대</a>
              <a class="dropdown-item" name="age" value="40대">40대</a>
              <a class="dropdown-item" name="age" value="50대">50대</a>
              <a class="dropdown-item" name="age" value="60대">시니어</a>
            </div>
          </div>
        </div>
        </div>

        <div class="row mt-3 mx-0 justify-content-center">
          <div class="col-2 ">
          <p class="float-right">인스타그램:</p></div>
          <div class="col-9">
          <input type="text" name="insta" class="form-control text-left" id="insta" placeholder="ex) https://www.instagram.com/styleshare"
          value="${principal.insta}"></input>
        </div>
        </div>

        <div class="row mt-3 mx-0 justify-content-center">
          <div class="col-2 ">
          <p class="float-right">외부사이트:</p></div>
          <div class="col-9">
          <input type="text" name="site" class="form-control text-left" id="site" placeholder="ex) https://blog.naver.com/styleshare"
          value="${principal.site}"></input>
        </div>
        </div>

              <div class="row mt-5">
                <button type="submit" class="btn btn-lg btn-dark mx-auto mb-4">완료</button>
              </div>
          </div>
          </form:form>
        </div>
         </div>
    </section>
    

<script>

//프로필 사진
$('#profile-delete').on('click', function() {
	$('#img').attr('src','/img/unknown.png');
	$('#input_img').val("");
	$('#deleteProfile').val('true');
});

	var sel_file;
	$(document).ready(function() {
		$('#input_img').on("change", handleImgFileSelect);
	});
	function handleImgFileSelect(e) {
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		filesArr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				return;
			}
			sel_file = f;
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#img').attr('src', e.target.result);
			}
			reader.readAsDataURL(f);
		});
	}

//드롭박스
	$('#gender > a').on('click', function() {
		// 버튼에 선택된 항목 텍스트 넣기 
		$('#dropdown-gender').text($(this).text());
		$('#selected-gender').val($(this).text());
		// 선택된 항목 값(value) 얻기
		// alert($(this).attr('value'));
	});

	$('#age > a').on('click', function() {
		// 버튼에 선택된 항목 텍스트 넣기 
		$('#dropdown-age').text($(this).text());
		$('#selected-age').val($(this).text());
		// 선택된 항목 값(value) 얻기
		// alert($(this).attr('value'));
	});

	
//포커스 이벤트
$('#height').focus(function(){
	$('#height').val('');
});

$('#insta').focus(function(){
	$('#insta').val('https://www.instagram.com/');
});

$('#site').focus(function(){
	$('#site').attr('placeholder','');
});
</script>
    

            
            
</body>
</html>