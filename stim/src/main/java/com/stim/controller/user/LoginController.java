package com.stim.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.user.StimUserService;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final StimUserService stimUserService;
	
	// 로그인 폼으로 접속
	@GetMapping("/loginForm")
	public String loginForm() {
        return "user/loginForm";
    }
	
	// 로그인 성공시 메인페이지로
	@PostMapping("/login")
	public String login() {
        return "/";
    }
	
	// 비밀번호 찾기 폼으로
	@GetMapping("/findPw")
	public String findPwForm() {
        return "user/findPwForm";
    }
	
	// 아이디 찾기 폼으로
	@GetMapping("/findId")
	public String findIdForm() {
        return "user/findIdForm";
    }
	
	// 회원가입 step1 이동
	@GetMapping("/registerS1")
	public String registerFormStep1() {
        return "user/regFormStep1";
    }
	
	// 회원가입 step2 이동
	@GetMapping("/registerS2")
	public String registerFormStep2() {
        return "user/regFormStep2";
    }

	// 회원가입 step3 이동
	@PostMapping("/registerS3")
	public ModelAndView registerFormStep3(UserVO uVo) {
		ModelAndView mav = new ModelAndView();
		
		stimUserService.registerUser(uVo);
		
		mav.addObject("user", uVo);
		mav.setViewName("user/regFormStep3");
		
        return mav;
    }
}
