package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.vo.GameVO;

@RestController
public class StimGameListController {

	@Resource
	private StimGameListService stimGameListService;

		
	
	/* 상점 페이지 이동 */
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
	public ModelAndView popGameList() {
		ModelAndView mav = new ModelAndView();
		try {
			
			
			List<GameVO> popGList = stimGameListService.SelectPopularGameList();
			mav.addObject("gameList", popGList);

	        mav.setViewName("game/gameListPopular");
			System.out.println("인기 게임 리스트 수: " + popGList.size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	
	
	
	/////////////////////////////////////////////////////
	/* 최신 게임 페이지 이동 */
	@RequestMapping("/gameListNew")
	public ModelAndView newGameList() {
		ModelAndView mav = new ModelAndView();
		try {
			
			
			List<GameVO> newGList = stimGameListService.SelectNewestGameList();
			mav.addObject("gameList", newGList);

	        mav.setViewName("game/gameListNew");
			System.out.println("최신 게임 리스트 수: " + newGList.size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	
	
	
	/////////////////////////////////////////////////////
	/* 할인 게임 페이지 이동 */
	@GetMapping("/gameListSale")
	public String gameListSale() {
        return "game/gameListSale";
    }
	
	
	
	////////////////////////////////////////////////////	
	/* 검색 페이지 이동 */
	@GetMapping("/gameListSearch")
	public ModelAndView gameListSearch(@RequestParam(value="keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		try {
			
			List<GameVO> searchedByKey = stimGameListService.SelectGameListByKeyword(keyword);
			mav.addObject("gameList", searchedByKey);
			
	        mav.setViewName("game/gameListSearch");
			System.out.println("검색된 게임 리스트 수: " + searchedByKey.size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
        return mav;
    }
	
	
	
	////////////////////////////////////////////////////
	/* 태그 검색 페이지 이동 */
	@GetMapping("/gameListTagSearch")
	public ModelAndView gameTagSearch(@RequestParam(value="tagSearch") String tagSearch,
									  @RequestParam(value="genre[]",required=false,defaultValue="") List<String> genre,
									  @RequestParam(value="price",required=false,defaultValue="") int price) {
	ModelAndView mav = new ModelAndView();
	try {		
		List<GameVO> searchedByTag = stimGameListService.SelectGameListByTags(tagSearch, price);
		String test = "";
		
		
		for(int i=0; i<genre.size(); i++) {
			test += genre.get(i) + ", ";
		}
		
		test += tagSearch;
		
		System.out.println("태그: "+genre);	
		System.out.println("태그 리스트 사이즈: " + genre.size());
		System.out.println("가격: "+price);
		System.out.println("검색된 태그의 게임 리스트 수: " + searchedByTag.size() +"\n");
		
		
		mav.addObject("tag", test);
		mav.addObject("gameList", searchedByTag);
		mav.addObject("genre", genre);
		mav.addObject("price", price);
		mav.setViewName("game/gameListTagSearch");

	
	
	} catch (Exception e) {
	e.printStackTrace();
	}	
	
	return mav;
	}
	

	

}
