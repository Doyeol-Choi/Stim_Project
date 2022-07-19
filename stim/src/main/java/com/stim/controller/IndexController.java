package com.stim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	// 주소창에 입력 없을때 메인페이지로 유도
	@GetMapping("/")
	public String index() {
        return "index";
    }

}
