package com.stim.service.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimProfileMapper;

@Service
public class StimProfileServiceImp implements StimProfileService {

	@Autowired
	StimProfileMapper stimProfileMapper;
	
//	@Override public List<UserVO> SelectAllUser() throws Exception { return
//	 stimProfileMapper.SelectAllUser(); }
	 
	
	@Override public List<Map<String, Object>> SelectById() throws Exception {
		return stimProfileMapper.SelectById();
	}
	
}
