package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimProfileService;
import com.stim.vo.UserVO;

@RestController
public class StimProfileController {

	@Resource
	private StimProfileService stimProfileService;

	public void setStimProfileService(StimProfileService stimProfileService) {
		this.stimProfileService = stimProfileService;
	}
	
	
	// 예시 => 수정해서 사용
//	@RequestMapping(value="user")
//	public ModelAndView SelectAllUserView(UserVO uVo) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		
//		List<UserVO> list = stimProfileService.SelectAllUser();
//		
//		mav.addObject("list", list);
//        mav.setViewName("user");
//        
//        return mav;
//	}

	/* 프로필 */
	@GetMapping("/profile/{user_id}")
	public ModelAndView profileList(@PathVariable("user_id") String user_id) {
		ModelAndView mav = new ModelAndView();
		
		try {
			UserVO uVo = stimProfileService.SelectById(user_id);
			mav.addObject("user", uVo);
			
			mav.setViewName("profile/profile");
			
			//DB 불러오기 테스트용
			System.out.println("프로필 몇개 출력 : " + uVo);
			System.out.println("뭐임 ? : " + uVo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
//	@RequestMapping(value="/profile")
//    public ModelAndView AllListView(Map<String, Object> map) throws Exception{
//        ModelAndView mav = new ModelAndView();
//        
//        List<Map<String, Object>> AllList = stimProfileService.SelectById();
//        
//        mav.addObject("Alllist", AllList);
//        mav.setViewName("profile/profile");
//        return mav;
//    }
	
	
	
}
