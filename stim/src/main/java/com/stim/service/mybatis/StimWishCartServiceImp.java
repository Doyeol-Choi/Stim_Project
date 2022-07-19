package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimWishCartMapper;
import com.stim.vo.UserVO;

@Service
public class StimWishCartServiceImp implements StimWishCartService {

	
	@Autowired
	StimWishCartMapper stimWishCartMapper;

	@Override
	public List<UserVO> SelectAllUser() throws Exception {
		return stimWishCartMapper.SelectAllUser();
	}
}
