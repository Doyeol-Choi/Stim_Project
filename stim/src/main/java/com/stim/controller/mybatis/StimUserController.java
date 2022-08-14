package com.stim.controller.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		// 정규식으로 특수문자 포함 확인
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(user_id);
		if(user_id.isEmpty() || matcher.find()) {
			mav.addObject("user_id", user_id);
			mav.addObject("result", 2);
			mav.setViewName("user/checkId");
			
			return mav;
		}
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
	
	// 비밀번호 변경
	@PostMapping("/findPwChange")
	public ModelAndView findPwChange(@RequestParam("user_id") String user_id, @RequestParam("user_password") String user_password, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		
		try {
			stimUserService.changePwByUserId(user_id, user_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (authentication != null) {
			int code = ((UserVO) authentication.getPrincipal()).getUser_code();
			mav.setViewName("redirect:/profile/" + code);
		} else {			
			mav.setViewName("user/loginForm");
		}
		
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
					List<UserVO> uList = stimUserService.SelectUserAll();
					// 랜덤으로 유저 코드 뽑기
					List<Integer> randomList = stimUserService.randomCode(uList.size());	// 추후 총 유저 수로 변경
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
					List<UserVO> uList = stimUserService.SelectUserAll();
					// 랜덤으로 유저 코드 뽑기
					List<Integer> randomList = stimUserService.randomCode(uList.size());	// 추후 총 유저 수로 변경
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

	// 친구 요청
	@GetMapping("/addFriend")
	public RedirectView addFriend(@RequestParam("user_code") int user_code, Authentication authentication) {
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			int login_code = uVo.getUser_code();
			try {
				// 친구 요청 전 친구 요청 중이거나 이미 친구인지 DB 확인
				List<Integer> result = stimUserService.FriendRequestCheck(login_code, user_code);
				if (result.isEmpty()) {
					// 친구 요청 보내기
					stimUserService.AddFriendRequest(login_code, user_code);					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new RedirectView("/searchUser");
	}
	
	// 친구 요청 취소
	@GetMapping("/friendRequestCancle")
	public RedirectView friendRequestCancle(@RequestParam("user_code") int user_code, Authentication authentication) {
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			int login_code = uVo.getUser_code();
			try {
				// 친구 요청 취소 전 친구요청이 있는지 DB 확인
				String chk = stimUserService.FriendRequestAcceptCheck(login_code, user_code);
				if (chk.equals("N")) {
					// 친구 요청 DB삭제
					stimUserService.CancleFriendRequset(login_code, user_code);	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new RedirectView("/searchUser");
	}
	
	// 비밀번호 체크 페이지
	@GetMapping("/pwdCheck")
	public ModelAndView pwdCheck(@RequestParam("user_code") int user_code, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if(user_code == uVo.getUser_code()) {
				mav.addObject("user_code", user_code);
				mav.setViewName("user/checkPwd");
				return mav;
			}
		}
		mav.setViewName("redirect:/loginForm");
		return mav;
	}

}
