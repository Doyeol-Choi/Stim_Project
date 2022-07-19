package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimUserService;
import com.stim.vo.UserVO;

@RestController
public class StimUserController {

	@Resource
	private StimUserService stimUserService;
	
	@RequestMapping(value="user")
	public ModelAndView SelectAllUserView(UserVO uVo) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		List<UserVO> list = stimUserService.SelectAllUser();
		
		mav.addObject("list", list);
        mav.setViewName("user");
        
        return mav;
	}
}
