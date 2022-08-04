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
				window.open(box);
			},
			error:function(error){
				alert(error);
			}
		});
			$(beforePayBtn).css('display', 'none');
	        $(afterPayBtn).css('display', 'inline-block');
	
};

function deleteReply(grade_code){
	let id = "#mentListBox" + grade_code 
	$.ajax({
	    url: "/reply/delete",
	       type: "POST",
	       async: true,
	       data: {
	          "grade_code" : grade_code
	       },
	       success: function () {
	          $(id).remove();
	       }
	});
}

function inputReply(){
	let user_code = $("#user_code").val();
	let game_code = $("#game_code").val();
	let grade_context = $("#grade_context").val();
	let grade_rate = $("#grade_rate").val();
		
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
			html += "<div class='reply_image'>";
			html += "<a> <img alt='프로필 사진' src='/image/profile/" + data.user_picture+"'> </a>";
			html += "</div>";
			html += "<div class='grade_content'>";
			html += "<div class='user_nickname' >"+data.user_nickname+"</div>";
			html += "<span class='grade_regdate' >"+dateFormat()+"</span>";
			html += "<div class='grade_context'>"+data.grade_context+"</div>";
			html += "<div class='grade_rate'>"+data.grade_rate+"</div>";
			html += "<button class='grade_deletebtn' th:if='${"+data.user_code+" == #authentication.principal.user_code}' onclick='deleteReply("+data.grade_code+")' >삭제</button>";
			html += "</div>";
			html += "</div>";
			
			$("#reply_lists").prepend(html);
		},
		error : function(){
			alert('추가 실패');
		}	
	});
	$("#reply_text").val("");

}

function dateFormat(){
	let date = new Date();
    return date.getFullYear() + "년 " + (date.getMonth()+1) + "월 " + date.getDate() + "일 " + date.getHours() + "시 " + date.getMinutes() + "분 ";
 
}



