package com.stim.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stim.model.mapper.StimProfileMapper;
import com.stim.vo.UserVO;

@Service
public class StimProfileServiceImp implements StimProfileService {

	@Autowired
	StimProfileMapper stimProfileMapper;
	
	@Override public UserVO SelectById(String user_id) throws Exception { 
		return	stimProfileMapper.SelectById(user_id); 
	}
	
	@Transactional
	@Override public void SelectByIdForUpdate(UserVO uVo) throws Exception{
		stimProfileMapper.SelectByIdForUpdate(uVo);
	}

	@Override
	public void DeleteById(String user_id) throws Exception {
		stimProfileMapper.DeleteById(user_id);
	}
	 
	

	
}
