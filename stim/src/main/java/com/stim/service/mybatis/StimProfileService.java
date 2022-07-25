package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.UserVO;

public interface StimProfileService {

	//public List<UserVO> SelectAllUser() throws Exception;
	public UserVO SelectById(String user_id) throws Exception;

	public void SelectByIdForUpdate(UserVO uVo) throws Exception;

	public void DeleteById(String user_id) throws Exception;
}
