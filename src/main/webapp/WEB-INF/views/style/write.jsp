<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../include/nav.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <!--랭킹-->
    <section>
      <div class="container post-box">
                <form action="/style/write" method="post" enctype="multipart/form-data" class="row justify-content-center">
      
        <div class="container pt-4">
        
          <div class="row align-items-center ml-4">	
          <h5><strong>스타일이미지</strong></h5>
            <div id="upload" class="filebox">
                 <button type="button" id="btn-image-upload" class="btn btn-dark ml-2"><i class="fas fa-plus-circle"></i> 사진업로드</button>
                 <input type="file" id="uploadFile1" name="image1" class="multi"  />
                 <input type="file" id="uploadFile2" name="image2" class="multi"  />
                 <input type="file" id="uploadFile3" name="image3" class="multi"  />
             </div>	
            </div>		     
           <div class="row justify-content-center mt-2">
            <div id="preview" class="preview">
              <div id="no-image" class=" text-center">
                <i class="mt-5 far fa-images" style="font-size: 50px"></i><br>
                <p class="mb-5 mt-1" style="display: block; font-size: 12px">사진은
                  3장까지 업로드 가능합니다.</p>
              </div>
            </div>
          </div>
          <div class="row mt-5 align-items-center ml-4">
          <h5><strong>착용 아이템</strong></h5>
            <button type="button" id="btn-item-upload" class="btn btn-dark ml-2"
            data-toggle="modal" data-target="#itemSearch">
            <i class="fas fa-plus-circle"></i> 아이템 추가
            </button>			     
           </div>
           <div class="row" id="selected-item">
           </div>
           <div class="row mt-5 align-items-center ml-4">
            <h5><strong>코디 설명</strong></h5></div>
            <div class="row justify-content-center mt-2 px-4">
							<textarea name="content" class="form-control" rows="5" id="review" required></textarea>
            </div>
            <div class="row mt-5 align-items-center ml-4">
              <h5><strong>태그 추가</strong></h5></div>
              <div id="tags">
							
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
                <button type="submit" class="btn btn-lg btn-dark mx-auto mb-4">업로드</button>
              </div>
          </div>
                        </form>
          
        </div>
      </div>
    </section>
    
   <%@include file="../modal/itemSearch.jsp"%>
   
   <script>
   //사진업로드 버튼
   $('#btn-image-upload').click(function(e){
      // e.preventDefalut();
      if($('.Img-card').length==0){
   	   $('#uploadFile1').click();
   	   console.log($('#uploadFile').val());
       }else if($('.Img-card').length==1){
           $('#uploadFile2').click();
       }else if($('.Img-card').length==2){
           $('#uploadFile3').click();
       }else{
           alert('사진은 3장까지 업로드 가능합니다.');

           }
       });
       
   var sel_file;
	$(document).ready(function() {
		$('#uploadFile1').on("change", handleImgFileSelect1);
		$('#uploadFile2').on("change", handleImgFileSelect2);
		$('#uploadFile3').on("change", handleImgFileSelect3);
		
	});
	
	function handleImgFileSelect1(e) {
		var files = e.target.files;
		var arr = Array.prototype.slice.call(files);
		arr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("이미지파일만 업로드 가능합니다.");
				return;
			}
			var size = f.size

			if(size > 1048576){
				alert('파일크기는 1mb보다 작아야 합니다.');
				return;
			}
			preview(arr,1);
		
		});
	}

	function handleImgFileSelect2(e) {
		var files = e.target.files;
		var arr = Array.prototype.slice.call(files);
		arr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("이미지파일만 업로드 가능합니다.");
				return;
			}
			var size = f.size

			if(size > 1048576){
				alert('파일크기는 1mb보다 작아야 합니다.');
				return;
			}
			preview(arr,2);
		
		});
	}

	function handleImgFileSelect3(e) {
		var files = e.target.files;
		var arr = Array.prototype.slice.call(files);
		arr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("이미지파일만 업로드 가능합니다.");
				return;
			}
			var size = f.size

			if(size > 1048576){
				alert('파일크기는 1mb보다 작아야 합니다.');
				return;
			}
			preview(arr,3);
		
		});
	}
	

		function preview(arr,num) {
			var sel_files = [];
			console.log(num);
			arr.forEach(function(f) {
			//div에 이미지 추가
			var str = '<div id="img" style="display: inline-flex; padding: 12px;">';
			var g_count = 0;
			//이미지 파일 미리보기
			if (f.type.match('image.*')) {
			
				sel_files.push(f);
				var reader = new FileReader();
				reader.onload = function(e) {
					
					if ($('img').length) {
						$('#no-image').hide();

					}
					
					str += '<div id="card'+num+'" class="card bg-light Img-card" style="width: 250px; hegit: 250px">';
					str += '<img src="'+e.target.result+'" id="foodPic" class="card-img-top" title="'+f.name+'" width=250 height=250 />';
					str += '<div class="card-img-overlay">';
					str += '<button type="button" onclick="deleteImage('+num+')" class="float-right btn btn-danger btn-sm rounded-circle"><i class="fas fa-times"></i></button>';
					str += '</div></div>';

					$(str).appendTo('#preview');
				//	$('#uploadFile').append("<input type='file' name='file_"+(g_count++)+"'/>");
				}
				console.log(sel_files);
				reader.readAsDataURL(f);
			} else {
				alert('이미지 파일만 업로드 가능합니다.');
				return;
			}
			console.log($('#uploadFile1').val());
			console.log($('#uploadFile2').val());
			console.log($('#uploadFile3').val());

			
		});//arr.forEach
	}
//});

	//파일 삭제
	function deleteImage(num) {
		console.log($('#uploadFile'+num).val());
		$('#uploadFile'+num).val("");

			
		$('#card'+num).remove();

		console.log($('#uploadFile'+num).val());
		
		if (!$('.Img-card').length) {
			console.log($(".Img-card").length);
			$('#no-image').show();
		}

		
		
	};
	
   $('#btn-item-upload').on('click',function(){
		$('#item-list').empty();
		$('#item').val('');
	   });

   function modal(image, title, lprice, link){
	   console.log(image);
	   console.log(title);
	   console.log(lprice);
	   console.log(link);	
	
		var itemlist ='<div class="row align-items-center">';
		   itemlist += '<a href="'+link+'"><img src="'+image+'" width="150px" height="150px"></a>';
		   itemlist += '<div class="align-items-center">';
		   itemlist += '<p>'+title+'</p>';
		   itemlist += '<p>'+lprice+'</p>';
		  
		   itemlist += '<input type="hidden" name="title[]" value="'+title+'">';
		   itemlist += '<input type="hidden" name="image[]" value="'+image+'">';
		   itemlist += '<input type="hidden" name="lprice[]" value="'+lprice+'">';
		   itemlist += '<input type="hidden" name="link[]" value="'+link+'">';
		   itemlist += '</div></div>'

	$('#selected-item').append(itemlist);
		
	 }

 //태그
	$('#tag-btn').on('click', function() {

		var i = $('.tag').length;
		console.log($('.tag').length);
		var str ='<div id="id_'+i+'" class="tag mb-1 mr-1">'
		str += '<p id="tag_'+i+'" class="tagArr ">';
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
	 
   </script>
  </body>
</html>