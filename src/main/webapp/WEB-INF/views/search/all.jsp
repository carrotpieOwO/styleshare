<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/nav_simple.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

      <!--상품 검색 -->
      <section>
      <div id="productSearch" class="container" style="margin-top: 100px;">
        <h3><span>"${searchContent}"</span>의 검색 결과</h3>
        <div class="row mt-5" style="border-bottom:#333 1px solid;">
          <strong>상품 리스트</strong>
        </div>

        <div class="row mt-3">
        <c:if test="${empty products}">
          		<p class="mx-auto">검색결과가 없습니다.</p>
          	</c:if>
          <!--카드시작-->
          <div class="row" id="productList">
            <c:forEach var="product" items="${products}">         
          <div class="col-md-3 ">
            <div class="card mb-3 productCard" >
              <a href="${product.link}" target="_blank">         
              <img class="card-img-top" src="${product.image}" ></a>           
              <div class="card-body justify-content-between align-items-center ml-3" style="padding: 0;">
                <p><strong>${product.title}</strong><br/>
                 <mark><i class="fas fa-tag"></i> 최저가: ${product.lprice} 원</mark> </p>
              </div>
    
            </div>
          </div>
          	</c:forEach>
          	</div>
            <!--카드 끝-->
           
            <div class="row mx-auto mt-5">
              <button class="btn btn-secondary" id="product-more">더보기</button>
            </div>
        </div>
      </div>
      </section>

       <!--유저  -->
       <section>
       
        <div id="productSearch" class="container" style="margin-top: 100px;">
          <div class="row mt-5" style="border-bottom:#333 1px solid;">
            <p><strong>유저</strong> ${fn:length(users)}건</p>
          </div>
  
          <div class="row mt-3">
          	<c:if test="${empty users}">
          		<p class="mx-auto">검색결과가 없습니다.</p>
          	</c:if>
            <!--카드시작-->
             <c:forEach var="user" items="${users}">          
            <div class="card bg-light col-5 mb-3 mx-auto" style="height: 140px;">
              <div class="card-body" >
               <div class="row align-items-center">
                 <img src="/media/${user.profile}" class="rounded-circle" width="100px" height="100px"/>
                 <div class="ml-2 align-items-between" >
                  <p>@${user.username}</p>
                  <p style="font-size: 12px;">게시글 ${user.count} 팔로워 ${user.follower}</p>
                 </div>
                 <div class="ml-auto">
                  <img src="/media/${user.image1}"  width="45px" height="45px"/>
                  <img src="/media/${user.image2}"  width="45px" height="45px"/><br/>
                  <img src="/media/${user.image3}"  width="45px" height="45px"/>
                  <img src="/media/${user.image4}"  width="45px" height="45px"/>
                 </div>
                
               </div>
              </div>
            </div>
            </c:forEach>
              <!--카드 끝-->
          </div>
          </div>
        </section>

        <!--게시글 검색 -->
      <section>      
        <div id="productSearch" class="container mb-5" style="margin-top: 100px;">
          <div class="row mt-5" style="border-bottom:#333 1px solid;">
            <p><strong>스타일</strong> ${fn:length(feeds)}건</p>
          </div>
  
          <div class="row mt-3">
          <c:if test="${empty feeds}">
          		<p class="mx-auto">검색결과가 없습니다.</p>
          	</c:if>
            <!--카드시작-->
            <c:forEach var="feed" items="${feeds}">  
            <div class="col-md-3 mb-3">
              <div class="card" >
              <a href="/style/${feed.id}">
                <div class="style">
                <img class="card-img-top style-photo" src="/media/${feed.image1}" style="cursor:pointer">
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
                  <a href="/user/mypage/${feed.username}" class="btn btn-dark text-light mb-auto ml-auto" style="cursor: pointer; z-index:10"><i class="fas fa-home"></i></a>            
              </div>
      
              </div>
            </div>
            </div>
            </c:forEach>
              <!--카드 끝-->
          </div>
        </div>
        </section>
 

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

//상품 더보기
$('#product-more').on('click',function(){
	var searchContent = $('span').text();
	var startNum = $('.productCard').length;
	
	console.log(searchContent);
	console.log(startNum);
	
	  $.ajax({
			type : 'POST',
			url : '/search/'+searchContent+'/'+startNum,
			dataType : 'json'
				
		}).done(function(r) {
			if (r.length != 0) {
				console.log(r);
				var res = '';
				for(i=0; i<r.items.length; i++){
					console.log(r.items.length);
					res += '<div class="col-md-3">';
					res += '<div class="card mb-3 productCard" >';
					res += '<a href="'+r.items[i].link+'" target="_blank">';
					res += '<img class="card-img-top" src="'+r.items[i].image+'" ></a>';
					res += ' <div class="card-body justify-content-between align-items-center ml-3" style="padding: 0;">';
					res += '  <p><strong>'+r.items[i].title+'</strong><br/>';
					res += '<mark><i class="fas fa-tag"></i> 최저가: '+r.items[i].lprice+' 원</mark> </p>'
					res += '</div></div></div>'
					
					}
				
				$('#productList').append(res); 
					
			} else {
					alert('수정 실패');
					console.log(r);
			}
		}).fail(function(r) {
			console.log(r);
			alert('회원가입 실패');
			
		}); 
});


//무한 스크롤
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
	let username = $('.username').text().replace('@','');
	let response = await fetch('/mypage/scrollDown/'+lastbno+'/'+username);
	let feeds = await response.json();
	
	
	console.log(feeds);

	feeds.forEach(function(feed){
		console.log(feed);
		let feed_box = make_feed_box(feed);
		$(".scrollLocation").append(feed_box);

		});
}

//무한스크롤 피드박스
function make_feed_box(feed){
	let id = feed.id;
	let image = feed.image1;
	let image2 = feed.image2;
	let username = feed.username;
	let profile = feed.profile;
	let likeCount = feed.likeCount;
	let clippingCount = feed.clippingCount;
	
	let str = '<div class="col-md-3 mb-3">';
	str += '<div class="card style scrolling" data-bno="'+id+'">';
	str += '<a href="/style/'+id+'">';
	str += '<img src="/media/'+image+'" class="card-img-top style-photo"/>';
	
	if(feed.image2){ 	
    	str += '<div class="card-img-overlay ">';
	  	str += '<i class="far fa-images" ></i></div>';
	}
	str += '</a>';
	str += '<div class="card-body d-flex justify-content-between align-items-center" style="height: 10px;">';
	str += '<i class="far fa-heart ml-3">&nbsp;'+likeCount+'</i>';
	str += '<i class="far fa-bookmark mr-3">&nbsp;'+clippingCount+'</i>'
	str += '</div></div></div>'
	

	return str;
}


</script>
            
            
<%@include file="../modal/followModal.jsp"%>
            
</body>
</html>