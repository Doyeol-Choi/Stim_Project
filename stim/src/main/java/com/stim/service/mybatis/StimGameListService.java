package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.UserVO;

public interface StimGameListService {

	public List<UserVO> SelectAllUser() throws Exception;
}
