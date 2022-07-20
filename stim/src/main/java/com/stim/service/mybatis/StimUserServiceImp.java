package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimUserMapper;
import com.stim.vo.GameVO;

@Service
public class StimUserServiceImp implements StimUserService {

	@Autowired
	StimUserMapper stimUserMapper;

	@Override
	public List<GameVO> SelectAllGame() throws Exception {
		return stimUserMapper.SelectAllGame();
	}
	
}
