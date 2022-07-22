package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.vo.GameVO;

@RestController
public class StimGameListController {

	@Resource
	private StimGameListService stimGameListService;

		
	/* 상점 */
	@GetMapping("/gameList")
	public ModelAndView gameList() {
		ModelAndView mav = new ModelAndView();
		try {
			
			
			List<GameVO> list = stimGameListService.SelectAllGameList();
			mav.addObject("gameList", list);

	        mav.setViewName("game/gameList");
			System.out.println("게임 리스트 수: " + list.size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	
	
	///////////////////////////////////////////////////////
	/* 인기 게임 페이지 이동 */
	@GetMapping("/gameListPopular")
	public String gameListPopular() {
        return "game/gameListPopular";
    }
	
	
	///////////////////////////////////////////////////////
	/* 최신 게임 페이지 이동 */
	@GetMapping("/gameListNew")
	public ModelAndView newGameList() {
		ModelAndView mav = new ModelAndView();
		try {
			
			
			List<GameVO> newGlist = stimGameListService.SelectNewestGameList();
			mav.addObject("gameList", newGlist);

	        mav.setViewName("game/gameListNew");
			System.out.println("최신 게임 리스트 수: " + newGlist.size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	
	
	/* 할인 게임 페이지 이동 */
	@GetMapping("/gameListSale")
	public String gameListSale() {
        return "game/gameListSale";
    }
	
	/* 검색 페이지 이동 */
	@GetMapping("/gameListSearch")
	public String gameListSearch() {
        return "game/gameListSearch";
    }
	
	
}
