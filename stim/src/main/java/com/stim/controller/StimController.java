package com.stim.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.stim.vo.UserVO;


@Controller
public class StimController {

	// 주소창에 입력 없을때 메인페이지로 유도
	@GetMapping("/")
	public String index() {
        return "index";
    }
	
	@GetMapping("/adminPage")
	public String adminPage(Authentication authentication) {
		if(authentication.getPrincipal() != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				return "/admin/admin"; 
			}
		}
		return "index";
	}

}
