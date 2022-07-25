package com.stim.model.mapper;



import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.UserVO;

@Mapper
public interface StimProfileMapper {

	// 유저 프로필 
	public UserVO SelectById(String user_id) throws Exception;

	public void SelectByIdForUpdate(UserVO uVo);
	
	// 
	public void DeleteById(String user_id) throws Exception;
	
	//public List<Map<String, Object>> SelectById() throws Exception;
}
