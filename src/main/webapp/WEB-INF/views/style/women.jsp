<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../include/nav.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <!--랭킹-->
    <div class="container mt-4">
    <div class="row">
        <h1>랭킹</h1>
    </div>
      <div class="row ranking-cards">
      	<c:forEach var="likeRank" items="${likeRanks}">
      		<div class="col-md-4 my-3">
          		<div class="card bg-light">
            		<a href="/style/${likeRank.id}">
              			<div class="style">
            				<img src="/media/${likeRank.image1}"
 				             class="card-img-top style-photo" style="cursor:pointer;"/>
            				<div class="row style-info align-items-center justify-content-center" style="width:80%">
				              <p class="ml-4"><i class="fas fa-heart"></i> ${likeRank.likeCount}</p>
				              <p class="ml-3"><i class="fas fa-bookmark right-float"></i> ${likeRank.clippingCount}</p>
				            </div>
	            			<div class="card-img-overlay "> 
				                <p class="rank${likeRank.rank} mr-2 float-left" style="width: 30px; height: 30px; font-size: 1.5em;">${likeRank.rank}</p>             			         
	            			</div>
          				</div></a>

            <div id="user"  class="card-body text-dark " style="height: 70px;">            
              <div class="d-flex align-items-center">
               <p class="card-text ">
                <p class="img float-left mr-2">
                	<img src="/media/${likeRank.profile}" class="border rounded-circle" onError="javascript:this.src='/img/unknown.png'" width="36" height="36"></p>
                <p class="name clearfix " style="font-size: 11px;">${likeRank.username}</p>
				<a href="/user/mypage/${likeRank.username}" class="btn btn-warning mb-auto ml-auto" style="cursor: pointer; z-index:10"><i class="fas fa-home"></i></a>            
				</div>
              </p>
             
            </div>
           
          </div>
        </div>
        </c:forEach>
          <!--카드끝-->
          
        </div>
  
        </div>

    <!--tag-->
<div class="container mt-5">
    <div class="row mb-2">
        <h1>트렌드 키워드</h1>
        </div>
    <div class="row text-center ml-auto justify-content-center mb-2 mr-5" >
       
       
          <div class="card mr-5 textZoom" >
            <a href="#" class=" my-3 mx-5">#Basic card</a>
          </div>
          <div class="card mr-5 textZoom">
            <a href="#" class="my-3 mx-5">#Basic card</a>
          </div>
          <div class="card mr-5 textZoom">
            <a href="#" class="my-3 mx-5">#Basic card</a>
          </div>
          
        
        </div>
        <div class="row text-center ml-auto justify-content-center" >
       
       
          <div class="card mr-5 textZoom" >
            <a href="#" class="my-3 mx-5">#Basic card</a>
            </div>
            <div class="card mr-5 textZoom">
              <a href="#" class=" my-3 mx-5">#Basic card</a>
            </div>
            <div class="card mr-5 textZoom">
              <a href="#" class="my-3 mx-5">#Basic card</a>
            </div>
      
          </div>
          </div>

          <!--카드-->
      <div class="container mt-5">
        <div class="row">
          <h1>Now</h1>
          </div>
        <div class="row scrollLocation" id="nowContainer">
         <c:forEach var="feed" items="${feeds}">  
          <div class="col-lg-3 col-md-4 col-sm-6 my-5 listToChange">
            <div class="card bg-light scrolling" data-bno="${feed.id}">
              <a href="/style/${feed.id}">
                <div class="style">
              <img
                src="/media/${feed.image1}"
                class="card-img-top style-photo"
                style="cursor:pointer"
              />
              <div class="row style-info align-items-center justify-content-center" style="width:80%">
                <p class="ml-4"><i class="fas fa-heart"></i> ${feed.likeCount}</p>
                <p class="ml-3"><i class="fas fa-bookmark right-float"></i> ${feed.clippingCount}</p>
                </div>
              <c:if test="${not empty feed.image2}">
	              <div class="card-img-overlay ">
	                  <i class="far fa-images" ></i>             
	              </div>
              </c:if>
            </div></a>
  
              <div id="user"  class="card-body text-dark " style="height: 70px;">            
                <div class="d-flex align-items-center">
                 <p class="card-text ">
                  <p class="img float-left mr-2">
                  <img src="/media/${feed.profile}" class="border rounded-circle" onError="javascript:this.src='/img/unknown.png'" width="36" height="36"></p>
                  <p class="name clearfix " style="font-size: 11px;">${feed.username}</p>
                  <p class="btn btn-sm btn-info ml-auto">팔로우</p>
              </div>
                </p>
               
              </div>
             
            </div>
          </div>
          </c:forEach>
          <!--카드끝-->
          
        </div>
        </div>

    <script>

    //마우스오버 효과                       
      $(document).on('mouseenter','.style',function(){
        $(this).find('.style-photo').css('filter', 'brightness(0.30)');
        $(this).find('.style-info').css('visibility', 'visible');
      });
      $(document).on('mouseleave','.style',function(){
        $(this).find('.style-photo').css('filter','');
        $(this).find('.style-info').css('visibility', 'hidden');
      });
  
      $(document).ready(function(){
      	$(window).scroll(function(){
      		
      			if ($(window).scrollTop() >= $(document).height()-$(window).height()){
      				let lastbno = $('.scrolling:last').attr('data-bno');
      				console.log(lastbno);
      				load_feed_box(lastbno);

      			}
      	});
      });


      async function load_feed_box(lastbno){
      	let response = await fetch('/women/scrollDown/'+lastbno);
      	let feeds = await response.json();
      	
      	
      	console.log(feeds);

      	feeds.forEach(function(feed){
      		console.log(feed);
      		let feed_box = make_feed_box(feed);
      		$(".scrollLocation").append(feed_box);

      		});
      }


      function make_feed_box(feed){
      	let id = feed.id;
      	let image = feed.image1;
      	let image2 = feed.image2;
      	let username = feed.username;
      	let profile = feed.profile;
      	let likeCount = feed.likeCount;
      	let clippingCount = feed.clippingCount;
      	
      	let str = '<div class="col-lg-3 col-md-4 col-sm-6 my-5 listToChange">';
      	str += '<div class="card bg-light scrolling" data-bno="'+id+'">';
      	str += '<a href="/style/'+id+'">';
      	str += '<div class="style">';
      	str += '<img src="/media/'+image+'" class="card-img-top style-photo" style="cursor:pointer;" />';
      	str += '<div class="row style-info align-items-center justify-content-center" style="width:80%">';
      	str += '<p class="ml-4"><i class="fas fa-heart"></i>'+likeCount+'</p>';
      	str += '<p class="ml-3"><i class="fas fa-bookmark right-float"></i>'+clippingCount+'</p></div>';
      	if(feed.image2){ 	
	      	str += '<div class="card-img-overlay ">';
    	  	str += '<i class="far fa-images" ></i></div>';
      	}
      	str += '</div></a>';
      	str += '<div id="user" class="card-body text-dark " style="height: 70px;">';
      	str += '<div class="d-flex align-items-center">';
      	str += '<p class="card-text ">';
      	str += '<p class="img float-left mr-2">';
      	str += '<img src="/media/'+profile+'" class="border rounded-circle" onError="javascript:this.src=`/img/unknown.png`" width="36" height="36"></p>';
      	str += '<p class="name clearfix " style="font-size: 11px;">'+username+'</p>';
      	str += '<a href="/user/mypage/'+username+'" class="btn btn-warning mb-auto ml-auto" style="cursor: pointer; z-index:10"><i class="fas fa-home"></i></a>';
      	str += '</div></p></div></div></div>';

      	return str;
      }
      				
    </script>
  </body>
</html>