package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

@Mapper
public interface StimProfileMapper {
	
	// 유저 검색 후 프로필 출력
	public UserVO SelectById(int user_code) throws Exception;

	// 유저 정보 업데이트 
	public void UpdateUserInfo(UserVO uVo);
	
	// 댓글 삽입
	public int InsertComment(ProFileVO pVo) throws Exception;

	// 댓글 출력
	public List<ProFileVO> getCommentInfo(int user_code, int firstRecordIndex, int lastRecordIndex);
	
	//public List<ProFileVO> getCommentInfo(int user_code) throws Exception;
 
	// 게임 출력
	public List<ProFileVO> SelectMyGames(int user_code) throws Exception;

	// 친구 출력
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception; 
	
	// 프로필 사진 변경
	public void UpdatePicture(ProFileVO pVo) throws Exception;

	// 프로필 댓글 삭제
	public void DeleteCommentByCode(int comment_code) throws Exception;

	// 친구 요청 출력
	public List<ProFileVO> selectFriendRequest(int user_code) throws Exception;

	// 친구 요청 삭제
	public void deleteFriendRequest(int friend_code) throws Exception;

	// 친구 요청 수락
	public void updateFriendRequest(int friend_code) throws Exception;
	
	// 프로필 자기소개 수정
	public void updateProfileContext(int user_code, String profile_context);

	// 프로필 자기소개 출력
	public String SelectProfileContext(int user_code) throws Exception;

	// 친구 삭제
	public void deleteMyFriend(int friend_code) throws Exception;

	// 해당 프로필에 가장 최근 작성된 댓글 가져오기
	public ProFileVO selectLastComment(int user_code) throws Exception;

	// 할말 삽입
	public void insertProfileContext(int user_code, String profile_context);

	// 프로필 댓글 총 갯수
	public int CountAllComment(int user_code);

	// 댓글 수정
	public void updateComment(ProFileVO pVo);

	

}
