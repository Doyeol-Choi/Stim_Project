package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

@Mapper
public interface StimProfileMapper {

	// 유저 프로필 
	
	// - 1. 유저 검색 후 프로필 출력
	public UserVO SelectById(int user_code) throws Exception;

	// - 2. 유저 검색 후 업데이트 
	public void SelectByIdForUpdate(UserVO uVo);
	
	// 
	public void DeleteById(String user_id) throws Exception;

	
	// 댓글 삽입
	public void InsertComment(ProFileVO pVo);

	// 댓글 출력
	public List<ProFileVO> getCommentInfo(int user_code);
	
	
	
	
	
}
