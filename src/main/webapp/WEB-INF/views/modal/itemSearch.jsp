<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- The Modal -->
	<div class="modal fade" id="itemSearch">
	  <div class="modal-dialog modal-lg modal-dialog-scrollable">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title"><i class="fas fa-search"></i> 아이템 찾기</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	      <div class="input-group mb-3 justify-content-center">
			  <input type="text" class="form-control col-6" id="item">
			  <div class="input-group-append">
			    <button class="btn btn-dark" type="button" id="item-search" >찾기</button>
			  </div>
		  </div>
		  <div id="item-list">
		  </div>
	      </div>
	
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	      </div>
	
	    </div>
	  </div>
	</div>
	
	 <script>

 
  //업데이트
	$('#item-search').on('click', function() {
		$('#item-list').empty();
		var data = {
				item : $('#item').val()
			};
		var item = $('#item').val()
	 
	$.ajax({
		type : 'POST',
		url : '/style/'+item,
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		dataType : 'json'
			
	}).done(function(r) {
		if (r.length != 0) {
			console.log(r);
			var res = '<table class="table">';
			res += '<tbody>'
			for(i=0; i<r.items.length; i++){
				console.log(r.items.length);
				res += '<tr><td style="width:150px;"><a onclick="modal(`'+r.items[i].image+'`,`'+r.items[i].title+'`,'+r.items[i].lprice+',`'+r.items[i].link+'`)" data-dismiss="modal">'
				res += '<img src="'+r.items[i].image+'"class="mx-1 my-1" width="150px" height="150px"></td>';
				res += '<td><p>'+r.items[i].title+'</p><br/><mark><i class="fas fa-tag"></i> '+r.items[i].lprice+'원</mark><br/>';
			   // itemlist += '<button class="btn btn-danger float-right"><i class="fas fa-trash-alt"></i> 삭제</button></td>'
			   // itemlist += '</tbody></table>';
			    
				// res +='<div class="row align-items-center">';
				//res +='<div><a onclick="modal(`'+r.items[i].image+'`,`'+r.items[i].title+'`,'+r.items[i].lprice+',`'+r.items[i].link+'`)" data-dismiss="modal">'
				//res +='<img src="'+r.items[i].image+'"class="mx-1 my-1" width="150px" height="150px"></a></div>'; 
				//res += '<div style="word-break:break-all"><p class="float-right">'+r.items[i].title+'</p></div>';
				//res+='</div>';
				}
			res += '</tbody></table>';
			$('#item-list').append(res);
			
				
		} else {
				alert('수정 실패');
				console.log(r);
		}
	}).fail(function(r) {
		console.log(r);
		alert('회원가입 실패');
		
	});
});

  $('.click').click(function(event){
	  console.log(event.target);
	  })
  
	 function modal1 (data){
		 console.dir(data);
		 

	}
  
    </script>