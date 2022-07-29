package com.stim.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.vo.GameVO;
import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

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
			System.out.println("게임 리스트 수: " + list.size()); // 콘솔 확인 용
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

	        mav.setViewName("game/gameListPopular"); // 인기 페이지 이동
			System.out.println("인기 게임 리스트 수: " + popGList.size()); // 콘솔 확인 용
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

	        mav.setViewName("game/gameListNew"); // 최신 게임 페이지 이동
			System.out.println("최신 게임 리스트 수: " + newGList.size()); // 콘솔 확인 용

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	
	
	
	/////////////////////////////////////////////////////
	/* 할인 게임 페이지 이동 */
	@GetMapping("/gameListSale")
	public ModelAndView gameListSale() {
		ModelAndView mav = new ModelAndView();
		try {
			List<GameVO> saleGList = stimGameListService.SelectAllGameListForSale();
			mav.addObject("gameList", saleGList);

	        mav.setViewName("game/gameListSale"); // 할인 페이지 이동
			System.out.println("할인 게임 리스트 수: " + saleGList.size()); // 콘솔 확인 용			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	
	
	
	////////////////////////////////////////////////////	
	/* 검색 페이지 이동 */ 
	@GetMapping("/gameListSearch")
	public ModelAndView gameListSearch(@RequestParam(value="keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		try {
			
			List<GameVO> searchedByKey = stimGameListService.SelectGameListByKeyword(keyword);
			mav.addObject("gameList", searchedByKey);
			
	        mav.setViewName("game/gameListSearch"); // 검색 페이지 이동
			System.out.println("검색된 게임 리스트 수: " + searchedByKey.size()); // 콘솔 확인용
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
				List<GameVO> searchedByTag;
				
				if (tagSearch.equals("") || tagSearch == null) {
					// tagSearch 검색어가 없는 경우, 전체 게임을 출력한다.
					searchedByTag = stimGameListService.SelectAllGameListByPrice(price);
				} else {
					searchedByTag = stimGameListService.SelectGameListByTags(tagSearch);
					
				}
				
				String test = ""; // 체크한 태그 받을 문자열
				for(int i=0; i<genre.size(); i++) { // checkbox에서 선택한 태그들의 수만큼 for문을 돌린다.
					test += genre.get(i) + ", "; // (태그), 형태로 문자열에 추가된다.
					for(int j=0; j<searchedByTag.size(); j++) { // tagSearch 검색어 조건에 맞는 게임들의 리스트 크기만큼 for문을 돌린다.
						if(!((searchedByTag.get(j).getGenre_1().equals(genre.get(i)) || 
								searchedByTag.get(j).getGenre_2().equals(genre.get(i)) || 
								searchedByTag.get(j).getGenre_3().equals(genre.get(i))) &&
								searchedByTag.get(j).getGame_price() <= price)) { // 기본 price 값은 1000000 (1000000원 이하의 게임들)
							// 선택한 태그 문자열 안에 태그 1,2,3과 같은 것이 확인하고, 없으면
							
							// 인덱스 넘버가 리스트 크기보다 커지면 마지막 것을 삭제하고 for문 탈출
							if(j>=searchedByTag.size()) {
								searchedByTag.remove(j);
								break;
							} else { // 리스트에서 해당 인덱스를 삭제한다.
								searchedByTag.remove(j);
								j--; // 다시 이전 번호로 돌아가 확인						
							}
						}
					}
				}
			
				
				/* tagSearch가 없을 때 문자열 마지막에 오는 콤마 삭제 */
				test += tagSearch;
				if(test.endsWith(", ")) {
					test = test.substring(0, test.length()-2);
				}
								

				// 콘솔 확인 용:: 콘솔에 선택한 태그와 가격, 검색된 리스트의 수를 출력한다.
				System.out.println("태그: "+test);	
				System.out.println("가격: "+price);
				System.out.println("검색된 태그의 게임 리스트 수: " + searchedByTag.size() +"\n");
				
				mav.addObject("tag", test);
				mav.addObject("gameList", searchedByTag);
				
				mav.setViewName("game/gameListTagSearch"); // 게임 태그 검색 페이지 이동		
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		return mav;
	}
	

	////////////////////////////////////////////////////
	/* 게임 상세 페이지 이동 */
	@GetMapping("/gameDetailView")
	public ModelAndView gameDetailViewPage(@RequestParam(value="game_code") int game_code) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			System.out.println("받은 게임 코드: " + game_code);
			GameVO gameDetailInfo = stimGameListService.SelectGameDetailInfo(game_code);
			mav.addObject("gameInfo", gameDetailInfo);

			mav.setViewName("game/gameDetailView");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	

}
