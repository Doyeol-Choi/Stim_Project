function delcart(cart_code){
	let id = "#cartListBox" + cart_code
	$.ajax({
		url: "delete_cart",
		type: "POST",
		async: true,
		data: {
			"cart_code" : cart_code
		},
		success: function () {
			$(id).remove();
		}
	});
}
function paybtn(user_code, total){

	$.ajax({
		url:'/kakaopayAll' ,
		data: { "user_code" : user_code,
				"total":total
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
};