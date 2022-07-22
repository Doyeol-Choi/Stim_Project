package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.GameVO;

public interface StimGameListService {

	// 게임 리스트 조회
	public List<GameVO> SelectAllGameList() throws Exception;
	
	
	// 최신 게임 리스트 조회
	public List<GameVO> SelectNewestGameList() throws Exception;
}
