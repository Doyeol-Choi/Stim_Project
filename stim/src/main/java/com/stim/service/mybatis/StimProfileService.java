package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

public interface StimProfileService {
	
	public UserVO SelectById(int user_code) throws Exception;

	public void SelectByIdForUpdate(UserVO uVo) throws Exception;

	public void DeleteById(String user_id) throws Exception;
	
	public void InsertComment(ProFileVO pVo) throws Exception;
	
	public List<ProFileVO> getCommentInfo(int user_code) throws Exception;
	
	public List<ProFileVO> SelectMyGames(int user_code) throws Exception;
	
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception;
}
