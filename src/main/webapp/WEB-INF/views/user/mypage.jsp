<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/nav_simple.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

   <!--프로필 바-->
    <section>
      <div id="user_header_mini" class="stickyUi" style="margin-top:60px;">
        <div class="container" style="width: 65%;" >
          <div class="row align-items-center" style="height: 250px;">
            <div class="profile">
              <div class="row">
              <a href="/user/mypage/${user.username}"><img src="/media/${user.profile}" 
               width="148" height="148" class="border rounded-circle" onError="javascript:this.src='/img/unknown.png'"></a></div>
               
            <!-- /.avatar --></div>
            <div>
           
              <div class="row align-items-center">
            <h3 class="ml-5 mb-2 username">@${user.username}</h3>
            <c:choose>
			<c:when test="${not empty user.insta}">
	          <a href="${user.insta}" target="_blank" class="border ml-3 text-center rounded-circle" style="width: 27px; height: 27px;">
	          <i class="fab fa-instagram" style="color: #aaa; "></i></a>
          	</c:when>
          </c:choose>
          <c:choose>
			<c:when test="${not empty user.site}">
          <a href="${user.site}" target="_blank" class="border ml-2 text-center rounded-circle" style="width: 27px; height: 27px;">
          <i class="fas fa-desktop" style="color: #aaa;"></i></a>
          	</c:when>
       	</c:choose>
          </div>
          <div class="ml-5 mb-3">
          <p class="my-auto" style="font-size: 11px;">
           <c:if test="${not empty user.height}">
           ${user.height}cm 
           </c:if>
           <c:if test="${not empty user.gender}">
           | ${user.gender} 
           </c:if>
           <c:if test="${not empty user.age}">
           | ${user.age}
           </c:if>
           </p>
           </div>
            <div class="ml-5 mb-3">
            <a type="button" class="tag" data-toggle="modal" data-target="#followerModal" data-submit='${user.id}' data-principal='${principal.id}'>
          		팔로워  <span class="ml-1" id="followerCount">${followInfo.followerCount}</span></a>
          <a type="button" class="tag" data-toggle="modal" data-target="#followModal" data-submit='${user.id}' data-principal='${principal.id}'>
          		팔로잉  <span class="ml-1" id="followCount">${followInfo.followCount}</span></a>
          
          </div>
       
        <div class="row ml-5">
            <p style="font-size: 14px;">${user.introduction}</p>
          </div> 
        </div>
        <div class="row ml-auto">
        	<!-- 팔로우 -->
          <c:choose>
      	<c:when test="${user.username eq principal.username}">
        	<a href="/user/profile/${principal.id}" class="btn btn-outline-dark mx-auto mt-2"><i class="fas fa-user-cog"></i> 프로필수정</a>
        </c:when>
        <c:otherwise>       	 	
       	 	<c:choose>
       	 		<c:when test="${followInfo.follow eq true}">
       	 			<input type="hidden" id="followBoolean" value="true">
       	 			<a onclick="follow(${principal.id},${user.id})" id="follow-true" class="btn btn-outline-dark mx-auto mt-2">
       	 			<i id="check" class="fas fa-user-check"></i> 팔로잉</a>
       	 		</c:when>
       	 		<c:otherwise>
       	 			<input type="hidden" id="followBoolean" value="false">
       	 			<a onclick="follow(${principal.id},${user.id})" id="follow-false" class="btn btn-primary mx-auto mt-2  text-white">
       	 			<i id="plus" class="fas fa-user-plus"></i> 팔로우</a>
       	 		</c:otherwise>
       	 	</c:choose>
        </c:otherwise>
       </c:choose>
         </div> 
      </div>
          </div>
              </div>
    </section>
  
    <!-- 카테고리바 -->
    <div>
     <nav>
    <ul id="category" class="d-flex justify-content-center text-center" >
  	<c:choose>
  	<c:when test="${not empty styles}">
    <li style="width: 300px;" ><a href="/user/mypage/${user.username}" ><i class="far fa-images"></i> ${styles[0].count} 개</a></li>
    <li style="width: 300px;">
      <a href="javascript:getClippings(${user.id})"><i class="fas fa-bookmark"></i> ${styles[0].myClippingCount} 개</a>
    </li>
    </c:when>
    <c:otherwise>
    <li style="width: 300px;" ><a href="/user/mypage/${user.username}" ><i class="far fa-images"></i> 0개</a></li>
    <li style="width: 300px;">
      <a href="javascript:getClippings(${user.id})"><i class="fas fa-bookmark"></i> 0개</a>
    </li>
    </c:otherwise>
    </c:choose>
  </ul>  
</nav>  
  </div>
  
      <!--디테일 -->
      <section>
      <div class="container mt-3">
        <div class="row scrollLocation" id="styleContainer">
        <c:choose>
	    <c:when test="${not empty styles}">
	      <c:forEach var="style" items="${styles}">
          <!--카드시작-->
          <div class="col-md-3 mb-3" id="review-cards">
            <div class="card style scrolling" data-bno="${style.id}">
            <a href="/style/${style.id}">
              <img class="card-img-top style-photo" src="/media/${style.image1}">
            </a>
            <c:if test="${not empty style.image2}">
	              <div class="card-img-overlay ">
	                  <i class="far fa-images" ></i>             
	              </div>
              </c:if>
              <div class="card-body d-flex justify-content-between align-items-center"
               style="height: 10px;">
                <i class="far fa-heart ml-3"> ${style.likeCount}</i> 
                <i class="far fa-bookmark mr-3"> ${style.clippingCount }</i>
              </div>
    
            </div>
          </div>
            <!--카드 끝-->
       </c:forEach>
          
          </c:when>
          <c:otherwise>
            	<p class="mx-auto">등록한 리뷰가 없습니다.</p>
          </c:otherwise>
          </c:choose>
      
          </div>
        </div>
     
    </section>
 

<script>
//마우스오버 효과                       
$(document).on('mouseenter','.style',function(){
  $(this).find('.style-photo').css('filter', 'brightness(0.30)');
});
$(document).on('mouseleave','.style',function(){
  $(this).find('.style-photo').css('filter','');
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

//클리핑리스트
function getClippings(userId){
	console.log(userId);
	$.ajax({
		type : 'GET',
		url : '/user/clipping/'+ userId,
		dataType : 'json'
	}).done(function(r) {
		console.log(r);
		if(r.length != 0){
		var str = '';
		for(i=0; i<r.length; i++){
			console.log(r[i].image1);
			var styleId = r[i].id;
			var image = r[i].image1;
			var image2 = r[i].image2;
			var likeCount = r[i].likeCount;
			var clippingCount = r[i].clippingCount;

			str += '<div class="col-md-3 mb-3">';
			str += '<div class="card style">';
			str += '<a href="/style/'+styleId+'">';
			str += '<img src="/media/'+image+'" class="card-img-top style-photo"/>';
			
			if(image2){ 	
		    	str += '<div class="card-img-overlay ">';
			  	str += '<i class="far fa-images" ></i></div>';
			}
			str += '</a>';
			str += '<div class="card-body d-flex justify-content-between align-items-center" style="height: 10px;">';
			str += '<i class="far fa-heart ml-3">&nbsp;'+likeCount+'</i>';
			str += '<i class="far fa-bookmark mr-3">&nbsp;'+clippingCount+'</i>'
			str += '</div></div></div>'

		}
		$('#styleContainer').html(str);

			}else{
				$('#review-cards').remove();
				$('#styleContainer').attr('class','row justify-content-center');
				$('#styleContainer').text('보관한 리뷰가 없습니다.');
				

				}
		
	});
}; 

//팔로우
function follow(fromUserId, toUserId){
	console.log(toUserId);
	console.log(fromUserId);

	console.log('#follow-true')
		var data = {
				fromUser: fromUserId,
				toUser: toUserId
			};
			console.log(data);
		 	$.ajax({
				type : 'POST',
				url : '/follow',
				data : JSON.stringify(data),
				contentType : 'application/json; charset=utf-8', //보내는 데이터
				dataType : 'text' //응답 데이터, 데이터 주고받을땐 무조건 스트링으로 인식해서 이렇게 해줘야 제이슨으로 인식함
			}).done(function(r) { //그래서 여기서 받을 때 잭슨이 제이슨을 자바스크립트로 바꿔줘서 자바스크립트 오브젝트화됨
				console.log(r);
				if (r == 'ok') {
					console.log(r);
					let followerCount = $('#followerCount').text()
					if($('#followBoolean').val() == 'true'){
						$('#follow-true').attr('class','btn btn-primary mx-auto mt-2  text-white');
						$('#follow-true').html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');						
						$('#follow-true').attr('id','follow-false');
						$('#followBoolean').val('false');
						$('#followerCount').text( Number(followerCount)-1);               

					}else {
						$('#follow-false').attr('class','btn btn-outline-dark mx-auto mt-2');
						$('#follow-false').html('<i id="check" class="fas fa-user-check"></i> 팔로잉');						
						$('#follow-false').attr('id','follow-true');
						$('#followBoolean').val('true');
						$('#followerCount').text( Number(followerCount)+1);
						/* $('#like-item-'+reviewId).attr('class','far fa-heart float-right');
						$('#likeCount').text(Number(likeCount)-1);
						}	 */
				}
				}else{
						alert('좋아요 실패');
					}
			}).fail(function(r) {
				alert('댓글 삭제 실패');
			}); 
		
		}

</script>
            
            
<%@include file="../modal/followModal.jsp"%>
            
</body>
</html>