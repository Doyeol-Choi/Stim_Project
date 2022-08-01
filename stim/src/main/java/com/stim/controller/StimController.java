package com.stim.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.vo.GameVO;


@RestController
public class StimController {
	
	@Resource
	private StimGameListService stimGameListService;

	// 주소창에 입력 없을때 메인페이지로 유도
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		
		try {
			List<GameVO> newList = stimGameListService.SelectNewestGameListMain();
			List<GameVO> popList = stimGameListService.SelectPopularGameListMain();
			List<String> tagList = stimGameListService.RandomTagMain();
			mav.addObject("newList", newList);
			mav.addObject("popList", popList);
			mav.addObject("tagList", tagList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("index");
        return mav;
    }
	
}
