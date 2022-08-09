package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.GameReplyVO;
import com.stim.vo.GameVO;
import com.stim.vo.Pagination;

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
	
	
	// 인기 게임 중에서 태그로 검색하기
	public List<GameVO> SelectGameListPopByTags(String tag) throws Exception;
	public List<GameVO> SelectGameListPopByPrice(int price) throws Exception;
	public List<GameVO> SelectGameListPopByTagsAndPrice(String tagSearch, int price) throws Exception;
	
	// 최신 게임 중에서 태그로 검색하기
	public List<GameVO> SelectGameListNewByTags(String tag) throws Exception;
	public List<GameVO> SelectGameListNewByPrice(int price) throws Exception;
	public List<GameVO> SelectGameListNewByTagsAndPrice(String tagSearch, int price) throws Exception;
	
	// 최신 게임 중에서 태그로 검색하기
	public List<GameVO> SelectGameListSaleByTags(String tag) throws Exception;
	public List<GameVO> SelectGameListSaleByPrice(int price) throws Exception;
	public List<GameVO> SelectGameListSaleByTagsAndPrice(String tagSearch, int price) throws Exception;
	
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
	
	// 해당 게임상세페이지에 댓글 입력
	public int InsertReply(GameReplyVO rVo) throws Exception;
	
	// 해당 게임상세페이지에 가장 최근 작성된 댓글 가져오기
	public GameReplyVO SelectLastReply(int game_code) throws Exception;
	
	// 게임 상세페이지 댓글 수정
	public void UpdateReplyByCode(GameReplyVO rVo) throws Exception;
	
	// 게임 상세페이지 댓글 삭제
	public void DeleteReplyByCode(int grade_code) throws Exception;

	// 게임 상세페이지 댓글 가져오기
//	public List<GameReplyVO> SelectALLReply(int game_code) throws Exception;

	public List<GameReplyVO> SelectALLReply(int game_code, int firstRecordIndex , int lastRecordIndex) throws Exception;

	// 게임 삭제 => 관리자
	public void deleteGame(int game_code) throws Exception;

	// 게임상세페이지 댓글 총 갯수
	public int CountAllReply(int game_code) throws Exception;

	// 게임 상세페이지에서 평점 댓글 비율 보기
	public List<GameReplyVO> SelectGradeRatebyGameCode(int game_code) throws Exception;
	
	// 게임 등록 => 관리자
	public void insertGame(GameVO gVo) throws Exception;
	
	// 게임 수정 => 관리자
	public void updateGame(GameVO gVo) throws Exception;

	// 게임 장르 등록 => 관리자
	public void insertGenre(GameVO gVo) throws Exception;

	// 게임 장르 설정 => 관리자
	public void updateGenre(GameVO gVo) throws Exception;

	

	
}
