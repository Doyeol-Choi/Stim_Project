package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimGameListMapper;
import com.stim.vo.UserVO;

@Service
public class StimGameListServiceImp implements StimGameListService {

	@Autowired
	StimGameListMapper stimGameListMapper;

	@Override
	public List<UserVO> SelectAllUser() throws Exception {
		return stimGameListMapper.SelectAllUser();
	}
	
}
