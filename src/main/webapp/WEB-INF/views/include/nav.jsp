<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal" />
</sec:authorize>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <title>Agency - Start Bootstrap Theme</title>
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link rel='dns-prefetch' href='//fonts.googleapis.com' />
    <link
    href="https://fonts.googleapis.com/css?family=Kaushan+Script"
    rel="stylesheet"
    type="text/css"
  />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
  <!-- ... -->


  <link
      href="https://fonts.googleapis.com/css?family=Finger+Paint|Open+Sans&display=swap"
      rel="stylesheet"
    />
    <link
    href="/css/style.css"
    rel="stylesheet"
    type="text/css"
  />
  </head>

  <body>
<!--네비게이터-->
    <section>
    <nav class="nav">
      <div class="container" style="width: 80%;" >
        <div class="row align-items-center justify-content-between nav-list">
            <a href="/" class="row align-items-center textZoom">
                <div class="nav-home rounded-circle">
                    <i class="fas fa-share-alt nav-icon"></i>
                </div>
                <div class="ml-2">
                    <p class="my-auto"><strong>StyleShare</strong></p>
                </div>
            </a>  
        <form action="/search" method="GET" class="nav-search">
            <input type="search" class="form-control" id="search" placeholder="&#xf002 스타일과 상품을 검색해 보세요" />
        </form>
       
        <c:choose>
			<c:when test="${not empty principal}">
				<div class="row align-items-center">
				<li class="nav-item dropdown">
					<a class="nav-link  textZoom dropdown-toggle d-flex align-items-center justify-content-center" data-toggle="dropdown" href="#">
						<img src="/media/${principal.profile}"  width="30px" height="30px" class="rounded-circle border" onError="javascript:this.src='/img/unknown.png'"/>
						<p class="my-auto ml-1">${principal.username}</p>
					</a>
				<div class="dropdown-menu" style="font-size:12px; width:30px;">
					<a class="dropdown-item" href="/style/write">리뷰작성</a> 
					<a class="dropdown-item" href="/user/mypage/${principal.username}">마이페이지</a> 
					<a class="dropdown-item" href="/user/profile/${principal.id}">프로필 수정</a> 
					<a class="dropdown-item" href="/logout">로그아웃</a>
				</div></li>
					<a class="nav-link mr-3 textZoom" data-toggle="modal" data-target="#alertModal" data-submit="${principal.id}">
					<i class="far fa-bell" style="font-size:20px"></i></a></li>
				</div>
			</c:when>
			<c:otherwise>
        		<a href="/user/login" class="btn btn-dark text-white px-4 py-2 textZoom">로그인/가입</a>  
        	</c:otherwise>
        </c:choose>
        </div>
        <ul class="d-flex justify-content-center nav-menu mt-1 mb-4r">
            <a href="/" class="borderCenter"><li class="mx-5">ALL</li></a>
            <a href="/men" class="borderCenter"><li class="mx-5">MEN</li></a>
            <a href="/women" class="borderCenter"><li class="mx-5">WOMEN</li></a>
            <a href="/kids" class="borderCenter"><li class="mx-5">KIDS</li></a>
            <a href="/follow" class="borderCenter"><li class="mx-5">FOLLOW</li></a>
        </ul>
            </div>
        </nav>
  </section>
  
  <script>
  $('#search').focus(function(){
	    $('#search').attr('placeholder','');
	  });
  </script>