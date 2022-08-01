package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

public interface StimProfileService {
	
	public UserVO SelectById(int user_code) throws Exception;

	public void UpdateUserInfo(UserVO uVo) throws Exception;
	
	public void InsertComment(ProFileVO pVo) throws Exception;
	
	public List<ProFileVO> getCommentInfo(int user_code) throws Exception;

	public List<ProFileVO> SelectMyGames(int user_code) throws Exception;
	
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception;

	public void UpdatePicture(ProFileVO pVo) throws Exception;

	public void DeleteCommentByCode(int comment_code) throws Exception;
	
	public List<ProFileVO> selectFriendRequest(int user_code) throws Exception;
	
	public void deleteFriendRequest(int friend_code) throws Exception;
	
	public void updateFriendRequest(int friend_code) throws Exception;
	
	public void updateProfileContext(ProFileVO pVo) throws Exception;

}
