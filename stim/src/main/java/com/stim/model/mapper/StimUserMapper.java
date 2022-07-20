package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.GameVO;

@Mapper
public interface StimUserMapper {
	
	// 유저 전체 검색
	public List<GameVO> SelectAllGame() throws Exception;
	 
}
