package com.stim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {
	// 지원 페이지 이동
	@GetMapping("/support")
	public String supportPage() {
        return "support";
    }
}
