package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimWishCartService;
import com.stim.vo.CartVO;
import com.stim.vo.WishVO;

@RestController
public class StimWishCartController {

	@Resource
	private StimWishCartService stimWishCartService;
	
	// 예시 => 수정해서 사용
	@RequestMapping(value="wish")
	public ModelAndView SelectAllUserView(WishVO wVo) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		List<WishVO> list = stimWishCartService.SelectWishGame();
		
		mav.addObject("list", list);
        mav.setViewName("wish");
               return mav;
	}
	
	@RequestMapping(value="cart")
	public ModelAndView SelectAllUserView(CartVO cVo) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		List<CartVO> list = stimWishCartService.SelectCartGame();
		
		mav.addObject("list", list);
        mav.setViewName("cart");
               return mav;
	}
	
	
}
