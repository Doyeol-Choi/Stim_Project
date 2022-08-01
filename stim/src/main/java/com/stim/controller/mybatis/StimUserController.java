package com.stim.controller.mybatis;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.servlet.view.RedirectView;

import com.stim.service.user.StimUserService;
import com.stim.vo.SearchUserVO;
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
			try {
				Map<String, String> validatorResult;
				validatorResult = stimUserService.validateHandling(errors);
				for (String key : validatorResult.keySet()) {
					mav.addObject(key, validatorResult.get(key));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("user/regFormStep2");
			return mav;
		}
	
		try {
			stimUserService.registerUser(uVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("regUser", uVo);
		mav.setViewName("user/regFormStep3");
		
        return mav;
    }
	
	// 아이디 찾기
	@PostMapping("/findId")
	public ModelAndView findIdByUserEmail(@RequestParam("user_email") String user_email) {
		ModelAndView mav = new ModelAndView();

		String user_id;
		try {
			user_id = stimUserService.findIdByEmail(user_email);
			mav.addObject("user_id", user_id);
			mav.setViewName("user/findIdResult");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	// 비밀번호 찾기
	@PostMapping("/findPw")
	public ModelAndView findPwByUserId(@RequestParam("user_id") String user_id, @RequestParam("user_email") String user_email) {
		ModelAndView mav = new ModelAndView();

		int result;
		try {
			result = stimUserService.findPwByUserId(user_id, user_email);
			mav.addObject("result", result);
			mav.addObject("user_id", user_id);
			mav.setViewName("user/findPwResult");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	// 비밀번호 찾기로 비밀번호 변경
	@PostMapping("/findPwChange")
	public ModelAndView findPwChange(@RequestParam("user_id") String user_id, @RequestParam("user_password") String user_password) {
		ModelAndView mav = new ModelAndView();
		
		try {
			stimUserService.changePwByUserId(user_id, user_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	// 유저 찾기 폼페이지 이동
	@GetMapping("/searchUser")
	public ModelAndView searchUserForm() {
		ModelAndView mav = new ModelAndView();
		
		List<UserVO> list = null;
		
		mav.addObject("userList", list);
		mav.setViewName("profile/searchUserForm");
		return mav;
	}
	
	// 유저 검색
	@PostMapping("/searchUser")
	public ModelAndView searchUser(@RequestParam("user_nickname") String user_nickname, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			int login_code = uVo.getUser_code();
			if(!user_nickname.isEmpty()) {	// 닉네임 검색시
				try {
					List<SearchUserVO> userList = stimUserService.SearchUserByNicknameLogin(user_nickname, login_code);
					mav.addObject("userList", userList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {	// 닉네임 입력 없이 검색
				try {
					List<Integer> randomList = stimUserService.randomCode(8, login_code);	// 추후 총 유저 수로 변경
					List<SearchUserVO> userList = new ArrayList<>();
					for(int code : randomList) {
						SearchUserVO randomUser = stimUserService.SearchUserByCodeLogin(code, login_code);
						userList.add(randomUser);
						mav.addObject("userList", userList);
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {	// 비로그인
			if(!user_nickname.isEmpty()) {	// 닉네임 검색시
				try {
					List<SearchUserVO> userList = stimUserService.SearchUserByNickname(user_nickname);
					mav.addObject("userList", userList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {	// 닉네임 입력 없이 검색
				try {
					List<Integer> randomList = stimUserService.randomCode(8);	// 추후 총 유저 수로 변경
					List<SearchUserVO> userList = new ArrayList<>();
					for(int code : randomList) {						
						SearchUserVO randomUser = stimUserService.SearchUserByCode(code);
						userList.add(randomUser);
						mav.addObject("userList", userList);
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mav.setViewName("profile/searchUserForm");

		return mav;
	}

	// 친구 추가
	@GetMapping("/addFriend")
	public RedirectView addFriend(@RequestParam("user_code") int user_code, Authentication authentication) {
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			int login_code = uVo.getUser_code();
			try {
				List<Integer> result = stimUserService.FriendRequestCheck(login_code, user_code);
				if (result.isEmpty()) {
					stimUserService.AddFriendRequest(login_code, user_code);					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new RedirectView("/searchUser");
	}
	
	
}
