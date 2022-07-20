package com.stim.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.UserVO;

@Mapper
public interface StimProfileMapper {

	// 예시 메서드 수정해서 사용
	//public List<UserVO> SelectAllUser() throws Exception;
	
	public List<Map<String, Object>> SelectById() throws Exception;
}
