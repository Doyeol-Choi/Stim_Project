package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.GameVO;

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
	public List<GameVO> SelectGameListByTags(String tag) throws Exception;
	public List<GameVO> SelectAllGameListByPrice(int price) throws Exception;
	
	// 할인을 위한 게임 리스트 조회
	public List<GameVO> SelectAllGameListForSale() throws Exception;
	
	// 선택한 게임의 상세 정보를 조회
	public GameVO SelectGameDetailInfo(int game_code) throws Exception;
	
	// 할인 목록 삭제
	public void discountListRemove() throws Exception;
	
	// 할인 목록 생성
	public void createDiscountList(int discount, int code) throws Exception;
	
	// 할인 목록을 뽑기위한 랜덤게임 10개 선정
	public List<Integer> randomGame(int count);
}
