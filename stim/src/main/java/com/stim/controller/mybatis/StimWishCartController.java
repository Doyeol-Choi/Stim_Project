package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.stim.service.mybatis.StimWishCartService;
import com.stim.vo.CartVO;
import com.stim.vo.WishVO;

@RestController
public class StimWishCartController {

	@Resource
	private StimWishCartService stimWishCartService;

	@GetMapping("/wish/{user_code}")
	public ModelAndView SelectWishGame(@PathVariable("user_code") int user_code) throws Exception {
			
		ModelAndView mav = new ModelAndView();
		
		List<WishVO> list = stimWishCartService.SelectWishGame(user_code);
	
		mav.addObject("list", list);
		mav.setViewName("wishcart/wish");

        return mav;
	}

	
	@GetMapping("/cart/{user_code}")
	public ModelAndView SelectCartGame(@PathVariable("user_code")int user_code) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		List<CartVO> list = stimWishCartService.SelectCartGame(user_code);
		List<CartVO> total  = stimWishCartService.TotalPriceGame(user_code);
	
		mav.addObject("list", list);
		mav.addObject("total", total);
        mav.setViewName("wishcart/cart");
        
        return mav;
	}
	
	//찜목록 삭제	
//	  @GetMapping("/delete_wish") 
//	  public RedirectView DeleteWishGame(
//			  @RequestParam("wish_code")int wish_code,
//			  @RequestParam(value="user_code")int user_code) throws Exception {
//	  stimWishCartService.DeleteWishGame(wish_code);  
//	  
//	  String asd = "/wish/"+ user_code;
//	  
//	  return new RedirectView(asd);
//	  }
	 
	 // ajax 사용 찜목록 삭제
	@PostMapping("/wish/delete_wish") 
	  public void DeleteWishGame(@RequestParam("wish_code") int wish_code) throws Exception {

		stimWishCartService.DeleteWishGame(wish_code);  
	  
	  }
	
	 // ajax 사용 장바구니 삭제
	@PostMapping("/cart/delete_cart") 
	  public void DeleteCartGame(@RequestParam("cart_code") int cart_code) throws Exception {

		stimWishCartService.DeleteCartGame(cart_code);  
	  
	  }
	
	  // 찜목록에서 장바구니 넣기
	  @GetMapping("/insert_cart") 
	  public RedirectView InsertCartGame(
			  @RequestParam("user_code") int user_code,
			  @RequestParam("game_code") int game_code) throws Exception {
	  
		  stimWishCartService.InsertCartGame(user_code, game_code);  
  
		  String url = "/wish/"+ user_code;
		  
		  return new RedirectView(url);
	  }
	
	
	
	// ajax 사용 찜목록에서 장바구니 넣기
//	@PostMapping("/wish/insert_cart") 
//	  public void InsertCartGame(
//			  @RequestParam("user_code") int user_code,
//			  @RequestParam("game_code") int game_code) throws Exception {
//		
//		System.out.println(user_code);
//		System.out.println(game_code);
//		stimWishCartService.InsertCartGame(user_code, game_code);  
//	  
//	  }
	 
}

