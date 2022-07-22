package com.stim.model.mapper;



import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.UserVO;

@Mapper
public interface StimProfileMapper {

	// 예시 메서드 수정해서 사용
	public UserVO SelectById(String user_id) throws Exception;

	public UserVO SelectByIdForUpdate(String user_id);
	
	//public List<Map<String, Object>> SelectById() throws Exception;
}
