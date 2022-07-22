package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimGameListMapper;
import com.stim.vo.GameVO;

@Service
public class StimGameListServiceImp implements StimGameListService {

	@Autowired
	StimGameListMapper stimGameListMapper;
	
	// 게임 리스트 조회
	@Override
	public List<GameVO> SelectAllGameList() throws Exception {
		List<GameVO> gameList = stimGameListMapper.SelectAllGameList();
		return gameList;
	}
	
	
	// 게임 최신 리스트 조회
	@Override
	public List<GameVO> SelectNewestGameList() throws Exception {
		List<GameVO> newGameList = stimGameListMapper.SelectNewestGameList();
		return newGameList;
	}
	
	
}
