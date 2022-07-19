package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.UserVO;

public interface StimUserService {

	public List<UserVO> SelectAllUser() throws Exception;
}
