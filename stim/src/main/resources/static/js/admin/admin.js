/* 할인 목록 갱신 */
function changeDiscount() {
	$.ajax({
		url: "/changeDiscountList",
			type: "Post",
			async: true,
			success: function () {
				alert("할인 게임 목록이 갱신되었습니다.")
			},
			error: function() {
				alert("갱신에 실패했습니다.")
			}
      });
}

/* 게임 삭제 */
function deleteGame(game_code) {
	$.ajax({
		url: "/deleteGame",
			type: "Post",
			data: {
				"game_code" : game_code
			},
			async: false,
			success: function () { 
			}
      });
	alert("게임을 삭제 했습니다.");
	setTimeout(function() {
		document.location.reload();				
	}, 2000);  
}

/* 프로필 사진 초기화 */
function rePicture(user_code) {
	$.ajax({
		url: "/userPictureRe",
			type: "Post",
			data: {
				"user_code" : user_code
			},
			async: false,
			success: function () { 
			}
      });
	alert("해당 유저의 프로필 사진을 초기화했습니다.");
	setTimeout(function() {
		document.location.reload();				
	}, 2000);     
}

/* 유저 삭제 */
function userDelete(user_code) {
	$.ajax({
		url: "/userDelete",
			type: "Post",
			data: {
				"user_code" : user_code
			},
			async: false,
			success: function () {  
			}
      });
	alert("해당 유저를 삭제 했습니다.");
	setTimeout(function() {
		document.location.reload();				
	}, 2000);  
}