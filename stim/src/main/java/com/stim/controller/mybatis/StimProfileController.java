package com.stim.controller.mybatis;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.stim.service.mybatis.StimProfileService;

@RestController
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;

	public void setStimProfileService(StimProfileService stimProfileService) {
		this.stimProfileService = stimProfileService;
	}

	/* 프로필 */
	@GetMapping("/profile/{user_id}")
	public ModelAndView profileList(@PathVariable("user_id") String user_id) {
		ModelAndView mav = new ModelAndView();
		
		try {
			UserVO uVo = stimProfileService.SelectById(user_id);
			mav.addObject("user", uVo);
			
			mav.setViewName("profile/profile");
			
			//DB 불러오기 테스트용
			System.out.println("프로필 몇개 출력 : " + uVo);
			System.out.println("뭐임 ? : " + uVo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@GetMapping("/profile/edit/{user_id}")
	public ModelAndView profileUpdate(@PathVariable("user_id") String user_id) {
		ModelAndView mav = new ModelAndView();
		
		try {
			//SelectByIdForUpdate
			UserVO uVo = stimProfileService.SelectById(user_id);
			mav.addObject("user", uVo);
			
			mav.setViewName("profile/edit/profileUpdate");
			
			//DB 불러오기 테스트용
			System.out.println("특정 누군가 프로필 전달 : " + uVo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
//	@RequestMapping(value="/profile")
//    public ModelAndView AllListView(Map<String, Object> map) throws Exception{
//        ModelAndView mav = new ModelAndView();
//        
//        List<Map<String, Object>> AllList = stimProfileService.SelectById();
//        
//        mav.addObject("Alllist", AllList);
//        mav.setViewName("profile/profile");
//        return mav;
//    }
	
}
