package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stim.model.mapper.StimWishCartMapper;
import com.stim.vo.CartVO;
import com.stim.vo.ProFileVO;
import com.stim.vo.WishVO;

@Service
public class StimWishCartServiceImp implements StimWishCartService {

	
	@Autowired
	StimWishCartMapper stimWishCartMapper;
	// 찜목록 조회
	@Override
	public List<WishVO> SelectWishGame(int user_code) throws Exception {
		return stimWishCartMapper.SelectWishGame(user_code);
	}
	// 장바구니 조회
	@Override
	public List<CartVO> SelectCartGame(int user_code) throws Exception {
		return stimWishCartMapper.SelectCartGame(user_code);
	}
	//찜목록에서 장바구니 넣기
	@Override
	@Transactional
	public void InsertCartGame(int user_code, int game_code) throws Exception {
		stimWishCartMapper.InsertCartGame(user_code,game_code);
	}
	//찜목록 삭제
	@Override
	@Transactional	// 트랜잭션관리 commit등
	public void DeleteWishGame(int wish_code) throws Exception {
		stimWishCartMapper.DeleteWishGame(wish_code);
	}
	//장바구니 삭제
	@Override
	@Transactional
	public void DeleteCartGame(int cart_code) throws Exception {
		stimWishCartMapper.DeleteCartGame(cart_code);
	}
	// // 장바구니 총금액
	@Override
	public Integer TotalPriceGame(int user_code) throws Exception{
		return stimWishCartMapper.TotalPriceGame(user_code);
		
	}
	// 결제 완료 후 보유게임 목록에 넣기
	@Override
	@Transactional
	public void InsertMyGame(int user_code, int game_code) throws Exception{
		stimWishCartMapper.InsertMyGame(user_code, game_code);
	}
	
	
	// 결제완료 후 장바구니 목록 삭제
	@Override
	@Transactional
	public void DeleteCartAllGame(int user_code, int game_code) throws Exception{
		stimWishCartMapper.DeleteCartAllGame(user_code, game_code);
	}
	// 결제완료 후 찜목록 삭제
	@Override
	@Transactional
	public void DeleteWishAllGame(int user_code, int game_code) throws Exception {
		stimWishCartMapper.DeleteWishAllGame(user_code, game_code);
		
	}
	// 장바구니 게임 코드 가져오기
	@Override
	public List<Integer> SelectCartGameCode(Integer user_code) throws Exception {
		return stimWishCartMapper.SelectCartGameCode(user_code);
	}
	// 찜목록 게임코드 가져오기
	public List<Integer> SelectWishGameCode(Integer user_code)throws Exception{
		return stimWishCartMapper.SelectWishGameCode(user_code);
	}
	// 보유게임에서 게임코드 가져오기
	@Override
	public List<Integer> SelectMyGameCode(Integer user_code) throws Exception {
		return stimWishCartMapper.SelectMyGameCode(user_code);
	}

	// 게임 상세보기에서 찜목록에 넣기
	@Override
	@Transactional
	public void InsertWishGame(int user_code, int game_code) throws Exception {
		stimWishCartMapper.InsertWishGame(user_code,game_code);
		
	}
	
	// 구매시 판매량 증가
	@Override
	@Transactional
	public void UpdateSalesRate(int game_code) throws Exception {
		stimWishCartMapper.UpdateSalesRate(game_code);
		
	}
	
}
