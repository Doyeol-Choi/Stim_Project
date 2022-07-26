package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stim.model.mapper.StimWishCartMapper;
import com.stim.vo.CartVO;
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
	
}
