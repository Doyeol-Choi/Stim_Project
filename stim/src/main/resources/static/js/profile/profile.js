function changPicture(user_code) {
	let url = "/changePicture?user_code="+user_code.toString();
	
	window.open(url, "_blank_1", "toolbar=no, menubar=no, scrollbar=yes, resizeable=no, width=450, height=300");
}


function updateCancle() {
	self.close();
}

function popupClose() {
	$("#pictureForm").submit();
	$("#textBox").css("display", "block");
	setTimeout(function() {   
		opener.parent.location.reload();
		self.close();
    }, 2400);
}

function deleteComment(comment_code, user_code, currentPage, cntPerPage, pageSize){
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
			if($("#comment_lists").children().length == 0) {
				movePage(user_code, currentPage, cntPerPage, pageSize);
			}
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


function deleteMyFriend(friend_code){
	let id = "#friend_List" + friend_code
	$.ajax({
		
		url: "/friend/delete",
		type: "POST",
		async: true,
		data : {
			"friend_code" : friend_code
		},
		success : function(){
			$(id).remove();
			
		},
		error : function(){
			alert('친구 삭제 실패');
		}
	});
}

function inputComment(currentPage){
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
					html += "<div id='comment_context"+data.comment_code+"'  class='comment_context'>"+data.comment_context+"</div>";

					html += "<div id='comment_fix"+data.comment_code+"' class='comment_fix'>";
						html += "<form action=''>";	
							html += "<input id='comment_code' name='comment_code' type='hidden' value='"+data.comment_code+"'>";
							html += "<textarea id='comment_fixContext"+data.comment_code+"' class='comment_fixContext' name='profile_fixContext'></textarea>";
							html += "<button id='comment_fixbtn"+data.comment_code+"' class='comment_fixbtn' type='button' onclick='edit_comment("+data.comment_code+")'>작성</button>";
		        			html += "<button class='comment_fixbtn2' type='button' onclick='edit_comment_cancle("+data.comment_code+")'>취소</button>	";
		        			html += "<div class='limitBox'>";
		        				html += "<span id='textCount"+data.comment_code+"' class='textCount3'>0</span>";	
								html += "<span class='textTotal3'>/100</span>";		
		        			html += "</div>";
		        		html += "</form>";	
	        		html += "</div>";
					html += "<button type='button' class='comment_updatebtn' onclick='changeComment("+data.comment_code+")'> 수정 </button>";									
					html += "<button sec:authorize='isAuthenticated()' class='comment_deletebtn' th:if='${"+data.user_code+" == #authentication.principal.user_code} or ${"+data.writer_code+" == #authentication.principal.user_code}' onclick='deleteComment("+data.comment_code+","+user_code+","+currentPage+", 5, 10)' >삭제</button>";
				html += "</div>";
			html += "</div>";
			
			if(currentPage == 1){
				$("#comment_lists").prepend(html);
				if($("#comment_lists").children().length >= 6) {
					$("#comment_lists").children().last().remove();
				}
			}	
		},
		error : function(){
			alert('추가 실패');
		}	
	});
	
	$("#comment_text").val("");
	$('.textCount2').text('0');
}

function dateFormat(){
   let date = new Date();
   let dateText;
   if(date.getMonth() < 10 && date.getDay() < 10) {
      dateText = date.getFullYear() + "년 0" + (date.getMonth()+1) + "월 0" + date.getDate() + "일 " + date.getHours() + "시 " + date.getMinutes() + "분";
   } else if(date.getDay() < 10) {
      dateText = date.getFullYear() + "년 " + (date.getMonth()+1) + "월 0" + date.getDate() + "일 " + date.getHours() + "시 " + date.getMinutes() + "분";
   } else if(date.getMonth() < 10) {
      dateText = date.getFullYear() + "년 0" + (date.getMonth()+1) + "월 " + date.getDate() + "일 " + date.getHours() + "시 " + date.getMinutes() + "분";
   }   
    return dateText;
}

function pwdCheck(user_code) {
	let url = "/pwdCheck?user_code="+user_code;
	
	window.open(url, "_blank_1", "toolbar=no, menubar=no, scrollbar=yes, resizeable=no, width=400, height=200");
}

// 할 말 수정 버튼 클릭
function changeMessage(){
	if($('#profile_textarea').css('display')=='block'){
		alert('수정 중 입니다');
	} else {
		$('#profile_context').val($('#profile_introduce').text());
		$('.textCount').text($('#profile_context').val().length);
	}
	$('#profile_introduce').css('display','none');
	$('#profile_textarea').css('display','block');
	
}

// 할 말 수정
function edit_context(){
	let user_code = $('#profileUser_code').val();
	let profile_context = $('#profile_context').val();
	if (profile_context.replace(/\s|　/gi, "").length == 0) {
    	alert("내용을 입력해주세요.");
    	$("#profile_context").focus();
 	} else {
		$.ajax({
			url : "/updateContext",
			data : {
				"user_code" : user_code,
				"profile_context" : profile_context
			},
			async : true,
			type : "POST",
			success: function(){
				$('#profile_introduce').css('display','block');
				$('#profile_textarea').css('display','none');
				$('#profile_introduce').text(profile_context);
			}
		});
	}
}

function cancelMessage() {
	$('#profile_introduce').css('display','block');
	$('#profile_textarea').css('display','none');
}

$(document).ready(function(){
	$('#profile_context').keyup(function(e){
		let content = $(this).val();
		
		// 글자수 세기
	    if (content.length == 0 || content == '') {
	    	$('.textCount').text('0');
	    } else {
	    	$('.textCount').text(content.length);
	    }
	    
	    // 글자수 제한
	    if (content.length > 230) {
	    	// 230자 부터는 타이핑 되지 않도록
	        $(this).val($(this).val().substring(0, 230));
	    }	
	})
	
	$('#comment_text').keyup(function(e){
		let content = $(this).val();
		
	    if (content.length == 0 || content == '') {
	    	$('.textCount2').text('0');
	    } else {
	    	$('.textCount2').text(content.length);
	    }
	    
	    if (content.length > 100) {
	        $(this).val($(this).val().substring(0, 100));
	    }	
	})
	
	$('.comment_fixContext').keyup(function(e){
		let code = $(this).prev().val();
		let content = $(this).val();
		let textCount = '#textCount' + code;
	    if (content.length == 0 || content == '') {
	    	$(textCount).text('0');
	    } else {
	    	$(textCount).text(content.length);
	    }
	    
	    if (content.length > 100) {
	        $(this).val($(this).val().substring(0, 100));
	    }	
	})
})

//페이지 이동
function movePage(user_code,currentPage, cntPerPage, pageSize){
    
    var url = "/profile";
    url = url + "/"+user_code;
    url = url + "?currentPage="+currentPage;
    url = url + "&cntPerPage="+cntPerPage;
    url = url + "&pageSize="+pageSize;
    
    location.href=url;
}


// 댓글 수정 버튼 클릭
function changeComment(comment_code){
	$('.comment_fixContext').keyup(function(e){
		let fixContext = "#comment_fixContext" + comment_code;
		let content = $(fixContext).val();
		let textCount = '#textCount' + comment_code;
	    if (content.length == 0 || content == '') {
	    	$(textCount).text('0');
	    } else {
	    	$(textCount).text(content.length);
	    }
	    
	    if (content.length > 100) {
	        $(fixContext).val($(fixContext).val().substring(0, 100));
	    }	
	})
	let fixcontext = "#comment_fix"+comment_code;
	let context = "#comment_context"+comment_code;
	let textarea = "#comment_fixContext"+comment_code;
	let count = "#textCount"+comment_code;
	if($(fixcontext).css('display')=='block'){
		alert('수정 중 입니다');
	} else {
		$(textarea).val($(context).text());
		$(count).text($(textarea).val().length);
	}
	$(context).css('display','none');
	$(fixcontext).css('display','block');
}

// 댓글 수정
function edit_comment(comment_code){
	let comment_fixContext = $('#comment_fixContext'+comment_code).val();
	let comment_context = '#comment_context'+comment_code;
	let comment_fix = '#comment_fix'+comment_code;
	//let comment_fixContext = $('#comment_fixContext').val();
	
	
	if (comment_fixContext.replace(/\s|　/gi, "").length == 0) {
    	alert("내용을 입력해주세요.");
    	$("#comment_fixContext").focus();
 	} else {
		$.ajax({
			url : "/updateComment",
			data : {
				"comment_code" : comment_code,
				"comment_context" : comment_context
			},
			async : true,
			type : "POST",
			success: function(){
				$(comment_context).css('display','block');
				$(comment_fix).css('display','none');
				$(comment_context).text(comment_fixContext);
			}
		});
	}
}

// 댓글 수정 취소
function edit_comment_cancle(comment_code) {
	let comment_context = '#comment_context'+comment_code;
	let comment_fix = '#comment_fix'+comment_code;
	$(comment_context).css('display','block');
	$(comment_fix).css('display','none');
}
