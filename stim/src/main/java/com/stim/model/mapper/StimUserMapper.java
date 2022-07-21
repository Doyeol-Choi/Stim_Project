package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.GameVO;
import com.stim.vo.UserVO;

@Mapper
public interface StimUserMapper {
	
	// 게임 전체 검색
	public List<GameVO> SelectAllGame() throws Exception;
	
	// 로그인
    UserVO getUserAccount(String user_id);

    // 회원가입
    void registerUser(UserVO uVo);
}
