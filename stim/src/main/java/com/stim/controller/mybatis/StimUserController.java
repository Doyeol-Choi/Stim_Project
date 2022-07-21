package com.stim.controller.mybatis;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.user.StimUserService;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StimUserController {

	@Resource
	private final StimUserService stimUserService;
	
	// 회원가입 step3 이동
	@PostMapping("/registerS3")
	public ModelAndView registerFormStep3(UserVO uVo) {
		ModelAndView mav = new ModelAndView();
		System.out.println("왔습니까?");
		stimUserService.registerUser(uVo);
		
		mav.addObject("user", uVo);
		mav.setViewName("user/regFormStep3");
		
        return mav;
    }
}
