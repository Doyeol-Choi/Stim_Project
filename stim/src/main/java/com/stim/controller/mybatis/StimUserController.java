package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimUserService;
import com.stim.vo.GameVO;

@RestController
public class StimUserController {

	@Resource
	private StimUserService stimUserService;
	
	@RequestMapping(value="test")
	public ModelAndView SelectAllUserView(GameVO gVo) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		List<GameVO> list = stimUserService.SelectAllGame();
		
		mav.addObject("list", list);
		mav.setViewName("user/test");
		
        return mav;
	}
}
