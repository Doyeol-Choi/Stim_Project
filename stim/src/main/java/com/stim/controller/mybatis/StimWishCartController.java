package com.stim.controller.mybatis;

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
	@GetMapping("/cart")
	public ModelAndView InsertCartGame(@RequestParam("user_code")int user_code,@RequestParam("game_code")int game_code) throws Exception {
		 
		 ModelAndView mav = new ModelAndView();
		 
		 
		 stimWishCartService.InsertCartGame(user_code,game_code);
		 
		 List<CartVO> list = stimWishCartService.SelectCartGame(user_code);
		 
		
		 mav.addObject("list", list);
		 mav.setViewName("wishcart/cart");
		 
		 return mav;

	}
	@GetMapping("/cart/{user_code}")
	public ModelAndView SelectCartGame(@PathVariable("user_code")int user_code) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		List<CartVO> list = stimWishCartService.SelectCartGame(user_code);
		
		mav.addObject("list", list);
        mav.setViewName("wishcart/cart");
        
        return mav;
	}
	
	
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
	 
	
	@PostMapping("/wish/delete_wish") 
	  public void DeleteWishGame(
			  @RequestParam("wish_code") int wish_code) throws Exception {

		stimWishCartService.DeleteWishGame(wish_code);  
	  
	  }
	 
}

