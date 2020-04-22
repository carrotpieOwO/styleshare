<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- The Modal -->

  <div class="modal" id="likeModal">
    <div class="modal-dialog modal-dialog-scrollable ">
      <div class="modal-content col-12">
      
        <!-- Modal Header -->
        <div class="modal-header" >
          <strong class="modal-title" style="margin: 0 auto;" >
            <i class="fas fa-heart text-danger"></i> 좋아요
          </strong>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="like-container" >
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

	$('#likeModal').on('show.bs.modal', function (e) { 
		var styleId = $(e.relatedTarget).data('submit'); 
		var principal = $(e.relatedTarget).data('principal'); 
		console.log(principal);
		$.ajax({
			type : 'GET',
			url : '/likeInfo/'+ styleId,
			dataType : 'json'
		}).done(function(r) {
			console.log(r);
			if(r.length != 0){	
				var res =' ';	
				for(i=0; i<r.length; i++){
					console.log(r[i].username);
					var username=r[i].username;
					var profile = r[i].profile;
					var userId = r[i].userId;
					var followCount = r[i].followCount;
					var followerCount = r[i].followerCount;
					
										
					res +='<div id="like-list" class="tag mb-1" style="width: 100%; height:auto;">';
					res += ' <div class="container-fluid" >';
					res += '<div class="row align-items-center" style="height: 70px;">';
					res += '<div class="profile">';
					res += '<a href="/user/mypage/'+username+'">';
					res += '<img src="/media/'+profile+'" class="border rounded-circle" width="48" height="48" onError="javascript:this.src=\'/img/unknown.png\'"></a></div>';
					res += '<div class="ml-2">';
					res += '<p class="my-auto">@'+username+'</p>';
					res += '<p class="my-auto" style="font-size: 11px;">';
					res += '팔로잉<strong id="mFollowCount-'+userId+'">'+followCount+'</strong> ';
					res += ' 팔로워<strong id="mFollowerCount-'+userId+'">'+followerCount+'</strong></p></div>';

					if(principal == ''){

					}
					else if(principal != userId){
						if(r[i].follow == true){
							
							res+='<input type="hidden" id="followBoolean-'+userId+'" value="true">';
							res+='<a onclick="mFollow('+principal+','+userId+')" id="follow-true-modal-'+userId+'" class="btn btn-outline-dark ml-auto mt-2">';
							res+='<i id="check" class="fas fa-user-check"></i> 팔로잉</a>';
							
						}else{
							res+='<input type="hidden" id="followBoolean-'+userId+'" value="false">';
							res+='<a onclick="mFollow('+principal+','+userId+')" id="follow-false-modal-'+userId+'" class="btn btn-primary ml-auto mt-2  text-white">';
							res+='<i id="plus" class="fas fa-user-plus"></i> 팔로우</a>';
								
						}

					}
					res += '</div></div></div>';
					
				}
				$('#like-container').html(res);
				
			}else{
				$('#like-list').text('팔로잉한 유저가 없습니다.');
			}
			
		}).fail(function(r) {
			console.log(r);
			alert("실패ㅑ");
		});
		
	});
		

	//팔로우
 	function mFollow(fromUserId, toUserId){
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
						console.log(toUserId);
						console.log($('#followCount').text());
						let username = $('#username').text();
						let mFollowCount = $('#mFollowCount'+toUserId).text();
						let mFollowerCount = $('#mFollowerCount'+toUserId).text();
						let followCount = $('#followCount').text();
					//	let principal = ${principal.id};
						let styleUser = ${style.userId};
						console.log($('#followBoolean-'+toUserId).val());
						if($('#followBoolean-'+toUserId).val() == 'true'){
							$('#follow-true-modal-'+toUserId).attr('class','btn btn-primary ml-auto mt-2 text-white');
							$('#follow-true-modal-'+toUserId).html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');
							$('#follow-true-modal-'+toUserId).attr('id','follow-false-modal-'+toUserId);						
							$('#follower-true-modal-'+toUserId).attr('class','btn btn-primary ml-auto mt-2 text-white');
							$('#follower-true-modal-'+toUserId).html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');	
							$('#follower-true-modal-'+toUserId).attr('id','follower-false-modal-'+toUserId);					
							$('#followBoolean-'+toUserId).val('false');
							$('#mFollowerCount-'+toUserId).text( Number($('#mFollowerCount-'+toUserId).text())-1);
							if(toUserId == styleUser){
								$('#follow-true').attr('class','btn btn-primary ml-auto mt-2  text-white');
								$('#follow-true').html('<i id="plus" class="fas fa-user-plus"></i> 팔로우');						
								$('#follow-true').attr('id','follow-false');
								$('#followBoolean').val('false');
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
							  if(toUserId == styleUser){
								  $('#follow-false').attr('class','btn btn-outline-dark ml-auto mt-2');
									$('#follow-false').html('<i id="check" class="fas fa-user-check"></i> 팔로잉');						
									$('#follow-false').attr('id','follow-true');
									$('#followBoolean').val('true');
							  } 
							/* $('#like-item-'+styleId).attr('class','far fa-heart float-right');
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