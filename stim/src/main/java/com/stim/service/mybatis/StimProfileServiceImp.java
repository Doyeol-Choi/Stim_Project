package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimProfileMapper;
import com.stim.vo.UserVO;

@Service
public class StimProfileServiceImp implements StimProfileService {

	@Autowired
	StimProfileMapper stimProfileMapper;
	
	/*
	 * @Override public List<UserVO> SelectAllUser() throws Exception { return
	 * stimProfileMapper.SelectAllUser(); }
	 */
	
	@Override public List<UserVO> SelectById() throws Exception {
		return stimProfileMapper.SelectById();
	}
	
}
