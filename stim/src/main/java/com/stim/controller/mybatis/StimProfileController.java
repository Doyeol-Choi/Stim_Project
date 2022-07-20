package com.stim.controller.mybatis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimProfileService;
import com.stim.vo.ProFileCommentVO;
import com.stim.vo.UserVO;

@RestController
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;

	public void setStimProfileService(StimProfileService stimProfileService) {
		this.stimProfileService = stimProfileService;
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

	
	
	@RequestMapping(value="/profile")
    public ModelAndView AllListView(Map<String, Object> map) throws Exception{
        ModelAndView mav = new ModelAndView();
        
        List<Map<String, Object>> AllList = stimProfileService.SelectById();
        
        mav.addObject("Alllist", AllList);
        mav.setViewName("profile/profile");
        return mav;
    }
	

	
}
