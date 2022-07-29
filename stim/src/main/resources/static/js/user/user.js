function checkId() {
	// 아이디를 입력받아야 함
	if(document.getElementById('user_id').value.length == 0){
		alert("아이디를 입력해 주세요");
		document.getElementById('user_id').focus();
		return false;
	}
	
	let url = "CheckId?user_id="+document.getElementById('user_id').value;
	
	window.open(url, "_blank_1", "toolbar=no, menubar=no, scrollbar=yes, resizeable=no, width=450, height=200");
}

function checkIdUse() {
	opener.regFrom.user_id.value = document.getElementById('user_id').value;
	opener.regFrom.chkId.value = 'check';
	
	self.close();
}

function checkReg() {
	if(document.getElementById('chkId').value != 'check'){
		alert("아이디 중복체크를 해주세요.");
		document.getElementById('user_id').focus();
		return false;
	}
	/*
	if(document.getElementById('user_password').value != document.getElementById('password_confirm').value){
		alert("비밀번호가 일치하지 않습니다.");
		
		document.getElementById('user_password').value = "";
		document.getElementById('password_confirm').value = "";
		
		document.getElementById('user_password').focus();
		return false;
	}
	*/
	checkPw();
}

function checkPw() {
	if(document.getElementById('user_password').value != document.getElementById('password_confirm').value){
		alert("비밀번호가 일치하지 않습니다.");
		
		document.getElementById('user_password').value = "";
		document.getElementById('password_confirm').value = "";
		
		document.getElementById('user_password').focus();
		return false;
	}
	
	if(document.getElementById('user_password').value.length < 4 || document.getElementById('user_password').value.length > 12) {
		alert("비밀번호는 4~12자리로 만들어주세요.");
		
		document.getElementById('user_password').value = "";
		document.getElementById('password_confirm').value = "";
		
		document.getElementById('user_password').focus();
		return false;
	}
}