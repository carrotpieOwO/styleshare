<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../include/nav.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
          <!--카드-->
      <div class="container" style="margin-top:150px">
        
       <div class="row text-center ml-auto justify-content-center mb-2 mr-5 mt-5" >
	<c:forEach var="tagKeyword" items="${tagKeywords}">
    
        	
        	<form class="mr-2" action="/search" method="get">
        	<input type="hidden" name="searchMenu" value="태그"/>
        	<input type="hidden" name="searchContent" value="${tagKeyword.tag}"/>
        	
        	
        	<button class="btn btn-dark text-outline-light" type="submit">#${tagKeyword.tag}</button>
        	</form>
        	
      
    </c:forEach>
</div>
	<div class="mt-1 row">
	<c:forEach var="tag" items="${tags}">
	<div class="col-md-4 my-3">
		<div class="card bg-light">
		<a href="/style/${tag.id}">
			<div class="style" >
			<img src="/media/${tag.image1}" class="card-img-top style-photo" style="cursor:pointer;"/>
			<div class="row style-info align-items-center justify-content-center" style="width:80%">
				<p class="ml-4"><i class="fas fa-heart"></i> ${tag.likeCount}</p>
				<p class="ml-3"><i class="fas fa-bookmark right-float"></i> ${tag.clippingCount}</p>
			</div>
			 <c:if test="${not empty tag.image2}">
	              <div class="card-img-overlay ">
	                  <i class="far fa-images" ></i>             
	              </div>
              </c:if>
			</div>
			</a>

			<div id="user" class="card-body text-dark " style="height: 70px;">
				<div class="d-flex align-items-center">
					<p class="card-text ">
					<p class="img float-left mr-2">
						<img src="/media/${tag.profile}" class="border rounded-circle" onError="javascript:this.src='/img/unknown.png'" width="36" height="36">
					</p>
					<p class="name clearfix " style="font-size: 11px;">${tag.username}</p>
	                 <a href="/user/mypage/${tag.username}" class="btn btn-dark text-light mb-auto ml-auto" style="cursor: pointer; z-index:10"><i class="fas fa-home"></i></a> 
				</div>
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
      				let userId = ${principal.id};
      				
      				console.log(lastbno);
      				load_feed_box(lastbno, userId);

      			}
      	});
      });


      async function load_feed_box(lastbno, userId){
      	let response = await fetch('/tiemline/scrollDown/'+lastbno+'/'+userId);
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
      	str += '<a href="/user/mypage/'+username+'" class="btn btn-dark text-light mb-auto ml-auto" style="cursor: pointer; z-index:10"><i class="fas fa-home"></i></a>';
      	str += '</div></p></div></div></div>';

      	return str;
      }
      				
    </script>
  </body>
</html>