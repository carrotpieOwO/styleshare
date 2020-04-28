<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../include/nav.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
 <!-- 유저정보 -->
 <section>
    <div id="user_header_mini" class="stickyUi ">
      <div class="container" style="width: 80%;" >
        <div class="row align-items-center" style="height: 70px;">
          <div class="profile">
            <a href="/user/mypage/${style.username}"><img src="/media/${style.profile}" 
              width="48" height="48" class="border rounded-circle"  onError="javascript:this.src='/img/unknown.png'"></a>
             
          <!-- /.avatar --></div>
          <div class="ml-2">
          <p class="my-auto">@${style.username}</p>
           <p class="my-auto" style="font-size: 11px;">
           <c:if test="${not empty style.height}">
           ${style.height}cm 
           </c:if>
           <c:if test="${not empty style.gender}">
           | ${style.gender} 
           </c:if>
           <c:if test="${not empty style.age}">
           | ${style.age}
           </c:if>
           </p>
        </div> 
        <c:choose> 
	        <c:when test="${style.username==principal.username}">
	        	<div class="ml-auto">
	    		 <a href="/style/modify/${style.id}" class="btn btn-outline-dark">수정</a>  
	        	<button id="post-delete-submit" value="${style.id}" class="btn btn-outline-danger">삭제</button>
	        	</div>
	        </c:when>
	        <c:otherwise>
			<c:choose>
				<c:when test="${empty principal.id}">
				</c:when>
       	 		<c:when test="${style.follow eq true}">
       	 			<input type="hidden" id="followBoolean" value="true">
       	 			<a onclick="follow(${principal.id},${style.userId})" id="follow-true" class="btn btn-outline-dark ml-auto mt-2">
       	 			<i id="check" class="fas fa-user-check"></i> 팔로잉</a>
       	 		</c:when>
       	 		<c:otherwise>
       	 			<input type="hidden" id="followBoolean" value="false">
       	 			<a onclick="follow(${principal.id},${style.userId})" id="follow-false" class="btn btn-primary ml-auto mt-2  text-white">
       	 			<i id="plus" class="fas fa-user-plus"></i> 팔로우</a>
       	 		</c:otherwise>
       	 	</c:choose>	   
	        </c:otherwise>
        </c:choose>
        </div>
            </div>
    </div>
  </section>
  <!--디테일 -->
        <section>
        <div class="container" style="margin-top: 16px;">
          <div class="row">
            <div class="col-md-7">
              
              <div class="card" >
                <div id="demo" class="carousel slide" data-ride="carousel">

                    <!-- The slideshow -->
                    <div class="carousel-inner">
                      <div class="carousel-item active">
                        <img src="/media/${style.image1}" width=630px height=630px>
                      </div>
          			 <c:if test="${!empty style.image2}">
                      <div class="carousel-item">
                        <img src="/media/${style.image2}" width=630px height=630px>
                      </div>
                      </c:if>
    				   <c:if test="${!empty style.image3}">              
                      <div class="carousel-item">
                        <img src="/media/${style.image3}" width=630px height=630px>
                      </div>
                      </c:if>
                    </div>
                  
                    <!-- Left and right controls -->
        			<c:if test="${!empty style.image2}">      
                    <a class="carousel-control-prev my-auto" href="#demo" data-slide="prev" style="height:50%">
                      <span class="carousel-control-prev-icon"></span>
                    </a>
                    <a class="carousel-control-next" href="#demo" data-slide="next">
                      <span class="carousel-control-next-icon"></span>
                    </a>
                    </c:if>
                  </div>

                <div class="card-img-overlay">
                  <a type="button" class="badge badge-dark" style="opacity: 70%;"  data-toggle="modal" data-target="#likeModal" data-submit='${style.id}' data-principal='${principal.id}'> 
               <h4 class="my-auto mx-auto" style="color: white; letter-spacing: 4px"> <i id="likeCount" class="far fa-heart">${style.likeCount}</i></h4></a>
                </div>
                <div class="card-header">
                  
                  <!-- 좋아요 -->
               <div class="my-auto" style="position:relative;">
                  <a onclick="like(${style.id})"  style="position: absolute; top:50%; left:-5px; cursor: pointer;">
                 <c:choose>
	                 <c:when test="${style.like eq true}">
	                  	<i id="like-item-${style.id}" class=" fas fa-heart text-danger" style="font-size: 25px;"></i></a>
	                 </c:when>
	                 <c:otherwise>
	                 	 <i id="like-item-${style.id}" class="far fa-heart" style="font-size: 25px;"></i></a>
	                 </c:otherwise>
                 </c:choose>
                  </div>
                  
                  <i class="far fa-comment-dots ml-4" style="font-size: 25px;"></i> 

 			<!-- 북마크 --> 
             <div class=" float-right mr-2" style="position:relative;">
                  <a onclick="clipping(${style.id})"  style="position: absolute; top:50%; left:-5px; cursor: pointer;">
                  <c:choose>
	                 <c:when test="${style.clipping eq true}">
	                  	 <i id="clipping-item-${style.id}" class="fas fa-bookmark float-right text-warning" style="font-size: 25px;"></i></a>    	 
	                 </c:when>
	                 <c:otherwise>
	                  	 <i id="clipping-item-${style.id}" class="far fa-bookmark float-right" style="font-size: 25px;"></i></a>    	 
	                 </c:otherwise>
                 </c:choose>
             
             </div>                 
                 
                </div>
                
                <!-- 댓글리스트 -->
                <div class="card-body" id="comment-items">
            
                  
              <c:forEach var="comment" items="${comments}">
              <c:if test="${empty comments}">
              	<h4>댓글이 없습니다.</h4>
              </c:if>
              <c:if test="${comment.userId ne principal.id}">
              <div class="row align-items-center ml-1" style="height: 70px;">
                <div class="profile">
                  <a onclick="/user/mypage/${comment.username}" style="cursor: pointer">
                  <img src="/media/${comment.profile}" onError="javascript:this.src='/img/unknown.png'" class="rounded-circle border" width="40" height="40"></a>
                    <p class="my-auto" style="font-size: 11px;">
                    <fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd" /></p> 
                <!-- /.avatar --></div>
                <div class="ml-2">
                <p class="my-auto" style="font-size: 12px;">@${comment.username}</p>
                <div class="balloon test_3 ml-2" ><span>${comment.content}</span></div>
              </div> 
              </div>
              </c:if>
              <c:if test="${comment.userId eq principal.id}">
              <div id="comment-item-${comment.id}" class="row align-items-center justify-content-end text-right mr-1" style="height: 70px;">
                <div class="mr-2">
                  <p class="my-auto" style="font-size: 12px;">@${comment.username}</p>
                  
                  <div class="balloon test_4 ml" style="position: relative" ><span>${comment.content}</span>
                  <a onclick="commentDelete(${comment.id})" class="text-danger" style="position: absolute; top:-5px; left:-5px; cursor: pointer;">
                  <i class="fas fa-times-circle bg-white rounded-circle"></i></a>
                  </div>
                </div> 
                <div class="profile">
                  <a href="/user/mypage/${principal.username}">
                  <img src="/media/${principal.profile}"  onError="javascript:this.src='/img/unknown.png'" class="rounded-circle border" width="40" height="40"></a>
                    <p class="my-auto" style="font-size: 11px;">
                    <fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd" /></p>
                <!-- /.avatar --></div>
               </div>
             </c:if>
             
              </c:forEach>
                          </div>
                
                <!-- 댓글쓰기 -->
                 <c:if test="${not empty principal.id}">
                <div class="card-footer">
                   <input type="hidden" id="styleId" value="${style.id}" /> 
                   <input type="hidden" id="userId" value="${principal.id}" />
                   
                          <div class="input-group align-items-center">
                            <div class="profile mr-2 ">
                              <a href="/user/mypage/${principal.username}">
                              <img src="/media/${principal.profile}" 
                                onError="javascript:this.src='/img/unknown.png'"
                                class="rounded-circle border"
                                width="40" height="40"></a> </div>
                            <input type="text" id="content" class="form-control form-control-sm" placeholder="댓글달기...">
                            <div class="input-group-append">
                              <button id="comment-submit" class="btn btn-secondary btn-sm " type="submit" style="border-radius: 0;">보내기</button>
                            </div>
                          </div>      
           
                </div>
                </c:if>
              </div>
              <div class="row mt-5 ml-1">
                <h4>username의 인기코디</h4>
              </div>
              <div class="row mt-3">
                  <div class="col-md-4 px-1">
                <div class="card" >
                    <img class="card-img-top" src="img_avatar1.png" alt="Card image">
                    <div class="card-footer ">
                      <h5 class="card-title my-auto mx-auto text-center">
                        <i class="fas fa-heart"></i> 33&nbsp;
                        <i class="fas fa-plus-circle"></i> 5
                      </h5>
                    </div>
                  </div>
                </div>

                <div class="col-md-4 px-1">
                    <div class="card" >
                        <img class="card-img-top" src="img_avatar1.png" alt="Card image">
                        <div class="card-footer ">
                          <h5 class="card-title my-auto mx-auto text-center">
                            <i class="fas fa-heart"></i> 33&nbsp;
                            <i class="fas fa-plus-circle"></i> 5
                          </h5>
                        </div>
                      </div>
                    </div>

                    <div class="col-md-4 px-1">
                        <div class="card" >
                            <img class="card-img-top" src="img_avatar1.png" alt="Card image">
                            <div class="card-footer ">
                              <h5 class="card-title my-auto mx-auto text-center">
                                <i class="fas fa-heart"></i> 33&nbsp;
                                <i class="fas fa-plus-circle"></i> 5
                              </h5>
                            </div>
                          </div>
                        </div>
              </div>
            </div>
    
            <div class="col-md-5">
             
              <!-- 후기 -->
              	<!-- 태그 -->
	           <div class="mb-2 ml-3 row">
		       	  <c:forEach var="tag" items="${tags}">
		         <form class="mr-2" action="/search" method="get">
		        	<input type="hidden" name="searchMenu" value="태그"/>
		        	<input type="hidden" name="searchContent" value="${tag}"/>
		        	<button class="btn btn-outline-dark text-outline-light" type="submit">#${tag}</button>
		        	</form>
		          </c:forEach>
		        </div>	
		        
		        <!-- 컨텐트 -->		
              <div class="card">
               
                <div class="card-body">
                  <p class="card-text">
              		${style.content}	
                  </p>
                </div>
                <div class="card-footer" style="color:grey;"><i class="far fa-clock"></i> 
                	 <fmt:formatDate value="${style.createDate}"
							pattern="yyyy.MM.dd HH:mm" />
                </div>
              </div>
    
              <!-- 프로덕트 -->
              <div class="card mt-3">
                <div class="card-header">
                    <i class="fas fa-tshirt"></i> <strong>착용 아이템</strong>
                </div>
                <div class="card-body">
                  <c:forEach var="product" items="${products}">  
                  <div  style="border-bottom:#ddd 1px solid">
                  <div class="text-center">      
                  <div class="py-3 mx-auto">
                    <img src="${product.image}" class="text-center" width="200px" height="200px">
                    </div>
                    <div >
                  <p style="margin:0">${product.title}<br>
                  <i class="fas fa-tag"></i> ${product.lprice} 원</p>
                   </div>
                  </div>
                  <div class="text-right mb-2">
                  <a href="${product.link}" class="btn btn-sm btn-dark" style="color:white">
                  <i class="fas fa-shopping-cart" ></i> 사러가기</a>
               </div>
				</div>
                  </c:forEach>
                  
                  <!-- 브랜드 -->
   			                              
                   <div class="pt-3 ml-3 row">
		       	  <c:forEach var="product" items="${products}">
		         <form class="mr-2" action="/search" method="get">
		          <c:if test="${!empty product.brand}"> 
		        	<input type="hidden" name="searchMenu" value="브랜드"/>
		        	<input type="hidden" name="searchContent" value="${product.brand}"/>
		        	<button class="btn btn-outline-dark text-outline-light" type="submit">${product.brand}</button>
		       		</c:if>
		       		</form>
		          </c:forEach>
		        </div>	
                </div>
              </div>
    
              

            
            
            </div>
          </div>
        </div>
      </section>
   
  <script>
//댓글달기
	$('#comment-submit').on('click', function() {
		var data = {
			styleId: $('#styleId').val(),
			userId: $('#userId').val(),
			content: $('#content').val()
		};
		
		$.ajax({
			type : 'POST',
			url : '/comment/write',
			data : JSON.stringify(data),
			contentType : 'application/json; charset=utf-8', //보내는 데이터
			dataType : 'json' //응답 데이터, 데이터 주고받을땐 무조건 스트링으로 인식해서 이렇게 해줘야 제이슨으로 인식함
		}).done(function(r) { //그래서 여기서 받을 때 잭슨이 제이슨을 자바스크립트로 바꿔줘서 자바스크립트 오브젝트화됨
			if (r.status.statusCode == 200) {
				makeCommentItem(r);
			}else{
				console.log(r);
				alert('댓글 등록 실패');
				}
		}).fail(function(r) {
			alert('댓글 등록 실패 실패');
		});
	});

	 function makeCommentItem(r){
		console.log(r);
		var id = r.id;
		var username = r.username;
		var content = r.content;
		var profile = r.profile;
		var date = r.createDate;
		var createDate = date.substring(0,10);
		console.log(createDate);
		
		var comment_item = '<div id="comment-item-'+id+'" class="row align-items-center justify-content-end text-right mr-1" id="comment-item-'+id+'"style="height: 70px;">';
		comment_item += '<div class="mr-2"><p class="my-auto" style="font-size: 12px;">@'+username+'</p>';
		comment_item += '<div class="balloon test_4" style="position: relative"><span>'+content+'</span>';
		comment_item += '<a onclick="commentDelete('+id+')" class="text-danger" style="position: absolute; top:-5px; left:-5px; cursor: pointer;">';
		comment_item += '<i class="fas fa-times-circle bg-white rounded-circle"></i></a></div></div>';
		comment_item += '<div class="profile">';
		comment_item += '<a href="/user/mypage/'+username+'">';
		comment_item += '<img src="/media/'+profile+'" onError="javascript:this.src=`/img/unknown.png`" class="rounded-circle border" width="40" height="40"></a>';
		comment_item += '<p class="my-auto" style="font-size: 11px;">'+createDate+'</p>';
		comment_item += '</div></div>';
		$('#comment-items').append(comment_item);
		$('#content').val('');
	 }

	 //댓글 삭제
	 function commentDelete(commentId){
			
			$.ajax({
				type : 'DELETE',
				url : '/comment/delete/'+commentId,
				dataType : 'json' 
			}).done(function(r) { 
				console.log(r);
				if (r.statusCode == 200) {
					$('#comment-item-'+commentId).remove();
				}else{
					alert('댓글 삭제 실패');
				}
			}).fail(function(r) {
				alert('댓글 삭제 실패');
			});
		}

	//좋아요
		function like(styleId){
			if(!$('#userId').val()){
				location.href = '/user/login';
				return;
			}
			var data = {
					styleId: $('#styleId').val(),
					userId: $('#userId').val()
				};
		
				$.ajax({
					type : 'POST',
					url : '/like/'+styleId,
					data : JSON.stringify(data),
					contentType : 'application/json; charset=utf-8', //보내는 데이터
					dataType : 'text' //응답 데이터, 데이터 주고받을땐 무조건 스트링으로 인식해서 이렇게 해줘야 제이슨으로 인식함
				}).done(function(r) { //그래서 여기서 받을 때 잭슨이 제이슨을 자바스크립트로 바꿔줘서 자바스크립트 오브젝트화됨
					console.log(r);
					if (r == 'ok') {
						let likeCount = $('#likeCount').text();
						if($('#like-item-'+styleId).hasClass('far fa-heart')){
							$('#like-item-'+styleId).attr('class','fas fa-heart float-right text-danger');
							$('#likeCount').text( Number(likeCount)+1);
						}else{
							$('#like-item-'+styleId).attr('class','far fa-heart float-right');
							$('#likeCount').text(Number(likeCount)-1);
							}	
					}else{
							alert('좋아요 실패');
						}
				}).fail(function(r) {
					alert('댓글 삭제 실패');
				});
			
			}

		//북마크
		function clipping(styleId){
			if(!$('#userId').val()){
				location.href = '/user/login';
				return;
			}
			var data = {
					styleId: $('#styleId').val(),
					userId: $('#userId').val()
				};
		
				$.ajax({
					type : 'POST',
					url : '/clipping/'+styleId,
					data : JSON.stringify(data),
					contentType : 'application/json; charset=utf-8', //보내는 데이터
					dataType : 'text' //응답 데이터, 데이터 주고받을땐 무조건 스트링으로 인식해서 이렇게 해줘야 제이슨으로 인식함
				}).done(function(r) { //그래서 여기서 받을 때 잭슨이 제이슨을 자바스크립트로 바꿔줘서 자바스크립트 오브젝트화됨
					console.log(r);
					if (r == 'ok') {
						if($('#clipping-item-'+styleId).hasClass('far fa-bookmark')){
							$('#clipping-item-'+styleId).attr('class','fas fa-bookmark text-warning');
						}else{
							$('#clipping-item-'+styleId).attr('class','far fa-bookmark');
							}	
					}else{
							alert('클리핑 실패');
						}
				}).fail(function(r) {
					alert('댓글 삭제 실패');
				});
			
			}

		//게시글 삭제
		$('#post-delete-submit').on('click', function() {
			var id= $('#post-delete-submit').val();
			$.ajax({
				type : 'DELETE',
				url : '/style/delete/'+id,
				dataType : 'json' //응답 데이터, 데이터 주고받을땐 무조건 스트링으로 인식해서 이렇게 해줘야 제이슨으로 인식함
			}).done(function(r) { //그래서 여기서 받을 때 잭슨이 제이슨을 자바스크립트로 바꿔줘서 자바스크립트 오브젝트화됨
				if (r.statusCode == 200) {
					alert('글이 삭제 되었습니다.');
					location.href = '/';
				}else{
					alert('글 삭제 실패');
				}
			}).fail(function(r) {
				alert('글 삭제 실패');
			});
		});
		
  </script>
  <%@include file="../modal/likeModal.jsp"%>
  </body>
</html>