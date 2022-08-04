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

function inputComment(){
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
		success : function(data) {
			html = "<div class='comment_list' id='mentListBox"+data.comment_code+"'>";
			html += "<div class='comment_image'>";
			html += "<a> <img alt='프로필 사진' src='/image/profile/" + data.user_picture+"'> </a>";
			html += "</div>";
			html += "<div class='comment_content'>";
			html += "<div class='comment_nickname' >"+data.comment_nickname+"</div>";
			html += "<span class='comment_regdate' >"+dateFormat()+"</span>";
			html += "<div class='comment_context'>"+data.comment_context+"</div>";
			html += "<button class='comment_deletebtn' th:if='${"+data.user_code+" == #authentication.principal.user_code} or ${"+data.writer_code+" == #authentication.principal.user_code}' onclick='deleteComment("+data.comment_code+")' >삭제</button>";
			html += "</div>";
			html += "</div>";
			
			$("#reply_lists").prepend(html);
		},
		error : function(){
			alert('추가 실패');
		}	
	});
	$("#comment_text").val("");

}

function dateFormat(){
	let date = new Date();
    return date.getFullYear() + "년 " + (date.getMonth()+1) + "월 " + date.getDate() + "일 " + date.getHours() + "시 " + date.getMinutes() + "분 ";
 
}



