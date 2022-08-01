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
};