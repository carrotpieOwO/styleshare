<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../include/nav.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <!--랭킹-->
    <section>
      <div class="container post-box">      
        <div class="container pt-4">
        
        <!-- 사진올리기 -->
          <div class="row align-items-center ml-4">	
          <h5><strong>스타일이미지</strong></h5>
          
            </div>		     
           <div class="row justify-content-center mt-2">
           <div id="preview" class="preview row align-items-center justify-content-center">
				<div id="card" class="card bg-light mr-2"
					style="width: 250px; hegit: 250px">
					<img src="/media/${style.image1}" id="foodPic"
						class="card-img-top " width=250 height=250 />
				</div>
				<c:if test="${!empty style.image2}">
					<div id="card" class="card bg-light mr-2"
						style="width: 250px; hegit: 250px">
						<img src="/media/${style.image2}" id="foodPic"
							class="card-img-top " width=250 height=250>
					</div>
				</c:if>
				<c:if test="${!empty style.image3}">
					<div id="card" class="card bg-light"
						style="width: 250px; hegit: 250px">
						<img src="/media/${style.image3}" id="foodPic"
							class="card-img-top" width=250 height=250>
					</div>
				</c:if>
			</div>
          </div>
        
        <!-- 아이템 선택 -->
          <div class="row mt-5 align-items-center ml-4">
          <h5><strong>착용 아이템</strong></h5>
            <button type="button" id="btn-item-upload" class="btn btn-dark ml-2"
            data-toggle="modal" data-target="#itemSearch">
            <i class="fas fa-plus-circle"></i> 아이템 추가
            </button>			     
           </div>
           <!-- 선택된 아이템 리스트 -->
           <div class="row ml-4 mr-4 mt-3" id="selected-item">
           		<c:if test="${not empty products}">
        			<c:forEach var="product" varStatus="status" items="${products}">
           			<table id="table_${status.index}" class="table selected-items">
						<tbody>
							<tr><td style="width:150px;">
								<img src="${product.image}" width="150px" height="150px"></td>
							<td><p class="title">${product.title}</p><br/>
								<mark><i class="fas fa-tag"></i>${product.lprice}원</mark><br/>
						    	<a onclick="deleteItem(${status.index},${product.productId})" class="btn btn-danger float-right text-white">
						    	<input type="hidden" id="link" value="${product.link}"/>
						    	<input type="hidden" id="productId" value="${product.productId}"/>
						    	<i class="fas fa-trash-alt"></i> 삭제</a></td>
						 </tbody>
					</table>
					</c:forEach>
           		</c:if>
           </div>
           
           <input type="hidden" name="userId" value="${principal.id}">
           <div class="row mt-5 align-items-center ml-4">
           
           <!-- 컨텐트 -->
            <h5><strong>코디 설명</strong></h5></div>
            <div class="row justify-content-center mt-2 px-4">
				<textarea name="content" class="form-control" rows="5" id="review">${style.content}</textarea>
            </div>
            
            <!-- 태그 -->
            <div class="row mt-5 align-items-center ml-4">
              <h5><strong>태그 추가</strong></h5></div>
              <div id="tags">
				<c:if test="${!empty tags}">
						<c:forEach var="tag" varStatus="status" items="${tags}">
							<div id="id_${status.index}" class="tag mb-1 mr-1">
							<p id="tag_${status.index}" class="tagArr">#${tag}</p>
							<a onclick="deleteTag(${status.index})" class="ml-2 tagDel"><strong>X</strong></a></div>
						</c:forEach>
					</c:if>	
              </div>
              <input type="hidden" name="tags" id="tag-submit">
              <div class="input-group input-group-sm col-6 row ml-1">
                <input type="text" id="tag-input" class="form-control"
                  placeholder="태그입력">
                <div class="input-group-append">
                  <button class="btn btn-dark" type="button" id="tag-btn">추가</button>
                </div>
              </div>
              <div class="row mt-5">
                <button type="submit" id="update-submit" class="btn btn-lg btn-dark mx-auto mb-4">업로드</button>
              </div>
          </div>
          
        </div>
      </div>
    </section>
    
   <%@include file="../modal/itemSearch.jsp"%>
   
   <script>
console.log($('#product').val());
   $('#btn-item-upload').on('click',function(){
		$('#item-list').empty();
		$('#item').val('');
	   });

   var index= 0; 
	
   function modal(image, title, lprice, link, productId){
	   console.log(image);
	   console.log(title);
	   console.log(lprice);
	   console.log(link);	
		var item = {
				image:image,
				title:title,
				lprice:lprice,
				link:link,
				productId: productId
		}
	
		var itemlist = '<table id="table_'+index+'" class="table selected-items">';
			itemlist += '<tbody>'
			itemlist += '<tr><td style="width:150px;"><img src="'+image+'" width="150px" height="150px"></td>';
			itemlist += '<td><p class="title">'+title+'</p><br/><mark><i class="fas fa-tag"></i> '+lprice+'원</mark><br/>';
		    itemlist += '<a onclick="deleteItem('+index+','+productId+')" class="btn btn-danger float-right text-white"><i class="fas fa-trash-alt"></i> 삭제</a></td>'
		    itemlist += '<input type="hidden" id="link" value="'+link+'">';
		    itemlist += '<input type="hidden" id="productId" value="'+productId+'">';
		    itemlist += '</tbody></table>';

		index++;

			
	$('#selected-item').append(itemlist);
		
	 }
	 
	function deleteItem(index, productId){
		Array.prototype.findIndexBy = function(key, value) {
		    return this.findIndex(item => item[key] === value)
		}
		
		$('#table_'+index).remove();
		
	}

	
 //태그
	$('#tag-btn').on('click', function() {

		var i = $('.tag').length;
		console.log($('.tag').length);
		var str ='<div id="id_'+i+'" class="tag mb-1 mr-1">'
		str += '<p id="tag_'+i+'" class="tagArr">';
		str += '#'+$('#tag-input').val()+'</p>';
		str += '<a onclick="deleteTag('+i+')" class="ml-2 tagDel"><strong>X</strong></a></div>';

					
		$('#tags').append(str);
		$('#tag-input').val("");

		var taglist = $('.tagArr').text();
		$('#tag-submit').val(taglist);
		

		var confirm =$('#tag-submit').val(); 
		console.log(confirm);
		
		
	});

	
	 function deleteTag(i) {
			var removeTag = $('#tag_'+i).text();
			console.log(removeTag);
			var taglist = $('.tagArr').text();
			$('#tag-submit').val(taglist.replace(removeTag,''));
			console.log($('#tag-submit').val());
			$('#id_'+i).remove();

		}

	//업데이트
		$('#update-submit').on('click', function() {

		var selected_items = [];
		for(var i=0; i<$('.selected-items').length; i++){
			var items = {
					image: $('.selected-items:eq('+i+')').find('img').attr('src'),
					title: $('.selected-items:eq('+i+')').find('.title').html(),
					lprice: $('.selected-items:eq('+i+')').find('mark').text().replace('원',''),
					link: $('.selected-items:eq('+i+')').find('#link').val(),
					productId: $('.selected-items:eq('+i+')').find('#productId').val()
			}
			
			selected_items.push(items);
		}
		
		 var data = {
			id : ${style.id},
			products : selected_items, 
			content : $('#review').val(),
			tag :$('#tag-submit').val() 
		}; 

		console.log(data);
			
		 $.ajax({
			type : 'PUT',
			url : '/style/modify',
			data : JSON.stringify(data), //만약 get 타입으로 데이터 보내면 'username=ssar' 이런식으로 쿼리스트링처럼 해서 날려야함,
			contentType : 'application/json; charset=utf-8',
			dataType : 'json'
		}).done(function(r) {
			if (r.statusCode == 200) {
				alert('수정 성공');
				location.href = '/style/${style.id}';
			} else {
					alert('수정 실패');
			}
		}).fail(function(r) {
			alert('회원가입 실패');
			
		}); 
	});
		
   </script>
  </body>
</html>