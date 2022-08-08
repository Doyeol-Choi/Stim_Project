package com.stim.controller.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stim.service.mybatis.StimGameListService;
import com.stim.service.mybatis.StimWishCartService;
import com.stim.vo.GameReplyVO;
import com.stim.vo.GameVO;
import com.stim.vo.Pagination;
import com.stim.vo.UserVO;


@RestController
public class StimGameListController {

	@Resource
	private StimGameListService stimGameListService;
	
	@Resource
	private StimWishCartService stimWishCartService;
	
	/* 상점 페이지 이동 */
	@GetMapping("/gameList")
	public ModelAndView gameList() {	
		ModelAndView mav = new ModelAndView();
		try {
			
			
			List<GameVO> list = stimGameListService.SelectAllGameList();
			mav.addObject("gameList", list);

	        mav.setViewName("game/gameList");
			System.out.println("게임 리스트 수: " + list.size() +"\n"); // 콘솔 확인 용
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
	/* 인기 게임 태그 검색 */
	@GetMapping("/gameListPopularTagSearch")
	public ModelAndView popGameListSearchedByTags(@RequestParam(value="tagSearch") String tagSearch,
					@RequestParam(value="genre[]",required=false,defaultValue="") List<String> genre,
					@RequestParam(value="price",required=false,defaultValue="") int price) {
		ModelAndView mav = new ModelAndView();
		try {
			
			List<GameVO> searchedByTag;
			
			if (tagSearch.equals("") || tagSearch == null) {
				// tagSearch 검색어가 없는 경우, 전체 게임을 출력한다.
				searchedByTag = stimGameListService.SelectGameListPopByPrice(price);
			} else { // 검색 키워드를 직접 입력하는 경우
				searchedByTag = stimGameListService.SelectGameListPopByTags(tagSearch);
				if(price != 1000000) { // 가격도 선택할 경우
					searchedByTag = stimGameListService.SelectGameListPopByTagsAndPrice(tagSearch, price);
				}
			}
			
			String test = ""; // 체크한 태그 받을 문자열
			for(int i=0; i<genre.size(); i++) { // checkbox에서 선택한 태그들의 수만큼 for문을 돌린다.
				test += genre.get(i) + ", "; // (태그), 형태로 문자열에 추가된다.
				for(int j=0; j<searchedByTag.size(); j++) { // tagSearch 검색어 조건에 맞는 게임들의 리스트 크기만큼 for문을 돌린다.
					if(!((searchedByTag.get(j).getGenre_1().equals(genre.get(i)) || 
							searchedByTag.get(j).getGenre_2().equals(genre.get(i)) || 
							searchedByTag.get(j).getGenre_3().equals(genre.get(i))) &&
							searchedByTag.get(j).getGame_finalPrice() <= price)) { // 기본 price 값은 1000000 (1000000원 이하의 게임들)
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
			System.out.println("\n태그: "+test);	
			System.out.println("가격: "+price);
			System.out.println("검색된 태그의 게임 리스트 수: " + searchedByTag.size() +"\n");
			
			mav.addObject("tag", test);
			mav.addObject("gameList", searchedByTag);
			
			mav.setViewName("game/gameListPopular"); // 게임 태그 검색 페이지 이동		
			
		}catch(Exception e) {
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
	/* 최신 게임 태그 검색 */
	@GetMapping("/gameListNewTagSearch")
	public ModelAndView newGameListSearchedByTags(@RequestParam(value="tagSearch") String tagSearch,
					@RequestParam(value="genre[]",required=false,defaultValue="") List<String> genre,
					@RequestParam(value="price",required=false,defaultValue="") int price) {
		ModelAndView mav = new ModelAndView();
		try {
			
			List<GameVO> searchedByTag;
			
			if (tagSearch.equals("") || tagSearch == null) {
				// tagSearch 검색어가 없는 경우, 전체 게임을 출력한다.
				searchedByTag = stimGameListService.SelectGameListNewByPrice(price);
			} else { // 검색 키워드를 직접 입력하는 경우
				searchedByTag = stimGameListService.SelectGameListNewByTags(tagSearch);
				if(price != 1000000) { // 가격도 선택할 경우
					searchedByTag = stimGameListService.SelectGameListNewByTagsAndPrice(tagSearch, price);
				}
			}
			
			String test = ""; // 체크한 태그 받을 문자열
			for(int i=0; i<genre.size(); i++) { // checkbox에서 선택한 태그들의 수만큼 for문을 돌린다.
				test += genre.get(i) + ", "; // (태그), 형태로 문자열에 추가된다.
				for(int j=0; j<searchedByTag.size(); j++) { // tagSearch 검색어 조건에 맞는 게임들의 리스트 크기만큼 for문을 돌린다.
					if(!((searchedByTag.get(j).getGenre_1().equals(genre.get(i)) || 
							searchedByTag.get(j).getGenre_2().equals(genre.get(i)) || 
							searchedByTag.get(j).getGenre_3().equals(genre.get(i))) &&
							searchedByTag.get(j).getGame_finalPrice() <= price)) { // 기본 price 값은 1000000 (1000000원 이하의 게임들)
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
			System.out.println("\n태그: "+test);	
			System.out.println("가격: "+price);
			System.out.println("검색된 태그의 게임 리스트 수: " + searchedByTag.size() +"\n");
			
			mav.addObject("tag", test);
			mav.addObject("gameList", searchedByTag);
			
			mav.setViewName("game/gameListNew"); // 게임 태그 검색 페이지 이동		
			
		}catch(Exception e) {
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
			List<GameVO> saleGList = stimGameListService.SelectNumForSale();
			mav.addObject("gameList", saleGList);

	        mav.setViewName("game/gameListSale"); // 할인 페이지 이동
			System.out.println("할인 게임 리스트 수: " + saleGList.size()); // 콘솔 확인 용			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;	
    }
	/* 할인 게임 태그 검색 */
	@GetMapping("/gameListSaleTagSearch")
	public ModelAndView gameListSaleSearchedByTags(@RequestParam(value="tagSearch") String tagSearch,
					@RequestParam(value="genre[]",required=false,defaultValue="") List<String> genre,
					@RequestParam(value="price",required=false,defaultValue="") int price) {
		ModelAndView mav = new ModelAndView();
		try {
			
			List<GameVO> searchedByTag;
			
			if (tagSearch.equals("") || tagSearch == null) {
				// tagSearch 검색어가 없는 경우, 전체 게임을 출력한다.
				searchedByTag = stimGameListService.SelectGameListSaleByPrice(price);
			} else { // 검색 키워드를 직접 입력하는 경우
				searchedByTag = stimGameListService.SelectGameListSaleByTags(tagSearch);
				if(price != 1000000) { // 가격도 선택할 경우
					searchedByTag = stimGameListService.SelectGameListSaleByTagsAndPrice(tagSearch, price);
				}
			}
			
			String test = ""; // 체크한 태그 받을 문자열
			for(int i=0; i<genre.size(); i++) { // checkbox에서 선택한 태그들의 수만큼 for문을 돌린다.
				test += genre.get(i) + ", "; // (태그), 형태로 문자열에 추가된다.
				for(int j=0; j<searchedByTag.size(); j++) { // tagSearch 검색어 조건에 맞는 게임들의 리스트 크기만큼 for문을 돌린다.
					if(!((searchedByTag.get(j).getGenre_1().equals(genre.get(i)) || 
							searchedByTag.get(j).getGenre_2().equals(genre.get(i)) || 
							searchedByTag.get(j).getGenre_3().equals(genre.get(i))) &&
							searchedByTag.get(j).getGame_finalPrice() <= price)) { // 기본 price 값은 1000000 (1000000원 이하의 게임들)
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
			System.out.println("\n태그: "+test);	
			System.out.println("가격: "+price);
			System.out.println("검색된 태그의 게임 리스트 수: " + searchedByTag.size() +"\n");
			
			mav.addObject("tag", test);
			mav.addObject("gameList", searchedByTag);
			
			mav.setViewName("game/gameListSale"); // 게임 태그 검색 페이지 이동		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	
	
	////////////////////////////////////////////////////	
	/* [전체] 검색 페이지 이동 */ 
	@GetMapping("/gameListSearch")
	public ModelAndView gameListSearch(@RequestParam(value="keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		try {
			
			List<GameVO> searchedByKey = stimGameListService.SelectGameListByKeyword(keyword);
			mav.addObject("gameList", searchedByKey);
			
	        mav.setViewName("game/gameListSearch"); // 검색 페이지 이동
			System.out.println("검색된 게임 리스트 수: " + searchedByKey.size() +"\n"); // 콘솔 확인용
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
        return mav;
    }
	
	
	
	////////////////////////////////////////////////////
	/* [전체] 태그 검색 페이지 이동 */
	@GetMapping("/gameListTagSearch")
	public ModelAndView gameTagSearch(@RequestParam(value="tagSearch",required=false,defaultValue="") String tagSearch,
									  @RequestParam(value="genre[]",required=false,defaultValue="") List<String> genre,
									  @RequestParam(value="price",required=false,defaultValue="") int price) {
		ModelAndView mav = new ModelAndView();
		try {
				List<GameVO> searchedByTag;
				
				if (tagSearch.equals("") || tagSearch == null) {
					// tagSearch 검색어가 없는 경우, 전체 게임을 출력한다.
					searchedByTag = stimGameListService.SelectAllGameListByPrice(price);
				} else { // 검색 키워드를 직접 입력하는 경우
					searchedByTag = stimGameListService.SelectGameListByTags(tagSearch);
					if(price != 1000000) { // 가격도 선택할 경우
						searchedByTag = stimGameListService.SelectGameListByTagAndPrice(tagSearch, price);
					}
				}
				
				String test = ""; // 체크한 태그 받을 문자열
				for(int i=0; i<genre.size(); i++) { // checkbox에서 선택한 태그들의 수만큼 for문을 돌린다.
					test += genre.get(i) + ", "; // (태그), 형태로 문자열에 추가된다.
					for(int j=0; j<searchedByTag.size(); j++) { // tagSearch 검색어 조건에 맞는 게임들의 리스트 크기만큼 for문을 돌린다.
						if(!((searchedByTag.get(j).getGenre_1().equals(genre.get(i)) || 
								searchedByTag.get(j).getGenre_2().equals(genre.get(i)) || 
								searchedByTag.get(j).getGenre_3().equals(genre.get(i))) &&
								searchedByTag.get(j).getGame_finalPrice() <= price)) { // 기본 price 값은 1000000 (1000000원 이하의 게임들)
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
				System.out.println("\n태그: "+test);	
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
//	@GetMapping("/gameDetailView")
//	public ModelAndView gameDetailViewPage(@RequestParam(value="game_code") int game_code, Authentication authentication) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		try {
//			System.out.println("받은 게임 코드: " + game_code);
//			GameVO gameDetailInfo = stimGameListService.SelectGameDetailInfo(game_code);
//			List<GameReplyVO> reply = stimGameListService.SelectALLReply(game_code);
//			List<GameReplyVO> gameGrade = stimGameListService.SelectGradeRatebyGameCode(game_code);
//	
//			/* 평점 댓글 Good/Bad 계산하기 */
//			int goodGradeCount = 0;
//			int badGradeCount = 0;
//			for(int i=0; i<=gameGrade.size()-1; i++) {
//				if(gameGrade.get(i).getGrade_rate().equals("g")) {
//					goodGradeCount++;
//				}else {	badGradeCount++;}
//			}
//			String Grade = ""; // 반영하기 위한 최소 댓글 개수: 5
//			if(gameGrade.size()>=5 && goodGradeCount >= badGradeCount) {
//				Grade = "G";
//			}else if(gameGrade.size()>=5 && goodGradeCount < badGradeCount){
//				Grade = "B";
//			}else {
//				Grade = "N";
//			}
//			mav.addObject("Grade", Grade);
//			
//			mav.addObject("reply", reply);
//	
//			// 로그인시 구매, 찜목록, 장바구니 체크
//			if(authentication != null) {
//				UserVO uVo = (UserVO) authentication.getPrincipal();
//				Integer user_code = uVo.getUser_code();
//		
//				List<Integer> game_code_cart = stimWishCartService.SelectCartGameCode(user_code);
//				List<Integer> game_code_wish = stimWishCartService.SelectWishGameCode(user_code);
//				List<Integer> game_code_my = stimWishCartService.SelectMyGameCode(user_code);
//		
//				if(game_code_cart.isEmpty()) {
//				game_code_cart= new ArrayList<>();
//				}
//				if(game_code_wish.isEmpty()) {
//				game_code_wish=new ArrayList<>();
//				}
//				if(game_code_my.isEmpty()) {
//				game_code_my=new ArrayList<>();
//				}
//		
//				mav.addObject("game_code_cart", game_code_cart);
//				mav.addObject("game_code_wish",game_code_wish);
//				mav.addObject("game_code_my", game_code_my);
//				mav.addObject("gameInfo", gameDetailInfo);
//				mav.setViewName("game/gameDetailView");
//				return mav;
//			}
//	
//			mav.addObject("gameInfo", gameDetailInfo);
//			mav.setViewName("game/gameDetailView");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
	
	@GetMapping("/gameDetailView")
	public ModelAndView gameDetailViewPage(@RequestParam(value="game_code") int game_code,
											@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
								            @RequestParam(value = "cntPerPage", required = false, defaultValue = "5") int cntPerPage,
								            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
								            Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			System.out.println("받은 게임 코드: " + game_code);
			GameVO gameDetailInfo = stimGameListService.SelectGameDetailInfo(game_code);
			
			int replyCnt = stimGameListService.CountAllReply(game_code);
			System.out.println("댓글 개수 : " + replyCnt);
			Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
			pagination.setTotalRecordCount(replyCnt);
			System.out.println(pagination.getFirstRecordIndex());
			List<GameReplyVO> reply =  stimGameListService.SelectALLReply(game_code, pagination.getFirstRecordIndex(), pagination.getLastRecordIndex());
			mav.addObject("pagination", pagination);
			mav.addObject("reply", reply);
	        
			List<GameReplyVO> gameGrade = stimGameListService.SelectGradeRatebyGameCode(game_code);
	
			/* 평점 댓글 Good/Bad 계산하기 */
			int goodGradeCount = 0;
			int badGradeCount = 0;
			for(int i=0; i<=gameGrade.size()-1; i++) {
				if(gameGrade.get(i).getGrade_rate().equals("g")) {
					goodGradeCount++;
				}else {	badGradeCount++;}
			}
			String Grade = ""; // 반영하기 위한 최소 댓글 개수: 5
			if(gameGrade.size()>=5 && goodGradeCount >= badGradeCount) {
				Grade = "G";
			}else if(gameGrade.size()>=5 && goodGradeCount < badGradeCount){
				Grade = "B";
			}else {
				Grade = "N";
			}
			mav.addObject("Grade", Grade);
	
			// 로그인시 구매, 찜목록, 장바구니 체크
			if(authentication != null) {
				UserVO uVo = (UserVO) authentication.getPrincipal();
				Integer user_code = uVo.getUser_code();
		
				List<Integer> game_code_cart = stimWishCartService.SelectCartGameCode(user_code);
				List<Integer> game_code_wish = stimWishCartService.SelectWishGameCode(user_code);
				List<Integer> game_code_my = stimWishCartService.SelectMyGameCode(user_code);
		
				if(game_code_cart.isEmpty()) {
				game_code_cart= new ArrayList<>();
				}
				if(game_code_wish.isEmpty()) {
				game_code_wish=new ArrayList<>();
				}
				if(game_code_my.isEmpty()) {
				game_code_my=new ArrayList<>();
				}
		
				mav.addObject("game_code_cart", game_code_cart);
				mav.addObject("game_code_wish",game_code_wish);
				mav.addObject("game_code_my", game_code_my);
				mav.addObject("gameInfo", gameDetailInfo);
				mav.setViewName("game/gameDetailView");
				return mav;
			}
	
			mav.addObject("gameInfo", gameDetailInfo);
			mav.setViewName("game/gameDetailView");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	// 게임상세페이지 댓글 삭제
	@PostMapping("/reply/delete")
	public void DeleteReply(@RequestParam("grade_code") int grade_code) throws Exception{
		stimGameListService.DeleteReplyByCode(grade_code);
	}
	
	
}
