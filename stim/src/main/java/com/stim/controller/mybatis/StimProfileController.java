package com.stim.controller.mybatis;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimProfileService;
import com.stim.service.user.StimUserService;
import com.stim.vo.UserVO;

@RestController
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;
	@Resource private StimUserService stimUserService;

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
			//System.out.println("프로필 가져오기: " + uVo);
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
			System.out.println("주소로 받아온 유저 : "+uVo.getUser_nickname());
			//DB 불러오기 테스트용
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@PostMapping("/profile/edit/update")
	public ModelAndView profileNewUpdate(@Valid UserVO uVo, Errors errors) {
		ModelAndView mav = new ModelAndView();
		System.out.println("테스트 1");
		if (errors.hasErrors()) {
			mav.addObject("user", uVo);
			
			Map<String, String> validatorResult = stimUserService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
            	mav.addObject(key, validatorResult.get(key));
            }
			
            System.out.println("테스트2");
			mav.setViewName("profile/edit/profileUpdate");
			return mav;
		}
		
		try {
			//SelectByIdForUpdate
			stimProfileService.SelectByIdForUpdate(uVo);	
			mav.addObject("user", uVo);
			mav.setViewName("profile/edit/profileUpdate");
			//DB 불러오기 테스트용
			System.out.println("특정 누군가 프로필 수정 : " + uVo.getUser_nickname());
		}catch(Exception e) {
			System.out.println("프로필 업데이트 안됨.");
			e.printStackTrace();
		}
		return mav;
	}

	
}
