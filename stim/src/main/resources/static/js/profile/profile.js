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





