package com.stim.controller.mybatis;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.service.user.StimUserService;
import com.stim.vo.GameVO;
import com.stim.vo.SearchUserVO;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StimAdminController {
	
	@Resource
	private final StimUserService stimUserService;
	
	@Resource
	private StimGameListService stimGameListService;
	
	// 관리자 페이지로 이동
	@GetMapping("/adminPage")
	public ModelAndView adminPage(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				try {
					List<GameVO> list = stimGameListService.SelectAllGameList();
					mav.addObject("gameList", list);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mav.setViewName("/admin/admin");
				return mav; 
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// 할인 목록 갱신
	@PostMapping("/changeDiscountList")
	public void changeDiscountList(Authentication authentication) {
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				try {
					stimGameListService.discountListRemove();
					List <Integer> list = stimGameListService.randomGame(38);	// 추후 게임 목록 개수로 변경
					Random random = new Random();
					for (int code : list) {
						int discount = (random.nextInt(19)+1) * 5;
						stimGameListService.createDiscountList(discount, code);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	// 관리자 게임 검색
	@PostMapping("/gameListSearchAdmin")
	public ModelAndView gameListSearch(@RequestParam(value="keyword") String keyword, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				try {
					List<GameVO> searchedByKey = stimGameListService.SelectGameListByKeyword(keyword);
					mav.addObject("gameList", searchedByKey);
				    mav.setViewName("admin/admin");
				    
				    return mav;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
	
	// 유저 관리 페이지로 이동
	@GetMapping("/adminUser")
	public ModelAndView adminUser(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				try {
					List<UserVO> list = stimUserService.SelectUserAll();
					mav.addObject("userList", list);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mav.setViewName("/admin/adminUser");
				return mav; 
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// 유저 검색
	@PostMapping("/searchUserAdmin")
	public ModelAndView searchUser(@RequestParam("user_nickname") String user_nickname, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) { // 로그인
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				if(!user_nickname.isEmpty()) {	// 닉네임 검색시
					try {
						List<SearchUserVO> userList = stimUserService.SearchUserByNickname(user_nickname);
						mav.addObject("userList", userList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {	// 닉네임 입력 없이 검색
					try {
						List<UserVO> list = stimUserService.SelectUserAll();
						mav.addObject("userList", list);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
		}
		mav.setViewName("admin/adminUser");

		return mav;
	}
	
	// 게임 등록 폼으로 이동
	@GetMapping("/addGameForm")
	public ModelAndView addGameForm(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				mav.setViewName("/admin/adminGame");
				return mav; 
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}

	
	// 게임 등록
	@PostMapping("/addGame")
	public ModelAndView addGame(@RequestParam("picture") MultipartFile file, 
								@RequestParam("releaseDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
								GameVO gVo, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				String path = this.getClass().getResource("/").getPath().replaceAll("/target/classes/", "/src/main/resources/static/image/game/");
				String uuid = UUID.randomUUID().toString();
				String picName = uuid + "_" + file.getOriginalFilename();
				File savePic = new File(path + "/" + picName);
				gVo.setGame_picture(picName);
				try {
					file.transferTo(savePic);
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					gVo.setGame_releaseDate(releaseDate);
					stimGameListService.insertGame(gVo);
					stimGameListService.insertGenre(gVo);
					mav.setViewName("redirect:/adminPage");
					return mav; 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// 게임 수정 폼으로 이동
	@GetMapping("/updateGameForm")
	public ModelAndView updateGameForm(@RequestParam("game_code") int game_code, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				try {
					GameVO gVo = stimGameListService.SelectGameDetailInfo(game_code);
					mav.addObject("game", gVo);
					mav.setViewName("/admin/adminGameUpdate");
					return mav; 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// 게임 수정 => 관리자
	@PostMapping("/updateGame")
	public ModelAndView updateGame(@RequestPart(value="picture",required = false) MultipartFile file, 
									@RequestParam("originPic") String originPic,
									@RequestParam("releaseDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
									GameVO gVo, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		if(authentication != null) {
			UserVO uVo = (UserVO) authentication.getPrincipal();
			if (uVo.getUser_admin().equals("Y")) {
				if(!file.isEmpty()) {
					String path = this.getClass().getResource("/").getPath().replaceAll("/target/classes/", "/src/main/resources/static/image/game/");
					String uuid = UUID.randomUUID().toString();
					String picName = uuid + "_" + file.getOriginalFilename();
					File savePic = new File(path + "/" + picName);
					try {
						file.transferTo(savePic);
					} catch (IllegalStateException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					gVo.setGame_picture(picName);
					File deletePic = new File(path + "/" + originPic);
					deletePic.delete();
				} else {
					gVo.setGame_picture(originPic);
				}
				try {
					gVo.setGame_releaseDate(releaseDate);
					stimGameListService.updateGame(gVo);
					stimGameListService.updateGenre(gVo);
					mav.setViewName("redirect:/adminPage");
					return mav; 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mav.setViewName("redirect:/");
		return mav;
	}
	
}
