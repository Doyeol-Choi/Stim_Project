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


	// 태그 키워드 또는 가격으로 검색된 리스트 조회
	@Override
	public List<GameVO> SelectGameListByTags(String tag) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListByTags(tag);
		return tagList;
	}
	@Override
	public List<GameVO> SelectAllGameListByPrice(int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectAllGameListByPrice(price);
		return tagList;
	}


	// 할인을 위한 게임 리스트 조회
	@Override
	public List<GameVO> SelectAllGameListForSale() throws Exception {
		List<GameVO> gameListForSale = stimGameListMapper.SelectAllGameListForSale();
		return gameListForSale;
	}

	
	
	// 선택한 게임의 상세 정보를 조회
	@Override
	public GameVO SelectGameDetailInfo(int game_code) throws Exception {
		GameVO gameDetailInfo = stimGameListMapper.SelectGameDetailInfo(game_code);
		return gameDetailInfo;
	}








	
	
}
