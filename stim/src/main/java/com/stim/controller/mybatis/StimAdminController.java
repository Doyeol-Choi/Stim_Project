package com.stim.controller.mybatis;


import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.service.user.StimUserService;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StimAdminController {
	
	@Resource
	private final StimUserService stimUserService;
	
	@Resource
	private StimGameListService stimGameListService;
	
	// 관리자 페이지로 이동
	@GetMapping("/adminPage")
	public ModelAndView adminPage(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				mav.setViewName("/admin/admin");
				return mav; 
			}
		}
		mav.setViewName("index");
		return mav;
	}
	
	// 할인 목록 갱신
	@GetMapping("/changeDiscountList")
	public void changeDiscountList(Authentication authentication) {
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				try {
					stimGameListService.discountListRemove();
					List <Integer> list = stimGameListService.randomGame(38);	// 추후 게임 목록 개수로 변경
					Random random = new Random();
					for (int code : list) {
						int discount = (random.nextInt(19)+1) * 5;
						stimGameListService.createDiscountList(discount, code);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
