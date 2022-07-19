package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.UserVO;

@Mapper
public interface StimWishCartMapper {

	// 예시 메서드 수정해서 사용
	public List<UserVO> SelectAllUser() throws Exception;
}
