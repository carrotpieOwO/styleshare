<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- The Modal -->
	<div class="modal fade" id="itemSearch">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">Modal Heading</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	      <div class="input-group mb-3">
			  <input type="text" class="form-control" id="item">
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
			var res ='<div class="row justify-content-center ">';
			for(i=0; i<r.items.length; i++){
				console.log(r.items.length);

				res +=
					'<a onclick="modal(`'+r.items[i].image+'`,`'+r.items[i].title+'`,'+r.items[i].lprice+',`'+r.items[i].link+'`)" data-dismiss="modal"><img src="'+r.items[i].image+'"class="mx-1 my-1" width="150px" height="150px"></a><br/>'; 
				res += '<p>'+r.items[i].title+'</p>';
			}
			res+='</div>'
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