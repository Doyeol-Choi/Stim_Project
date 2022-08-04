package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.GameReplyVO;
import com.stim.vo.GameVO;
import com.stim.vo.ProFileVO;

public interface StimGameListService {

	// 게임 리스트 조회
	public List<GameVO> SelectAllGameList() throws Exception;
	
	// 최신 게임 리스트 조회
	public List<GameVO> SelectNewestGameList() throws Exception;
	
	// 인기 게임 리스트 조회
	public List<GameVO> SelectPopularGameList() throws Exception;
	
	// 키워드로 검색된 게임 리스트 조회
	public List<GameVO> SelectGameListByKeyword(String keyword) throws Exception;

	// 태그로 검색된 게임 리스트 조회
	public List<GameVO> SelectGameListByTags(String tag) throws Exception; // 체크박스로 검색
	public List<GameVO> SelectAllGameListByPrice(int price) throws Exception; // 가격으로만 검색
	public List<GameVO> SelectGameListByTagAndPrice(String tagSearch, int price) throws Exception; // 검색키워드와 가격을 동시에 검색
	
	// 할인을 위한 게임 리스트 조회	
	public List<GameVO> SelectNumForSale() throws Exception;
	
	// 선택한 게임의 상세 정보를 조회
	public GameVO SelectGameDetailInfo(int game_code) throws Exception;
	
	// 할인 목록 삭제
	public void discountListRemove() throws Exception;
	
	// 할인 목록 생성
	public void createDiscountList(int discount, int code) throws Exception;
	
	// 할인 목록을 뽑기위한 랜덤게임 10개 선정
	public List<Integer> randomGame(int count);
	
	// 최신 게임 목록 출력 (게임 날짜 최신/내림차순) 메인페이지용
    public List<GameVO> SelectNewestGameListMain() throws Exception;
    
    // 인기 게임 목록 출력 (게임 판매량 내림차순) 메인페이지용
    public List<GameVO> SelectPopularGameListMain() throws Exception;
    
    // 메인페이지용 랜덤 태그
    public List<String> RandomTagMain();

    // 게임상세보기 페이지에 댓글 입력
    public int InsertReply(GameReplyVO rVo) throws Exception;
    
    // 해당 게임상세보기에 가장 최근 작성된 댓글 가져오기
    public GameReplyVO SelectLastReply(int game_code) throws Exception;
    
    // 게임상세페이지 댓글 삭제
	public void DeleteReplyByCode(int grade_code) throws Exception;

	// 게임상세페이지 댓글 가져오기
	public List<GameReplyVO> SelectALLReply(int game_code) throws Exception;

 	
 	
}
