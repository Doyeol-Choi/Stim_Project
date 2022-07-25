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
    
    // 아이디 중복 체크
    int checkById(String user_id);
    
    // 아이디 찾기
    String findIdByEmail(String user_email);
    
    // 비밀번호 찾기
	public int findPwByUserId(String user_id, String user_email);

	// 비밀번호 찾기 => 새로운 비밀번호
	public void changePwByUserId(String user_id, String user_password);
}
