package com.stim.controller.mybatis;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	// 약관동의 후 올때만 step2로 이동
	@PostMapping("/registerS2")
	public ModelAndView registerFormStep2(@RequestParam(value="agree", required=false, defaultValue="false")boolean agree) {
		ModelAndView mav = new ModelAndView();
		
		if(!agree) {
			mav.addObject("chk", -1);
			mav.setViewName("user/regFormStep1");
			return mav;
		}
		mav.addObject("uVo", new UserVO());
		mav.setViewName("user/regFormStep2");
		return mav;
    }
	
	@GetMapping("/CheckId")
	public ModelAndView checkId(@RequestParam("user_id") String user_id) {
		ModelAndView mav = new ModelAndView();
		
		int result = stimUserService.checkById(user_id);
		
		mav.addObject("user_id", user_id);
		mav.addObject("result", result);
		mav.setViewName("user/checkId");
		
		return mav;
	}

	// 회원가입 step3 이동
	@PostMapping("/registerS3")
	public ModelAndView registerFormStep3(@Valid UserVO uVo, Errors errors) {
		ModelAndView mav = new ModelAndView();
		
		if (errors.hasErrors()) {
			mav.addObject("uVo", uVo);
			
			Map<String, String> validatorResult = stimUserService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
            	mav.addObject(key, validatorResult.get(key));
            }
			
			mav.setViewName("user/regFormStep2");
			return mav;
		}
		
		stimUserService.registerUser(uVo);
		mav.addObject("regUser", uVo);
		mav.setViewName("user/regFormStep3");
		
        return mav;
    }

}
