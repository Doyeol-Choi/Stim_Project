package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimGameListMapper;
import com.stim.vo.GameVO;
import com.stim.vo.GenreVO;

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

	
	// 게임 인기 리스트 조회
	@Override
	public List<GameVO> SelectPopularGameList() throws Exception {
		List<GameVO> popularGameList = stimGameListMapper.SelectPopularGameList();
		return popularGameList;
	}


	// 키워드로 검색된 리스트 조회
	@Override
	public List<GameVO> SelectGameListByKeyword(String keyword) throws Exception {
		List<GameVO> gVoList = null;
		gVoList = stimGameListMapper.SelectGameListByKeyword(keyword);	
		
		
		return gVoList;
	}


	@Override
	public List<GameVO> SelectGameListByTags(String tag) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListByTags(tag);
		return tagList;
	}






	
	
}
