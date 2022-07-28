package com.stim.controller.mybatis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.stim.service.mybatis.StimProfileService;
import com.stim.service.user.StimUserService;
import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

@RestController
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;
	@Resource private StimUserService stimUserService;

	/* 프로필 */
	@GetMapping("/profile/{user_code}")
	public ModelAndView profileList(@PathVariable("user_code") int user_code) {
		ModelAndView mav = new ModelAndView();
		
		try {
			UserVO uVo = stimProfileService.SelectById(user_code);
			
			List<ProFileVO> list = stimProfileService.getCommentInfo(user_code);
			List<ProFileVO> game_list = stimProfileService.SelectMyGames(user_code);
 			
			
			mav.addObject("game_list",game_list);
			mav.addObject("user", uVo);
			mav.addObject("list", list);
			
			mav.setViewName("profile/profile");
			
			//DB 불러오기 테스트용
			//System.out.println("프로필 가져오기: " + uVo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	//프로필 수정
	@GetMapping("/profile/edit/{user_code}")
	public ModelAndView profileUpdate(@PathVariable("user_code") int user_code) {
		ModelAndView mav = new ModelAndView();
		
		try {
			//SelectByIdForUpdate
			UserVO uVo = stimProfileService.SelectById(user_code);
			mav.addObject("user", uVo);
			
			mav.setViewName("profile/edit/profileUpdate");
			System.out.println("주소로 받아온 유저 : "+uVo.getUser_nickname());
			//DB 불러오기 테스트용
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	//프로필 수정
	@PostMapping("/profile/edit/update")
	public ModelAndView profileNewUpdate(@Valid UserVO uVo, Errors errors) {
		ModelAndView mav = new ModelAndView();
		System.out.println("테스트 1");
		
		// 유효성 검사 
		if (errors.hasErrors()) {
			mav.addObject("user", uVo);
			
			Map<String, String> validatorResult = stimUserService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
            	mav.addObject(key, validatorResult.get(key));
            }
			
            System.out.println("테스트2");
			mav.setViewName("profile/edit/profileUpdate");
			return mav;
		}
		
		try {
			//SelectByIdForUpdate
			stimProfileService.SelectByIdForUpdate(uVo);	
			mav.addObject("user", uVo);
			mav.setViewName("profile/edit/profileUpdate");
			//DB 불러오기 테스트용
			System.out.println("특정 누군가 프로필 수정 : " + uVo.getUser_nickname());
		}catch(Exception e) {
			System.out.println("프로필 업데이트 안됨.");
			e.printStackTrace();
		}
		return mav;
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
