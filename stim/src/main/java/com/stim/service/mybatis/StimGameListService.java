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
	
	
}
