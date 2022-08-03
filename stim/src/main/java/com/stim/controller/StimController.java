package com.stim.controller;


import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stim.service.mybatis.StimProfileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StimController {
	
	@Resource
	private StimProfileService stimProfileService;
	
	// 로그인 폼으로 접속
	@GetMapping("/loginForm")
	public String loginForm(Authentication authentication) {
        if(authentication == null) {
        	return "user/loginForm";
        }
        return "index";
    }
	
	// 비밀번호 찾기 폼으로
	@GetMapping("/findPw")
	public String findPwForm(Authentication authentication) {
        if(authentication == null) {
        	return "user/findPwForm";
        }
        return "index";
    }
	
	// 아이디 찾기 폼으로
	@GetMapping("/findId")
	public String findIdForm(Authentication authentication) {
        if(authentication == null) {
        	return "user/findIdForm";
        }
        return "index";
    }
	
	// 회원가입 step1 이동
	@RequestMapping("/registerS1")
	public String registerFormStep1(Authentication authentication) {
        if(authentication == null) {
        	return "user/regFormStep1";
        }
        return "index";
    }
	
	// 회원가입 step2 Get으로 이동시 step1 이동
	@GetMapping("/registerS2")
	public String registerFormStep2Get() {
        return "user/regFormStep1";
    }
	
	// step3 Get방식으로 접근시 메인페이지로 이동
	@GetMapping("/registerS3")
	public String registerFormStep3Get() {
        return "index";
    }	

}
