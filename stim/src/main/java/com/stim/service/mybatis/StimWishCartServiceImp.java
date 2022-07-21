package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimWishCartMapper;
import com.stim.vo.CartVO;
import com.stim.vo.WishVO;

@Service
public class StimWishCartServiceImp implements StimWishCartService {

	
	@Autowired
	StimWishCartMapper stimWishCartMapper;

	@Override
	public List<WishVO> SelectWishGame(int user_code) throws Exception {
		return stimWishCartMapper.SelectWishGame(user_code);
	}
	
	@Override
	public List<CartVO> SelectCartGame(int user_code) throws Exception {
		return stimWishCartMapper.SelectCartGame();
	}
	@Override
	public void InsertCartGame(int user_code,int game_code) throws Exception {
		
	}

	@Override
	public void DeleteWishGame(int wish_code) throws Exception {
		
	}

	@Override
	public void DeleteCartGame(int cart_code) throws Exception {
		
	}
}
