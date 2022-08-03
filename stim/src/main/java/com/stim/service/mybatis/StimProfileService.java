package com.stim.service.mybatis;

import java.util.List;

import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

public interface StimProfileService {
	
	// 유저 코드로 프로필 주소 가기
	public UserVO SelectById(int user_code) throws Exception;
	
	// 유저 정보 수정
	public void UpdateUserInfo(UserVO uVo) throws Exception;
	
	// 프로필 댓글 입력 
	public int InsertComment(ProFileVO pVo) throws Exception;
	
	// 프로필 댓글 목록 출력
	public List<ProFileVO> getCommentInfo(int user_code) throws Exception;

	// 보유 게임 목록 출력
	public List<ProFileVO> SelectMyGames(int user_code) throws Exception;
	
	// 친구 목록 출력
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception;

	// 프로필 사진 업데이트
	public void UpdatePicture(ProFileVO pVo) throws Exception;

	// 댓글 삭제
	public void DeleteCommentByCode(int comment_code) throws Exception;
	
	// 친구 추가 요청 목록 출력
	public List<ProFileVO> selectFriendRequest(int user_code) throws Exception;
	
	// 친구 추가 요청 거절
	public void deleteFriendRequest(int friend_code) throws Exception;
	
	// 친구 추가 요청 승인
	public void updateFriendRequest(int friend_code) throws Exception;
	
	// 프로필 할 말 수정
	public void updateProfileContext(ProFileVO pVo) throws Exception;
	
	// 해당 프로필에 가장 최근 작성된 댓글 가져오기
	public ProFileVO selectLastComment(int user_code) throws Exception;

}
