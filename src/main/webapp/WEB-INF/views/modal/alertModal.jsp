<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- The Modal -->
  <div class="modal fade" id="alertModal">
    <div class="modal-dialog modal-dialog-scrollable ">
      <div class="modal-content col-12">
      
        <!-- Modal Header -->
        <div class="modal-header" >
          <strong class="modal-title" style="margin: 0 auto;" >
            <i class="fas fa-bell text-warning" style="font-size:20px"></i> 새소식
          </strong>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="alert-container" >
          <!-- 유저1명 -->
          
        </div>
 
 
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
        </div>
        </div>
        </div>
        </div>
 
 
 
 <script> 

	$('#alertModal').on('show.bs.modal', function (e) { 
		//var reviewId = $(e.relatedTarget).data('submit'); 
		
		$.ajax({
			type : 'GET',
			url : '/user/alert',
			dataType : 'json'
		}).done(function(r) {
			console.log(r);
			if(r.length != 0){	
				var res =' ';	
				for(i=0; i<r.length; i++){
					console.log(r[i].username);
					var username=r[i].username;
					var profile = r[i].profile;
					var styleId = r[i].styleId;
					var image = r[i].image1;
					var content = r[i].content;
					var date = r[i].createDate;
					var createDate = date.substring(0,10);
					var userId = r[i].userId;
					var principal = ${principal.id};
					var principalName = '${principal.username}';
					var like = r[i].like;
					var followMe = r[i].followMe;
					var follow = r[i].follow;
					var toUser = r[i].fromUser;
					if(username != principalName){
						
					if(like==true){
						res +='<div id="alert-list" class="tag mb-1" style="width: 100%; height:auto;">';
						res += ' <div class="container-fluid" >';
						res += '<div class="row align-items-center" style="height: 70px;">';
						res += '<div class="profile">';
						res += '<a href="/user/mypage/'+username+'">';
						res += '<img src="/media/'+profile+'" class="border rounded-circle" width="48" height="48" onError="javascript:this.src=\'/img/unknown.png\'"></a></div>';
						res += '<div class="ml-2">';
						res += '<strong class="my-auto">@'+username+'</strong>';
						res += '<p class="my-auto" style="font-size: 11px;">';
						res += '님이 회원님의 게시글을 좋아합니다.</p>';
						res += '<p class="my-auto" style="font-size:12px">'+createDate+'</p></div>';
						res += '<a href="/style/'+styleId+'" class="ml-auto my-auto">';
						res += '<img src="/media/'+image+'" width=70px height=70px></a>';			
						res += '</div></div></div></div>';
					}else if(follow==true){
						res +='<div id="alert-list" class="tag mb-1" style="width: 100%; height:auto;">';
						res += ' <div class="container-fluid" >';
						res += '<div class="row align-items-center" style="height: 70px;">';
						res += '<div class="profile">';
						res += '<a href="/user/mypage/'+username+'">';
						res += '<img src="/media/'+profile+'" class="border rounded-circle" width="48" height="48" onError="javascript:this.src=\'/img/unknown.png\'"></a></div>';
						res += '<div class="ml-2">';
						res += '<strong class="my-auto">@'+username+'</strong>';
						res += '<p class="my-auto" style="font-size: 11px;">'; 
						res += '님이 회원님을 팔로우합니다.</p>'
						res += '<p class="my-auto" style="font-size:12px">'+createDate+'</p></div>'
						 	if(follow == true){		
								res+='<input type="hidden" id="followBoolean-'+toUser+'" value="true">';
								res+='<a onclick="mFollow('+principal+','+toUser+')" id="follow-true-modal-'+toUser+'" class="btn btn-outline-dark ml-auto mt-2">';
								res+='<i id="check" class="fas fa-user-check"></i> 팔로잉</a>';
							}else{
								res+='<input type="hidden" id="followBoolean-'+toUser+'" value="false">';
								res+='<a onclick="mFollow('+principal+','+toUser+')" id="follow-false-modal-'+toUser+'" class="btn btn-primary ml-auto mt-2  text-white">';
								res+='<i id="plus" class="fas fa-user-plus"></i> 팔로우</a>';
							} 
						res += '</div></div></div></div>';
					}else{
						res +='<div id="alert-list" class="tag mb-1" style="width: 100%; height:auto;">';
						res += ' <div class="container-fluid" >';
						res += '<div class="row align-items-center" style="height: 70px;">';
						res += '<div class="profile">';
						res += '<a href="/user/mypage/'+username+'">';
						res += '<img src="/media/'+profile+'" class="border rounded-circle" width="48" height="48" onError="javascript:this.src=\'/img/unknown.png\'"></a></div>';
						res += '<div class="ml-2">';
						res += '<strong class="my-auto">@'+username+'</strong>';
						res += '<p class="my-auto" style="font-size: 11px;">';
						res += '님이 회원님의 게시글에 댓글을 달았습니다.</p>"'+content+'"';
						res += '<p class="my-auto" style="font-size:12px">'+createDate+'</p></div>'
						res += '<a href="/style/'+styleId+'" class="ml-auto my-auto">';
						res += '<img src="/media/'+image+'" width=70px height=70px></a>';			
						res += '</div></div></div></div>';



						}
					}					
			$('#alert-container').html(res);	
				}	
				}else{
				$('#like-list').text('팔로잉한 유저가 없습니다.');
			}
			
		}).fail(function(r) {
			console.log(r);
			alert("실패ㅑ");
		});
		
	});
		
	//팔로우
	function mFollow(fromUserId, toUserId, principal){
		console.log(toUserId);
		console.log(fromUserId);
		console.log(principal);
		
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
						console.log($('#followCount').text());
						let username = $('#username').text();
						let mFollowCount = $('#mFollowCount').text();
						let mFollowerCount = $('#mFollowerCount').text();
						let followCount = $('#followCount').text();
						//let myUser = ${user.id};
						let toUser = toUserId;
						let fromUser = fromUserId;
						//console.log('마이유저:'+myUser);
						
						if($('#followBoolean-'+toUserId).val() == 'true'){
							$('#follow-true-modal-'+toUserId).attr('class','btn btn-primary ml-auto mt-2 text-white');
							$('#follow-true-modal-'+toUserId).html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');
							$('#follow-true-modal-'+toUserId).attr('id','follow-false-modal-'+toUserId);						
							$('#follower-true-modal-'+toUserId).attr('class','btn btn-primary ml-auto mt-2 text-white');
							$('#follower-true-modal-'+toUserId).html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');	
							$('#follower-true-modal-'+toUserId).attr('id','follower-false-modal-'+toUserId);					
							$('#followBoolean-'+toUserId).val('false');
							$('#mFollowerCount-'+toUserId).text( Number($('#mFollowerCount-'+toUserId).text())-1);
							 if(principal==fromUser){
								$('#followCount').text( Number($('#followCount').text())-1);
								}
							 if(toUser==myUser){
								 $('#follow-true').attr('class','btn btn-primary ml-auto mt-2  text-white');
									$('#follow-true').html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');						
									$('#follow-true').attr('id','follow-false');
									$('#followBoolean').val('false');
									$('#followerCount').text( Number($('#followerCount').text())-1);
								 }
							

						}else {
							$('#follow-false-modal-'+toUserId).attr('class','btn btn-outline-dark ml-auto mt-2');
							$('#follow-false-modal-'+toUserId).html('<i id="check" class="fas fa-user-check"></i> 팔로잉');						
							$('#follow-false-modal-'+toUserId).attr('id','follow-true-modal-'+toUserId);
							$('#follower-false-modal-'+toUserId).attr('class','btn btn-outline-dark ml-auto mt-2');
							$('#follower-false-modal-'+toUserId).html('<i id="check" class="fas fa-user-check"></i> 팔로잉');						
							$('#follower-false-modal-'+toUserId).attr('id','follower-true-modal-'+toUserId);
							$('#followBoolean-'+toUserId).val('true');
							$('#mFollowerCount-'+toUserId).text( Number($('#mFollowerCount-'+toUserId).text())+1);
							 if(principal==fromUser){
								$('#followCount').text( Number($('#followCount').text())+1);
							}
							 if(toUser==myUser){
								 $('#follow-false').attr('class','btn btn-outline-dark ml-auto mt-2');
									$('#follow-false').html('<i id="check" class="fas fa-user-check"></i> 팔로잉');						
									$('#follow-false').attr('id','follow-true');
									$('#followBoolean').val('true');
									$('#followerCount').text( Number($('#followerCount').text())+1);
								 } 
							
					}
					}else{
							alert('좋아요 실패');
						}
				}).fail(function(r) {
					alert('댓글 삭제 실패');
				}); 
			
			}
	

</script>