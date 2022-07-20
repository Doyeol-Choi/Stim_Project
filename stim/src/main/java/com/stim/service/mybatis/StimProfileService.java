package com.stim.service.mybatis;

import java.util.List;
import java.util.Map;

public interface StimProfileService {

	//public List<UserVO> SelectAllUser() throws Exception;
	public List<Map<String, Object>> SelectById() throws Exception;
}
