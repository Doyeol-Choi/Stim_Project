package com.stim.service.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	
	// 할인 목록 갱신을 위해 기존 목록 삭제
	@Override
	@Transactional
	public void discountListRemove() throws Exception {
		stimGameListMapper.discountListRemove();
	}


	// 할인 목록 생성
	@Override
	@Transactional
	public void createDiscountList(int discount, int code) throws Exception {
		stimGameListMapper.createDiscountList(discount, code);
	}

	// 할인 목록을 뽑기위한 랜덤게임 10개 선정
	@Override
	public List<Integer> randomGame(int count) {
		Set<Integer> set = new HashSet<>();
		Random random = new Random();
		while (set.size() < 10) {
			int num = random.nextInt(count) + 1;
			set.add(num);				
		}
		List<Integer> list = new ArrayList<>(set);
		return list;
	}

	// 최신 게임 목록 출력 (게임 날짜 최신/내림차순) 메인페이지용
	@Override
	public List<GameVO> SelectNewestGameListMain() throws Exception {
		return stimGameListMapper.SelectNewestGameListMain();
	}

	// 인기 게임 목록 출력 (게임 판매량 내림차순) 메인페이지용
	@Override
	public List<GameVO> SelectPopularGameListMain() throws Exception {
		return stimGameListMapper.SelectPopularGameListMain();
	}

	// 메인페이지용 랜덤 태그
	@Override
	public List<String> RandomTagMain() {
		List<String> tagList = new ArrayList<>();
		String[] tags = {"액션", "퍼즐", "오픈 월드", "시뮬레이션", "생존", "좀비", "건설", "협동", "애니메이션", "RPG", "캐주얼", "멀티플레이어"};
		Set<Integer> set = new HashSet<>();
		Random random = new Random();
		while(set.size() < 4) {
			int num = random.nextInt(12);
			set.add(num);
		}
		Iterator<Integer> iter = set.iterator();
		while(iter.hasNext()) {
			tagList.add(tags[iter.next()]);
		}
		return tagList;
	}
	
}
