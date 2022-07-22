package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.UserVO;

public interface StimProfileService {

	//public List<UserVO> SelectAllUser() throws Exception;
	public UserVO SelectById(String user_id) throws Exception;

	public UserVO SelectByIdForUpdate(String user_id) throws Exception;
}
