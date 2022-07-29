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