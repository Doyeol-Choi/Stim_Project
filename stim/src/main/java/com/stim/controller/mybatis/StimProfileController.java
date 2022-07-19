package com.stim.controller.mybatis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimProfileService;
import com.stim.vo.UserVO;

@RestController
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;
	
	@RequestMapping(value="profile")
	public ModelAndView AllListView(UserVO uVo) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		List<UserVO> AllList = stimProfileService.SelectById();
		
		mav.addObject("Alllist", AllList);
		mav.setViewName("list");
	}
	
	//받는거 이후 하기 
	@GetMapping("/profile")
	public String profile(UserVO uVo, Model model ) {
		//UserVO uVo = //stimProfileService.
		return "";
	}
	
	// 예시 => 수정해서 사용
//	@RequestMapping(value="user")
//	public ModelAndView SelectAllUserView(UserVO uVo) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		
//		List<UserVO> list = stimProfileService.SelectAllUser();
//		
//		mav.addObject("list", list);
//        mav.setViewName("user");
//        
//        return mav;
//	}
	
	
	
	@RequestMapping("/profile")
	public ModelAndView AllListView(Map<String, Object> map) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		List<UserVO> AllList = stimProfileService.SelectById();
		
		mav.addObject("Alllist",AllList);
		mav.setViewName("list");
		
		return mav;
	}
}
