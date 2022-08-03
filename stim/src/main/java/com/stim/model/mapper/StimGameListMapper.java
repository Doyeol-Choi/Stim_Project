package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.stim.vo.GameVO;

@Mapper
public interface StimGameListMapper {

	
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
	public List<GameVO> SelectAllGameListByPrice(int price); // 가격으로만 검색
	public List<GameVO> SelectGameListByTagAndPrice(String tagSearch, int price) throws Exception; // 검색 키워드와 가격을 동시에 검색
	
	// 할인을 위한 게임 리스트 조회
	public List<GameVO> SelectNumForSale()throws Exception;
	
	// 선택한 게임의 상세 정보를 조회
	public GameVO SelectGameDetailInfo(int game_code) throws Exception;
	
	// 할인 목록 갱신을 위해 기존 목록 삭제
	public void discountListRemove() throws Exception;

	// 할인 목록 생성
	public void createDiscountList(int discount, int code) throws Exception;

	// 최신 게임 목록 출력 (게임 날짜 최신/내림차순) 메인페이지용
	public List<GameVO> SelectNewestGameListMain() throws Exception;

	// 인기 게임 목록 출력 (게임 판매량 내림차순) 메인페이지용
	public List<GameVO> SelectPopularGameListMain() throws Exception;
	
	
}
