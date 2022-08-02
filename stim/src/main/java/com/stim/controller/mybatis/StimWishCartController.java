package com.stim.controller.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.stim.service.mybatis.StimWishCartService;
import com.stim.vo.CartVO;
import com.stim.vo.UserVO;
import com.stim.vo.WishVO;

@RestController
public class StimWishCartController {

	@Resource
	private StimWishCartService stimWishCartService;

	// 찜목록
	@GetMapping("/wish/{user_code}")
	public ModelAndView SelectWishGame(@PathVariable("user_code") int user_code, Authentication authentication) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if(uVo.getUser_code()==user_code) {
			
			List<WishVO> wlist = stimWishCartService.SelectWishGame(user_code);
			List<Integer> game_code = stimWishCartService.SelectCartGameCode(user_code);
			if(game_code.isEmpty()) {
				game_code= new ArrayList<>();
			}
			
			mav.addObject("wlist", wlist);
			mav.addObject("game_code", game_code);
			mav.setViewName("wishcart/wish");
	
	        return mav;
			}
		}
		mav.setViewName("redirect:/loginForm");
		return mav;
	}

	// 장바구니 목록
	@GetMapping("/cart/{user_code}")
	public ModelAndView SelectCartGame(@PathVariable("user_code")int user_code, Authentication authentication) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if(uVo.getUser_code()==user_code) {
				List<CartVO> list = stimWishCartService.SelectCartGame(user_code);
				Integer total  = stimWishCartService.TotalPriceGame(user_code);
			
				mav.addObject("list", list);
				mav.addObject("total", total);
		        mav.setViewName("wishcart/cart");
		        
		        return mav;
					}
		}
		mav.setViewName("redirect:/loginForm");
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
	
	//장바구니 삭제	
	  @GetMapping("/delete_cart") 
	  public RedirectView DeleteWishGame(
			  @RequestParam("cart_code")int cart_code,
			  @RequestParam(value="user_code")int user_code) throws Exception {
	  stimWishCartService.DeleteCartGame(cart_code);  
	  
	  String asd = "/cart/"+ user_code;
	  
	  return new RedirectView(asd);
	  }
	 
	  
	 // ajax 사용 찜목록 삭제
	@PostMapping("/wish/delete_wish") 
	  public void DeleteWishGame(@RequestParam("wish_code") int wish_code) throws Exception {

		stimWishCartService.DeleteWishGame(wish_code);  
		
	  }
	
	 // ajax 사용 장바구니 삭제
//	@PostMapping("/cart/delete_cart") 
//	  public void DeleteCartGame(@RequestParam("cart_code") int cart_code) throws Exception {
//
//		stimWishCartService.DeleteCartGame(cart_code);  
//		
//	  }
	
	  // 찜목록에서 장바구니 넣기
//	  @GetMapping("/insert_cart") 
//	  public RedirectView InsertCartGame(
//			  @RequestParam("user_code") int user_code,
//			  @RequestParam("game_code") int game_code) throws Exception {
//	  
//		  stimWishCartService.InsertCartGame(user_code, game_code);  
//  
//		  String url = "/wish/"+ user_code;
//		  
//		  return new RedirectView(url);
//	  }
	
	
	// ajax 사용 상세보기에서 찜목록 넣기
	@PostMapping("/insert_wish") 
	  public RedirectView InsertWishGame(
			  	@RequestParam("game_code") int game_code,
			  	@RequestParam("user_code") int user_code) throws Exception {
		
			stimWishCartService.InsertWishGame(user_code, game_code);  
			
			String url = "/gameDetailView?game_code="+game_code;
			
			return new RedirectView(url);
		}
	
		

	// ajax 사용 찜목록, 게임 상세보기에서 장바구니 넣기
	@PostMapping("/insert_cart") 
	  public RedirectView InsertCartGame(
			  @RequestParam("user_code") int user_code,
			  @RequestParam("game_code") int game_code) throws Exception {
		
		stimWishCartService.InsertCartGame(user_code, game_code);  
		
		String url = "/gameDetailView?game_code="+game_code;
		return new RedirectView(url);
	  }
	
	
	// 장바구니에서 결제완료
	  @GetMapping("/paysuccessAll/{user_code}") 
	  public RedirectView PaySuccessAll(
			  @PathVariable("user_code") int user_code) throws Exception {
		  
		  List<CartVO> list = stimWishCartService.SelectCartGame(user_code);
		  for(int i=0; i<list.size(); ++i){
			  
//			 int user_code = list.get(i).getUser_code();
			 int game_code = list.get(i).getGame_code();
			 
			 stimWishCartService.InsertMyGame(user_code, game_code);
			 stimWishCartService.DeleteWishAllGame(user_code, game_code);
			 stimWishCartService.DeleteCartAllGame(user_code, game_code); 
		  }
		  	 
		  
		  String main = "/";

		  return new RedirectView(main);
	  
	  }
	// 바로 결제완료
		  @GetMapping("/paysuccessOne/{user_code},{game_code}") 
		  public RedirectView PaySuccessOne(
				  @PathVariable("user_code") int user_code,
				  @PathVariable("game_code") int game_code) throws Exception {
			  
				 
				 stimWishCartService.InsertMyGame(user_code, game_code);
				 stimWishCartService.DeleteWishAllGame(user_code, game_code);
			  
			  
				 stimWishCartService.DeleteCartAllGame(user_code, game_code);  
			  
			  String main = "/";

			  return new RedirectView(main);
		  
		  }
	
	
	
	
	
}

