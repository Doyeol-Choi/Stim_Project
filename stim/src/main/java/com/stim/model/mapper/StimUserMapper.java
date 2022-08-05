package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.vo.GameVO;
import com.stim.vo.SearchUserVO;
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
	
	// 유저 찾기에서 닉네임으로 유저 목록 검색
	public List<SearchUserVO> SearchUserByNickname(String user_nickname);
	
	// 유저 찾기에서 유저코드로 유저 검색
	public SearchUserVO SearchUserByCode(int user_code);

	// 로그인중 닉네임으로 유저 검색
	public List<SearchUserVO> SearchUserByNicknameLogin(String user_nickname, int login_code);

	// 로그인중 공백으로 유저 검색
	public SearchUserVO SearchUserByCodeLogin(int code, int login_code);

	// 친구 요청 전 확인
	public List<Integer> FriendRequestCheck(int login_code, int user_code);
	
	// 친구 요청
	public void AddFriendRequest(int login_code, int user_code);

	// 친구 요청 취소 전 확인
	public String FriendRequestAcceptCheck(int login_code, int user_code);

	// 친구 요청 취소
	public void CancleFriendRequset(int login_code, int user_code);
	
	// 전체 유저 검색
	public List<UserVO> SelectUserAll();

	// 회원 탈퇴
	public void deleteUser(int user_code);
	
	// 유저코드로 유저정보 받기
	public UserVO selectUserByUserCode(int user_code);
	
	// 기본 프로필 사진으로 변경
	public void profilePictureRe(int user_code);

}
