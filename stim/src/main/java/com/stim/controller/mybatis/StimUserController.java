package com.stim.controller.mybatis;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.user.StimUserService;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SessionAttributes("user")
public class StimUserController {

	@Resource
	private final StimUserService stimUserService;

	// 회원가입 step3 이동
	@PostMapping("/registerS3")
	public ModelAndView registerFormStep3(UserVO uVo) {
		ModelAndView mav = new ModelAndView();
		
		stimUserService.registerUser(uVo);
		
		mav.addObject("regUser", uVo);
		mav.setViewName("user/regFormStep3");
		
        return mav;
    }
	
	// 로그인 성공시 메인페이지로
//	@PostMapping("/login_success")
//	public ModelAndView login(Authentication authentication) {
//        ModelAndView mav = new ModelAndView();
//        
//        UserVO uVo = (UserVO) authentication.getPrincipal();
//        System.out.println(uVo.getUser_nickname());
//        System.out.println("땡!");
//        mav.addObject("user", uVo);
//        mav.setViewName("user/regFormStep3");
//		
//		return mav;
//    }

}
