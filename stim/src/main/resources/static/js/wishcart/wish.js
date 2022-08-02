
		function delwish(wish_code){
			let id = "#wishListBox" + wish_code
	         $.ajax({
	            url: "delete_wish",
	               type: "POST",
	               async: true,
	               data: {
	                  "wish_code" : wish_code
	               },
	               success: function () {
	                  $(id).remove();
	               }
	          });
	      }
		
		 function insertcart(user_code, game_code){
			let wishBtn = "#wish"+game_code;
			let cartBtn = "#cart"+game_code;
	    	$.ajax({
				url: "/insert_cart",
	            type: "POST",
	            async: true,
	            data: {
	            		"user_code" : user_code,
	            		"game_code" : game_code
	            	//"insertCart":JSON.stringify()
	            	//"command": command
	            },
	            success: function() {
	            	alert("장바구니 넣기 성공");
	            	
	           		}
	            //error: function (error){
	           	//        alert("에러");
	            //	    } 
	            });
	    	$(wishBtn).css('display', 'none');
        	$(cartBtn).css('display', 'inline-block');
			}
		 
	