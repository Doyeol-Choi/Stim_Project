package com.stim.controller.mybatis;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
	public ModelAndView profileList(@PathVariable("user_code") int user_code){
		ModelAndView mav = new ModelAndView();
		
		try {
			UserVO uVo = stimProfileService.SelectById(user_code);
			
			//Page<ProFileVO> paging = this.stimProfileService.getList(page);
			List<ProFileVO> list = stimProfileService.getCommentInfo(user_code);
			List<ProFileVO> game_list = stimProfileService.SelectMyGames(user_code);
 			List<ProFileVO> f_list = stimProfileService.SelectMyFriends(user_code);
			List<ProFileVO> request = stimProfileService.selectFriendRequest(user_code);	
			String context = stimProfileService.SelectProfileContext(user_code);
 			
			
			mav.addObject("game_list",game_list);
			mav.addObject("user", uVo);
			mav.addObject("list", list);
			mav.addObject("f_list",f_list);
			mav.addObject("request",request);
			mav.addObject("context",context);
			
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
			Map<String, String> validatorResult;
			try {
				validatorResult = stimUserService.validateHandling(errors);
				for (String key : validatorResult.keySet()) {
					mav.addObject(key, validatorResult.get(key));
					System.out.println("여기오지?");
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		if (authentication != null & !file.isEmpty()) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			String existingPic = uVo.getUser_picture();
			System.out.println(existingPic);
			// 로그인 유저와 프로필 유저가 같은지 체크
			if (uVo.getUser_code() == user_code) {
				String path = this.getClass().getResource("/").getPath().replaceAll("/target/classes/", "/src/main/resources/static/image/profile/");
				String uuid = UUID.randomUUID().toString();
				String picName = uuid + "_" + file.getOriginalFilename();
			    File savePic = new File(path + "/" + picName);
			    ProFileVO pVo = new ProFileVO();
			    pVo.setUser_code(user_code);
			    pVo.setUser_picture(picName);
				try {
					file.transferTo(savePic);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				try {
					stimProfileService.UpdatePicture(pVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				uVo.setUser_picture(picName);
				if(!existingPic.equals("noimage.jpg")) {
					File deletePic = new File(path + "/" + existingPic);
					deletePic.delete();
					System.out.println("삭제");
				}
			}
		}
		String url = "redirect:/";
		return new RedirectView(url);
	}
	
	//댓글 삭제
	@PostMapping("/comment/delete")
	public void DeleteComment(@RequestParam("comment_code") int comment_code) throws Exception{
		stimProfileService.DeleteCommentByCode(comment_code);
	}
	
	//친추 요청 삭제
	@PostMapping("/friend/request")
	@Transactional
	public void deleteRequest(@RequestParam("friend_code") int friend_code)	throws Exception{
		
		System.out.println("친추 삭제 테스트");
		
		stimProfileService.deleteFriendRequest(friend_code);
	}
	
	//친추 요청 승인
	@Transactional
	@GetMapping("/friend/approval")
	public RedirectView approvalRequest(@RequestParam("friend_code") int friend_code,
										@RequestParam("user_code") int user_code) throws Exception{
		System.out.println("친추 승인 테스트");
		
		stimProfileService.updateFriendRequest(friend_code);
		String url = "/profile/" + user_code;
		
		return new RedirectView(url);
	}
	
	//친구 삭제
	@PostMapping("/friend/delete")
	@Transactional
	public void deleteMyFriend(@RequestParam("friend_code") int friend_code ) throws Exception {
	
		stimProfileService.deleteMyFriend(friend_code);
	}
	
	
}
