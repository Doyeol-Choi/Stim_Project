package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stim.model.mapper.StimProfileMapper;
import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

@Service
public class StimProfileServiceImp implements StimProfileService {

	@Autowired
	StimProfileMapper stimProfileMapper;
	
	@Override public UserVO SelectById(int user_code) throws Exception { 
		return	stimProfileMapper.SelectById(user_code); 
	}
	
	@Transactional
	@Override public void SelectByIdForUpdate(UserVO uVo) throws Exception{
		stimProfileMapper.SelectByIdForUpdate(uVo);
	}

	@Override
	public void DeleteById(String user_id) throws Exception {
		stimProfileMapper.DeleteById(user_id);
	}

	@Override
	@Transactional
	public void InsertComment(ProFileVO pVo) throws Exception {
		stimProfileMapper.InsertComment(pVo);
	}

	@Override
	public List<ProFileVO> getCommentInfo(int user_code) throws Exception {
		return stimProfileMapper.getCommentInfo(user_code);
	}

	@Override
	public List<ProFileVO> SelectMyGames(int user_code) throws Exception {
		
		return stimProfileMapper.SelectMyGames(user_code);
	}

	@Override
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception {
		
		return stimProfileMapper.SelectMyFriends(user_code);
	}
	 
	

	
}
