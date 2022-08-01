function delcart(cart_code, game_price){
	let id = "#cartListBox" + cart_code
	$.ajax({
		url: "delete_cart",
		type: "POST",
		async: true,
		data: {
			"cart_code" : cart_code
		},
		success: function (data) {
			$(id).remove();
		}
	});
}
function paybtn(user_code, total){

	$.ajax({
		url:'/kakaopay' ,
		data: { "user_code" : user_code,
				"total":total
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