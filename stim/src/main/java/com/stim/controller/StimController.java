package com.stim.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stim.service.mybatis.StimGameListService;
import com.stim.service.mybatis.StimProfileService;
import com.stim.service.user.StimUserService;
import com.stim.vo.Criteria;
import com.stim.vo.GameReplyVO;
import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StimController {
	
	@Resource
	private StimProfileService stimProfileService;
	
	@Resource
	private final StimUserService stimUserService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Resource
	private StimGameListService stimGameListService;
	
	// 로그인 폼으로 접속
	@GetMapping("/loginForm")
	public String loginForm(Authentication authentication) {
        if(authentication == null) {
        	return "user/loginForm";
        }
        return "redirect:/";
    }
	
	// 비밀번호 찾기 폼으로
	@GetMapping("/findPw")
	public String findPwForm(Authentication authentication) {
        if(authentication == null) {
        	return "user/findPwForm";
        }
        return "redirect:/";
    }
	
	// 아이디 찾기 폼으로
	@GetMapping("/findId")
	public String findIdForm(Authentication authentication) {
        if(authentication == null) {
        	return "user/findIdForm";
        }
        return "redirect:/";
    }
	
	// 회원가입 step1 이동
	@RequestMapping("/registerS1")
	public String registerFormStep1(Authentication authentication) {
        if(authentication == null) {
        	return "user/regFormStep1";
        }
        return "redirect:/";
    }
	
	// 회원가입 step2 Get으로 이동시 step1 이동
	@GetMapping("/registerS2")
	public String registerFormStep2Get() {
        return "user/regFormStep1";
    }
	
	// step3 Get방식으로 접근시 메인페이지로 이동
	@GetMapping("/registerS3")
	public String registerFormStep3Get() {
        return "redirect:/";
    }
	
	// 지원 페이지 이동
	@GetMapping("/support")
	public String supportPage() {
        return "support";
    }
	
	// 프로필 댓글 작성 ajax
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
	
	// 회원 탈퇴
	@PostMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(@RequestParam("user_code") int user_code,
							@RequestParam("user_password") String user_password,
							Authentication authentication) {
		String result = "1";
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if(user_code == uVo.getUser_code()) {
				if(encoder.matches(user_password, uVo.getUser_password())) {
					try {
						stimUserService.deleteUser(user_code);
						SecurityContextHolder.clearContext();
						result = "-1";
						return result;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}			    
			}
		}
		return result;
	}

	// 게임 상세페이지 평점댓글 입력
	@PostMapping("/gamereply")
	@ResponseBody
	public GameReplyVO InsertReply( 
							  @RequestParam("user_code") int user_code,
							  @RequestParam("game_code")  int game_code,
							  @RequestParam("grade_context") String grade_context,
							  @RequestParam("grade_rate") String grade_rate) throws Exception {
		System.out.println(user_code);
		System.out.println(game_code);
		System.out.println(grade_context);
		System.out.println(grade_rate);
		
		GameReplyVO rVo = new GameReplyVO();
		rVo.setUser_code(user_code);
		rVo.setGame_code(game_code);
		rVo.setGrade_context(grade_context);
		rVo.setGrade_rate(grade_rate);
		
		// 댓글 DB 저장
		stimGameListService.InsertReply(rVo);
		// 저장된 댓글의 모든 정보 불러오기
		rVo = stimGameListService.SelectLastReply(game_code);
		
		return rVo;

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
