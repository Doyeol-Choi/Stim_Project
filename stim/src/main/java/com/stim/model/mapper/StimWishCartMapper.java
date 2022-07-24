package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.CartVO;
import com.stim.vo.WishVO;

@Mapper
public interface StimWishCartMapper {

	// 찜목록 
	public List<WishVO> SelectWishGame(int user_code) throws Exception;
	
	// 장바구니
	public List<CartVO> SelectCartGame(int user_code) throws Exception;
	
	// 찜목록에서 장바구니 추가
	public void InsertCartGame(int user_code, int game_code) throws Exception;
	
	// 찜목록에서 삭제
	public void DeleteWishGame(int wish_code) throws Exception;
	
	// 장바구니에서 삭제
	public void DeleteCartGame(int cart_code) throws Exception;
	
	// 장바구니 총금액
	public List<CartVO> TotalPriceGame(int user_code) throws Exception;
}
