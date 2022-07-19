package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.UserVO;

@Mapper
public interface StimUserMapper {
	
	// 유저 전체 검색
	public List<UserVO> SelectAllUser() throws Exception;
	 
}
