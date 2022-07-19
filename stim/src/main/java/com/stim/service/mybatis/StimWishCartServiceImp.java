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
	public List<WishVO> SelectWishGame() throws Exception {
		return stimWishCartMapper.SelectWishGame();
	}
	
	@Override
	public List<CartVO> SelectCartGame() throws Exception {
		return stimWishCartMapper.SelectCartGame();
	}
	
}
