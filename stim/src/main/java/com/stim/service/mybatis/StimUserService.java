package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.GameVO;

public interface StimUserService {

	public List<GameVO> SelectAllGame() throws Exception;
}
