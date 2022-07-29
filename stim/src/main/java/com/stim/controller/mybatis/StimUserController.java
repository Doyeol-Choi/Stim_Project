package com.stim.controller.mybatis;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	// 아이디 중복체크
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
			mav.addObject("chkId", "check");
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
	
	// 아이디 찾기
	@PostMapping("/findId")
	public ModelAndView findIdByUserEmail(@RequestParam("user_email") String user_email) {
		ModelAndView mav = new ModelAndView();

		String user_id = stimUserService.findIdByEmail(user_email);
		mav.addObject("user_id", user_id);
		mav.setViewName("user/findIdResult");
		
		return mav;
	}
	
	// 비밀번호 찾기
	@PostMapping("/findPw")
	public ModelAndView findPwByUserId(@RequestParam("user_id") String user_id, @RequestParam("user_email") String user_email) {
		ModelAndView mav = new ModelAndView();

		int result = stimUserService.findPwByUserId(user_id, user_email);
		mav.addObject("result", result);
		mav.addObject("user_id", user_id);
		mav.setViewName("user/findPwResult");
		
		return mav;
	}
	
	// 비밀번호 찾기로 비밀번호 변경
	@PostMapping("/findPwChange")
	public ModelAndView findPwChange(@RequestParam("user_id") String user_id, @RequestParam("user_password") String user_password) {
		ModelAndView mav = new ModelAndView();
		
		stimUserService.changePwByUserId(user_id, user_password);
		mav.setViewName("user/loginForm");
		
		return mav;
	}
	
	// 프로필에서 비밀번호 변경
	@GetMapping("/pwdChangeForm")
	public ModelAndView pwdChangeForm(@RequestParam("user_id") String user_id, Authentication authentication) {
		ModelAndView mav = new ModelAndView();

		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_id().equals(user_id)) {
				mav.addObject("result", 1);
				mav.addObject("user_id", user_id);
				mav.setViewName("/user/findPwResult");
				return mav;
			}
		}
		
		mav.setViewName("/user/loginForm");
		
		return mav;
	}
	
	// 관리자가 할인목록 갱신
	@GetMapping("/changeDiscountList")
	public void changeDiscountList(Authentication authentication) {
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
			}
		}
	}


}
