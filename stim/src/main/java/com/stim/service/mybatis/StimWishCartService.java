package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.CartVO;
import com.stim.vo.WishVO;

public interface StimWishCartService {

	public List<WishVO> SelectWishGame() throws Exception;

	public List<CartVO> SelectCartGame() throws Exception;
}
