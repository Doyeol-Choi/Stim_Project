package com.stim.controller.wishcart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishCartController {
	
//	 //찜목록
	@GetMapping("/wish")
	public String wish() {
	   return "wishcart/wish";
	}
	//장바구니
	@GetMapping("/cart")
	public String cart() {
		return "wishcart/cart";
		}

}
