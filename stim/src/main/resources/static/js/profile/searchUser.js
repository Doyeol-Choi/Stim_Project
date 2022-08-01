function friendRequset(user_code){
	let before = "#beforeAjax" + user_code;
	let after1 = "#afterAjax1_" + user_code;
	let after2 = "#afterAjax2_" + user_code;
	$.ajax({
		url:'/addFriend' ,
		data: {
			"user_code" : user_code,
			   },
		success:function(){
			$(before).css('display', 'none');
			$(after1).css('display', 'none');
			$(after2).css('display', 'inline-block');
		},
		error:function(){
			alert("친구 요청에 실패했습니다.");
		}
	});
};

function friendRequsetCancle(user_code){
	let before = "#beforeAjax" + user_code;
	let after1 = "#afterAjax1_" + user_code;
	let after2 = "#afterAjax2_" + user_code;
	$.ajax({
		url:'/friendRequestCancle' ,
		data: {
			"user_code" : user_code,
			   },
		success:function(){
			$(before).css('display', 'none');
			$(after1).css('display', 'inline-block');
			$(after2).css('display', 'none');
		},
		error:function(){
			alert("친구 요청 취소에 실패했습니다.");
		}
	});
};