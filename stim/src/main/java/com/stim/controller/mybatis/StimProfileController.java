package com.stim.controller.mybatis;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.stim.service.mybatis.StimProfileService;
import com.stim.service.user.StimUserService;
import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;
	@Resource
	private final StimUserService stimUserService;

	// 프로필
	@GetMapping("/profile/{user_code}")
	public ModelAndView profileList(@PathVariable("user_code") int user_code) {
		ModelAndView mav = new ModelAndView();
		
		try {
			UserVO uVo = stimProfileService.SelectById(user_code);
			
			List<ProFileVO> list = stimProfileService.getCommentInfo(user_code);
			
			mav.addObject("user", uVo);
			mav.addObject("list", list);
			
			mav.setViewName("profile/profile");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	//프로필 수정 폼으로 이동
	@GetMapping("/profile/edit/{user_code}")
	public ModelAndView profileUpdate(@PathVariable("user_code") int user_code) {
		ModelAndView mav = new ModelAndView();
		
		try {
			UserVO uVo = stimProfileService.SelectById(user_code);
			mav.addObject("user", uVo);
			
			mav.setViewName("profile/edit/profileUpdate");
			System.out.println("주소로 받아온 유저 : "+uVo.getUser_nickname());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	//프로필 수정 진행
	@PostMapping("/profile/edit/update")
	public ModelAndView profileNewUpdate(@Valid UserVO user, Errors errors, Authentication authentication) {
		ModelAndView mav = new ModelAndView();

		// 유효성 검사 
		if (errors.hasErrors()) {
			mav.addObject("user", user);
			System.out.println(errors);	// 콘솔에서 에러 확인
			Map<String, String> validatorResult = stimUserService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
            	mav.addObject(key, validatorResult.get(key));
            	System.out.println("여기오지?");
            }
			
            mav.setViewName("profile/edit/profileUpdate");
			return mav;
		}
		
		try {
			stimProfileService.UpdateUserInfo(user);
			
			mav.setViewName("redirect:/profile/" + user.getUser_code());

			System.out.println("특정 누군가 프로필 수정 : " + user.getUser_nickname());
		}catch(Exception e) {
			System.out.println("프로필 업데이트 안됨.");
			e.printStackTrace();
		}
		return mav;
	}
	
	// 프로필 사진 변경폼으로 이동
	@GetMapping("/changePicture")
	public ModelAndView ChangePicture(@RequestParam("user_code") int user_code, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		// 로그인 체크
		if (authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			// 로그인 유저와 프로필 유저가 같은지 체크
			if (uVo.getUser_code() == user_code) {						
				mav.addObject("user_code", user_code);
				mav.setViewName("profile/edit/pictureUpdateForm");
				
				return mav;
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// 프로필 사진 업데이트
	@PostMapping("/pictureUpdate")
	public RedirectView pictureUpdate(@RequestParam("user_code") int user_code, @RequestParam("picture") MultipartFile file, Authentication authentication) {
		// 로그인 체크
		if (authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			// 로그인 유저와 프로필 유저가 같은지 체크
			if (uVo.getUser_code() == user_code) {						
				
				String path = this.getClass().getResource("/").getPath().replaceAll("/target/classes/", "/src/main/resources/static/image/profile/");
				System.out.println(path);
//			    File dest = new File(filePath);
//			    files.transferTo(dest);
				
				
				String url = "redirect:/profile/" + user_code;
				
				return new RedirectView(url);
			}
		}
		String url = "redirect:/";
		return new RedirectView(url);
	}
	
	//댓글 저장 No Ajax
	@PostMapping("/comment")
	public RedirectView InsertComment(@RequestParam("user_code") int user_code,
									  @RequestParam("comment_text") String comment_text,
									  @RequestParam("writer_code") int writer_code,
									  @RequestParam("user_id") String user_id) throws Exception {
		System.out.println("인서트 테스트");
		ProFileVO pVo = new ProFileVO();
		pVo.setUser_code(user_code);
		pVo.setComment_context(comment_text);
		pVo.setWriter_code(writer_code);
		stimProfileService.InsertComment(pVo);
		
		String url = "/profile/" + user_code;
		System.out.println(url);
		
		return new RedirectView(url);
	}
	
	//댓글 저장 ajax
//	@PostMapping("/comment")
//	public void InsertComment(@RequestParam("user_code") int user_code,
//							  @RequestParam("writer_code") int writer_code,
//							  @RequestParam("comment_text") String comment_text) throws Exception{
//		
//		//테스트 용
//		System.out.println(user_code);
//		System.out.println(writer_code);
//		System.out.println(comment_text);
//		//
//		
//		ProFileVO pVo = new ProFileVO();
//		pVo.setUser_code(user_code);
//		pVo.setComment_context(comment_text);
//		pVo.setWriter_code(writer_code);
//		
//		stimProfileService.InsertComment(pVo);
//	}
}
