package com.stim.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/loginForm")
	public String loginForm() {
        return "user/loginForm";
    }
}
