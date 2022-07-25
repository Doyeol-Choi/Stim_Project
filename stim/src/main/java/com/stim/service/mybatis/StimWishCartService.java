package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.CartVO;
import com.stim.vo.WishVO;

public interface StimWishCartService {
	// 찜목록 가져오기
	public List<WishVO> SelectWishGame(int user_code) throws Exception;
	// 장바구니 가져오기
	public List<CartVO> SelectCartGame(int user_code) throws Exception;
	// 찜목록에서 장바구니 넣기
	public void InsertCartGame(int user_code, int game_code) throws Exception;
	// 찜목록 삭제
	public void DeleteWishGame(int wish_code) throws Exception;
	// 장바구니 삭제
	public void DeleteCartGame(int cart_code) throws Exception;
	
	// 장바구니 총금액
	public String TotalPriceGame(int user_code) throws Exception;
	
}
