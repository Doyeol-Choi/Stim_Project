package com.stim.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimProfileMapper;
import com.stim.vo.UserVO;

@Service
public class StimProfileServiceImp implements StimProfileService {

	@Autowired
	StimProfileMapper stimProfileMapper;
	
	@Override public UserVO SelectById(String user_id) throws Exception { 
		return	stimProfileMapper.SelectById(user_id); 
	}
	
	@Override public UserVO SelectByIdForUpdate(String user_id) throws Exception{
		return stimProfileMapper.SelectByIdForUpdate(user_id);
	}
	 
	

	
}
