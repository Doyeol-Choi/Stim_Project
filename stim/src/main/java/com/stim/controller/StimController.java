package com.stim.controller;


import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stim.service.mybatis.StimProfileService;
import com.stim.vo.ProFileVO;

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
	
	// 지원 페이지 이동
	@GetMapping("/support")
	public String supportPage() {
        return "support";
    }
	
	@PostMapping("/comment")
	@ResponseBody
	public ProFileVO InsertComment( 
							  @RequestParam("user_code") int user_code,
							  @RequestParam("comment_text") String comment_text,
							  @RequestParam("writer_code") int writer_code,
							  @RequestParam("user_id") String user_id) throws Exception {
		ProFileVO pVo = new ProFileVO();
		pVo.setUser_code(user_code);
		pVo.setComment_context(comment_text);
		pVo.setWriter_code(writer_code);
		// 댓글 DB 저장
		stimProfileService.InsertComment(pVo);
		// 저장된 댓글의 모든 정보 불러오기
		pVo = stimProfileService.selectLastComment(user_code);
		return pVo;
		
	}

	//프로필 메시지 등록	
	@PostMapping("/updateContext")
	@Transactional
	@ResponseBody
	public void updateContext(@RequestParam("user_code") int user_code,
								@RequestParam("profile_context") String profile_context) throws Exception {
		
		String context = stimProfileService.SelectProfileContext(user_code);
		
		if(context == null ) {
			stimProfileService.insertProfileContext(user_code, profile_context);
		}
		else {
			stimProfileService.updateProfileContext(user_code, profile_context);
		}

	}
	
}
