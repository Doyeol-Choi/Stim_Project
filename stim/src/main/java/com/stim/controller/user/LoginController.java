package com.stim.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@GetMapping("/loginForm")
	public String loginForm() {
        return "user/loginForm";
    }
	
	@PostMapping("/login")
	public String login() {
        return "/";
    }
	
	@GetMapping("/findPw")
	public String findPwForm() {
        return "user/findPwForm";
    }
	
	@GetMapping("/findId")
	public String findIdForm() {
        return "user/findIdForm";
    }
	
	@GetMapping("/registerS1")
	public String registerFormStep1() {
        return "user/regFormStep1";
    }

}
