function insertwish(user_code,game_code){
			let beforeWishBtn = "#bwish"+game_code;
			let afterWishBtn = "#awish"+game_code;
	    	$.ajax({
				url: "/insert_wish",
	            type: "POST",
	            async: true,
	            data: {
						"user_code" : user_code,
	            		"game_code" : game_code
	            },
	            success: function() {
						alert("찜목록 넣기 성공");
           		}
            });
	    	$(beforeWishBtn).css('display', 'none');
        	$(afterWishBtn).css('display', 'inline-block');
			}

function insertcart(user_code, game_code){
			let beforeCartBtn = "#bcart"+game_code;
			let afterCartBtn = "#acart"+game_code;
	    	$.ajax({
				url: "/insert_cart",
	            type: "POST",
	            async: true,
	            data: {
	            		"user_code" : user_code,
	            		"game_code" : game_code
	            },
	            success: function () {
	            	alert("장바구니 넣기 성공");
	            	
	           		}
	            });
	    	$(beforeCartBtn).css('display', 'none');
        	$(afterCartBtn).css('display', 'inline-block');
			}


function paybtn(user_code, game_code ,game_price){

		$.ajax({
			url:'/kakaopayOne' ,
			data: { "user_code" : user_code,
					"game_code" : game_code,
					"game_price": game_price
				   },
			dataType :'json',
			success:function(data){
				var box = data.next_redirect_pc_url;
				var _left = Math.ceil(( window.screen.width - 400 )/2);
			    var _top = Math.ceil(( window.screen.height - 1000 )/2)
				window.open(box,"kakaopay","width=400, height=700, left="+_left+", top="+_top);
				
			},
			error:function(error){
				alert(error);
			}
		});
			$(beforePayBtn).css('display', 'none');
	        $(afterPayBtn).css('display', 'inline-block');
	
};

function deleteReply(grade_code, game_code, currentPage, cntPerPage, pageSize){
	let id = "#mentListBox" + grade_code 
	$.ajax({
		url: "/reply/delete",
		type: "POST",
		async: true,
		data: {
			"grade_code" : grade_code
		},
		success: function () {
			let height = $(id).css("height").slice(0,-2);
			$(id).remove();
			if($("#reply_lists").children().length == 0) {
				movePage(game_code,currentPage, cntPerPage, pageSize);
			}
			changeHeight(height);
		}
	});
}

function change_reply(grade_code){
	let pid = '#reply_area'+grade_code;
	let uid = '#reply_update' + grade_code;
	let textarea = '#update_context' + grade_code;
	let textid = '#textCount' + grade_code;
	if($(uid).css('display')=='block'){
		alert('수정 중 입니다');
	} else {
		$(textarea).val($(pid).text());
		$(textid).text($(textarea).val().length);
	}
	$(pid).css('display','none');
	$(uid).css('display','block');
	
}

function edit_reply(grade_code){
	let pid = '#reply_area'+ grade_code;
	let uid = '#reply_update' + grade_code;
	let update_context = '#update_context' + grade_code;
	let grade_context = $(update_context).val();
	if (update_context.replace(/\s|　/gi, "").length == 0) {
    	alert("내용을 입력해주세요.");
    	$(update_context).focus();
 	} else {
		$.ajax({
			url : "/reply/update",
			data : {
				"grade_code" : grade_code,
				"grade_context" : grade_context
			},
			async : true,
			type : "POST",
			success: function(){
				$(pid).css('display','block');
				$(uid).css('display','none');
				$(pid).text(grade_context);
			}
		});
	}
}

function cancel() {
	let grade_code = $('#grade_code').val();
	let pid = '#reply_area'+grade_code;
	let uid = '#reply_update' + grade_code;
	$(pid).css('display','block');
	$(uid).css('display','none');
}




function inputReply(currentPage){
	let user_code = $("#user_code").val();
	let game_code = $("#game_code").val();
	let grade_context = $("#grade_context").val();
	let grade_rate = $('input[name="grade_rate"]:checked').val();
	
	if(grade_rate != null) {
		$.ajax({
			url : "/gamereply",
			data : {
				"user_code":user_code,
				"game_code":game_code,
				"grade_context":grade_context,
				"grade_rate":grade_rate
			},
			type : "POST",
			success : function(data) {
				html = "<div class='reply_list' id='mentListBox"+data.grade_code+"'>";
				html += "<a href='/profile/'"+user_code+"><img id='reply_picture' alt='프로필 사진' src='/image/profile/"+data.user_picture+"'></a>";
				html += "<div class='reply_content'>";
				html += "<span class='user_nickname' >"+data.user_nickname+"</span>";
				html += "<span class='grade_regdate' >"+dateFormat()+"</span>";
					if(data.grade_rate == 'g'){
						html += "<span id='grade_rateG'>Good</span>";
					}else if(data.grade_rate == 'b'){
						html += "<span id='grade_rateB'>Bad</span>";
					}
				html += "<p id='reply_area"+data.grade_code+"' class='grade_context'>"+data.grade_context+"</p>";
				html += "<div id='reply_update"+data.grade_code+"' class='reply_update'>";
				html += "<form action=''>";
				html += "<input id='grade_code' name='grade_code' type='hidden' value='"+data.grade_code+"'>";
				html += "<textarea id='update_context"+data.grade_code+"' class='update_context' name='grade_context'></textarea>";
				html += "<button type='button' onclick='cancel()'>취소</button>";
				html += "<button type='button' onclick='edit_reply("+data.grade_code+")'>작성</button>";
		        html += "</form>";
	 			html += "</div>";
				html += "</div>";
				html += "<button class='reply_deletebtn' onclick='deleteReply("+data.grade_code+")' >삭제</button>";
				html += "<button class='reply_deletebtn' onclick='change_reply("+data.grade_code+")' >수정</button>";
				html += "</div>";

				if(currentPage==1) {
					$("#reply_lists").prepend(html);
					if($("#reply_lists").children().length >= 5) {
						$("#reply_lists").children().last().remove();
					}	
				}
				changeHeight();
			},
			error : function(){
				alert('추가 실패');
			}	
		});

		$("#grade_context").val("");
		$("input[name='grade_rate']").prop("checked", false);
		$('.textCount').text('0');
	} else {
		alert("평점을 체크해주세요.");
	}

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

function changeHeight(height) {
	if (height == null) {
		height = $("#contents").css("height").slice(0, -2);
		height = (height-90)+"px";		
	} else {
		let heightA = $("#contents").css("height").slice(0, -2);
		height = (heightA-height-90)+"px";
	}
	$("#gameInfo").css("height", height);
	$("#barBackground").css("height", height);
}

$(document).ready(function() {
	$('#grade_context').keyup(function (e) {
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
	
	$('.update_context').keyup(function (e) {
		let content = $(this).val();
	    let code = $(this).prev().val();
	    let textid = '#textCount' + code;
	    // 글자수 세기
	    if (content.length == 0 || content == '') {
	    	$(textid).text('0');
	    } else {
	    	$(textid).text(content.length);
	    }
	    
	    // 글자수 제한
	    if (content.length > 230) {
	    	// 230자 부터는 타이핑 되지 않도록
	        $(this).val($(this).val().substring(0, 230));
	    }
	})
	changeHeight();
	
})

//페이지 이동
function movePage(game_code,currentPage, cntPerPage, pageSize){
    
    var url = "/gameDetailView";
    url = url + "?game_code="+game_code;
    url = url + "&currentPage="+currentPage;
    url = url + "&cntPerPage="+cntPerPage;
    url = url + "&pageSize="+pageSize;
    
    location.href=url;
}




