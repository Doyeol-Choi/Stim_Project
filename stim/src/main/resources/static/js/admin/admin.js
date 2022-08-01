function changeDiscount() {
	$.ajax({
		url: "/changeDiscountList",
			type: "Get",
			async: true,
			success: function () {
				alert("할인 게임 목록이 갱신되었습니다.")
			},
			error: function() {
				alert("갱신에 실패했습니다.")
			}
      });
}