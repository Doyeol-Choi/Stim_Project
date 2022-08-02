function changPicture(user_code) {
	let url = "/changePicture?user_code="+user_code.toString();
	
	window.open(url, "_blank_1", "toolbar=no, menubar=no, scrollbar=yes, resizeable=no, width=450, height=300");
}

function updateCancle() {
	self.close();
}

function popupClose(user_code) {
	$("#pictureForm").submit();
	$("#textBox").css("display", "block");
	setTimeout(function() {   
		opener.parent.location.reload();
		self.close();
    }, 2400);
}

function deleteComment(comment_code){
	let id = "#mentListBox" + comment_code 
	$.ajax({
	    url: "/comment/delete",
	       type: "POST",
	       async: true,
	       data: {
	          "comment_code" : comment_code
	       },
	       success: function () {
	          $(id).remove();
	       }
	});
}

function deleteRequest(friend_code){
	let id = "#reqList" + friend_code
	$.ajax({
		url : "/friend/request",
			type: "POST",
			async: true,
			data:{
				"friend_code" : friend_code
			},
			success: function () {
				$(id).remove();
			}
	});
}

/*function inputComment(){
//	var pVo = $('#comment_form').serialize();
	let user_code = $("#user_code").val();
	let user_id = $("#user_id").val();
	let writer_code = $("#writer_code").val();
	let comment_text = $("#comment_text").val();
	
	$.ajax({
		url : "/comment",
		data : {
			"user_code":user_code,
			"user_id":user_id,
			"writer_code":writer_code,
			"comment_text":comment_text
		},
		type : "POST",
		cache: false,
		error : function(){
			alert('추가 실패');
		}	
		}).done( function(fragment){
			$("#comment_lists").replaceWith(fragment)
		});

}*/

/*$('#comment_submit').click(function(){
	
	let commentInfo = {
		
		"user_code" : $('#user_code').val(),
		"user_id" : $('user_id').val(),
		"writer_code" : $('writer_code').val(),
		"comment_text" : $('comment_text').val()
	};
	
	$.ajax({
		
		url: "/comment", // 매핑된 요청 url
		type: "POST",
		async: true,
		data: {
			"comment": JSON.stringify(commentInfo)
		},
		datatype : "json",
		success : function (data){
			let comment = JSON.parse(data);
			let html = "";
			html += 
			
			
			$('#comment_list').append(html);
		}
	});
	
	
})
*/



