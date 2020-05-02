<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!-- The Modal -->
  <div class="modal fade" id="followModal">
    <div class="modal-dialog modal-dialog-scrollable ">
      <div class="modal-content col-12">
      
        <!-- Modal Header -->
        <div class="modal-header" >
          <strong class="modal-title" style="margin: 0 auto;" >
            <a href="#followerModal" id="follower" href="#followerModal" class="mr-5 text-dark">팔로워</a>
            <a class="ml-5 text-primary">팔로잉</a> 
          </strong>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="follow-container" >
          <p class="text-center">팔로잉한 유저가 없습니다.</p>
          
            
        </div>
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
        </div>
        </div>
        </div>
        </div>
        
        
         <!-- 팔로워 모달 -->
  <div class="modal fade" id="followerModal">
    <div class="modal-dialog modal-dialog-scrollable ">
      <div class="modal-content col-12">
      
        <!-- Modal Header -->
        <div class="modal-header" >
          <strong class="modal-title" style="margin: 0 auto;" >
            <a class="mr-5 text-primary">팔로워</a>
            <a id="follow" class="ml-5 text-dark">팔로잉</a> 
          </strong>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="follower-container" >
         <p class="text-center">팔로우한 유저가 없습니다.</p>
          
            
        </div>
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
        </div>
        </div>
        </div>
        </div>
        
<script> 




	$('#followModal').on('show.bs.modal', function (e) { 
		var fromUser = $(e.relatedTarget).data('submit'); 
		var principal = $(e.relatedTarget).data('principal');
		/* var followCount = ${reviews[i].followCount}
		var followerCount = ${reviews[i].followerCount} */
		console.log(principal);
		console.log(fromUser);
		$.ajax({
			type : 'GET',
			url : '/followInfo/'+ fromUser,
			dataType : 'json'
		}).done(function(r) {
			console.log(r);
			//$('#follower').attr('href','/followerInfo/'+userId);
			if(r.length != 0){	
				var res =' ';	
				for(i=0; i<r.length; i++){
					console.log(r[i].username);
					var username=r[i].username;
					var profile = r[i].profile;
					var toUser = r[i].toUser;
					var followCount = r[i].followCount;
					var followerCount = r[i].followerCount;
					console.log('프린시펄:'+principal);
					console.log('투유저:'+toUser);
										
					res +='<div id="follow-list" class="tag mb-1" style="width: 100%; height:auto;">';
					res += ' <div class="container-fluid" >';
					res += '<div class="row align-items-center" style="height: 70px;">';
					res += '<div class="profile">';
					res += '<a href="/user/mypage/'+username+'">';
					res += '<img src="/media/'+profile+'" class="border rounded-circle" width="48" height="48" onError="javascript:this.src=\'/img/unknown.png\'"></a></div>';
					res += '<div class="ml-2">';
					res += '<p class="my-auto">@'+username+'</p>';
					res += '<p class="my-auto" style="font-size: 11px;">';
					res += '팔로잉<strong id="mFollowCount-'+toUser+'">'+followCount+'</strong> ';
					res += ' 팔로워<strong id="mFollowerCount-'+toUser+'">'+followerCount+'</strong></p></div>';

					if(principal ==''){

					}
					else if(principal != toUser){
						if(r[i].follow == true){
							
							res+='<input type="hidden" id="followBoolean-'+toUser+'" value="true">';
							res+='<a onclick="mFollow('+principal+','+toUser+','+principal+')" id="follow-true-modal-'+toUser+'" class="btn btn-outline-dark ml-auto mt-2">';
							res+='<i id="check" class="fas fa-user-check"></i> 팔로잉</a>';
							
						}else{
							res+='<input type="hidden" id="followBoolean-'+toUser+'" value="false">';
							res+='<a onclick="mFollow('+principal+','+toUser+','+principal+')" id="follow-false-modal-'+toUser+'" class="btn btn-primary ml-auto mt-2  text-white">';
							res+='<i id="plus" class="fas fa-user-plus"></i> 팔로우</a>';
								
						}

					}
					
					res += '</div></div></div>';
					
				}
				$('#follow-container').html(res);
				
			}else{
				$('#follow-list').text('팔로잉한 유저가 없습니다.');
			}
			
		}).fail(function(r) {
			console.log(r);
			alert("실패ㅑ");
		});
		
	});
		
	$('#followerModal').on('show.bs.modal', function (e) { 
		var toUser = $(e.relatedTarget).data('submit'); 
		var principal = $(e.relatedTarget).data('principal');

		$.ajax({
			type : 'GET',
			url : '/followerInfo/'+ toUser,
			dataType : 'json'
		}).done(function(r) {
			console.log(r);
			//$('#follow').attr('href','/followInfo/'+userId);
			if(r.length != 0){	
				var res =' ';	
				for(i=0; i<r.length; i++){
					console.log(r[i].username);
					var username=r[i].username;
					var profile = r[i].profile;
					var toUser = r[i].toUser;
					var fromUser = r[i].fromUser;
					var followCount = r[i].followCount;
					var followerCount = r[i].followerCount;
					console.log(r[i].follow);

					
					res +='<div id="follower-list" class="tag mb-1" style="width: 100%; height:auto;">';
					res += ' <div class="container-fluid" >';
					res += '<div class="row align-items-center" style="height: 70px;">';
					res += '<div class="profile">';
					res += '<a href="/user/mypage/'+username+'">';
					res += '<img src="/media/'+profile+'" class="border rounded-circle" width="48" height="48" onError="javascript:this.src=\'/img/unknown.png\'"></a></div>';
					res += '<div class="ml-2">';
					res += '<p class="my-auto">@'+username+'</p>';
					res += '<p class="my-auto" style="font-size: 11px;">';
					res += '팔로잉<strong id="mFollowCount-'+fromUser+'">'+followCount+'</strong> ';
					res += ' 팔로워<strong id="mFollowerCount-'+fromUser+'">'+followerCount+'</strong></p></div>';

					if(principal ==''){

					}
					else if(principal != fromUser){
						if(r[i].follow == true){
							res+='<input type="hidden" id="followBoolean-'+fromUser+'" value="true">';
							res+='<a onclick="mFollow('+principal+','+fromUser+','+principal+')" id="follower-true-modal-'+fromUser+'" class="btn btn-outline-dark ml-auto mt-2">';
							res+='<i id="check" class="fas fa-user-check"></i> 팔로잉</a>';
						}else{
							res+='<input type="hidden" id="followBoolean-'+fromUser+'" value="false">';
							res+='<a onclick="mFollow('+principal+','+fromUser+','+principal+')" id="follower-false-modal-'+fromUser+'" class="btn btn-primary ml-auto mt-2  text-white">';
							res+='<i id="plus" class="fas fa-user-plus"></i> 팔로우</a>'	
						}

					}	
					res += '</div></div></div>';
					
				}
				$('#follower-container').html(res);
			}else{
				$('#follower-list').text('팔로잉한 유저가 없습니다.');
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
						let myUser = ${user.id};
						let toUser = toUserId;
						let fromUser = fromUserId;
						console.log('마이유저:'+myUser);
						
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
