package com.stim.service.mybatis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stim.model.mapper.StimGameListMapper;
import com.stim.vo.GameReplyVO;
import com.stim.vo.GameVO;
import com.stim.vo.Pagination;

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
	@Override
	public List<GameVO> SelectGameListByTagAndPrice(String tagSearch, int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListByTagAndPrice(tagSearch, price);
		return tagList;
	}
	
	// 인기 게임 중에서 태그로 검색하기
	@Override
	public List<GameVO> SelectGameListPopByTags(String tag) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListPopByTags(tag);
		return tagList;
	}
	@Override
	public List<GameVO> SelectGameListPopByPrice(int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListPopByPrice(price);
		return tagList;
	}
	@Override
	public List<GameVO> SelectGameListPopByTagsAndPrice(String tagSearch, int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListPopByTagsAndPrice(tagSearch, price);
		return tagList;
	}
	
	// 최신 게임 중에서 태그로 검색하기
	@Override
	public List<GameVO> SelectGameListNewByTags(String tag) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListNewByTags(tag);
		return tagList;
	}
	@Override
	public List<GameVO> SelectGameListNewByPrice(int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListNewByPrice(price);
		return tagList;
	}
	@Override
	public List<GameVO> SelectGameListNewByTagsAndPrice(String tagSearch, int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListNewByTagsAndPrice(tagSearch, price);
		return tagList;
	}
	
	// 할인 게임 중에서 태그로 검색하기
	@Override
	public List<GameVO> SelectGameListSaleByTags(String tag) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListSaleByTags(tag);
		return tagList;
	}
	@Override
	public List<GameVO> SelectGameListSaleByPrice(int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListSaleByPrice(price);
		return tagList;
	}
	@Override
	public List<GameVO> SelectGameListSaleByTagsAndPrice(String tagSearch, int price) throws Exception {
		List<GameVO> tagList = stimGameListMapper.SelectGameListSaleByTagsAndPrice(tagSearch, price);
		return tagList;
	}
	


	// 할인을 위한 게임 리스트 조회
	@Override
	public List<GameVO> SelectNumForSale() throws Exception {
		List<GameVO> gameListForSale = stimGameListMapper.SelectNumForSale();
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

	// 게임 상세보기에 댓글 입력
	@Override
	@Transactional
	public int InsertReply(GameReplyVO rVo) throws Exception {
		return stimGameListMapper.InsertReply(rVo);
	}

	// 해당 게임 상세페이지에 가장 최근 작성된 댓글 가져오기
	@Override
	public GameReplyVO SelectLastReply(int game_code) throws Exception {
		return stimGameListMapper.SelectLastReply(game_code);
	}

	@Override
	public void UpdateReplyByCode(int grade_code) throws Exception {
		stimGameListMapper.UpdateReplyByCode(grade_code);
	}
	
	// 게임상세페이지 댓글 삭제
	@Override
	@Transactional
	public void DeleteReplyByCode(int grade_code) throws Exception {
		stimGameListMapper.DeleteReplyByCode(grade_code);
		
	}

	// 게임 상세페이지 댓글 가져오기
	@Override
	public List<GameReplyVO> SelectALLReply(int game_code, int firstRecordIndex , int lastRecordIndex) throws Exception {
		return stimGameListMapper.SelectALLReply(game_code, firstRecordIndex, lastRecordIndex);
	}

	// 게임상세페이지 댓글 총 갯수
	@Override
	public int CountAllReply(int game_code) throws Exception {
		return stimGameListMapper.CountAllReply(game_code);
	}

	
	// 게임 상세 페이지에서 평점 댓글 비율 읽기
	@Override
	public List<GameReplyVO> SelectGradeRatebyGameCode(int game_code) throws Exception {
		return stimGameListMapper.SelectGradeRatebyGameCode(game_code);
	}


	// 게임 삭제 => 관리자
	@Override
	@Transactional
	public void deleteGame(int game_code) throws Exception {
		stimGameListMapper.deleteGame(game_code);
	}

	// 게임 등록 => 관리자
	@Override
	@Transactional
	public void insertGame(GameVO gVo) throws Exception {
		stimGameListMapper.insertGame(gVo);
		
	}

	// 게임 수정 => 관리자
	@Override
	@Transactional
	public void updateGame(GameVO gVo) throws Exception {
		stimGameListMapper.updateGame(gVo);
	}

	// 게임 장르 등록 => 관리자
	@Override
	@Transactional
	public void insertGenre(GameVO gVo) throws Exception {
		stimGameListMapper.insertGenre(gVo);
	}

	// 게임 장르 수정 => 관리자
	@Override
	@Transactional
	public void updateGenre(GameVO gVo) throws Exception {
		stimGameListMapper.updateGenre(gVo);
	}

	
}
