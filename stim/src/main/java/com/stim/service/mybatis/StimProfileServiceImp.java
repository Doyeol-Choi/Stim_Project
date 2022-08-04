package com.stim.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stim.model.mapper.StimProfileMapper;
import com.stim.vo.ProFileVO;
import com.stim.vo.UserVO;

@Service
public class StimProfileServiceImp implements StimProfileService {

	@Autowired
	StimProfileMapper stimProfileMapper;
	
	// 유저 코드로 프로필 주소 가기
	@Override
	public UserVO SelectById(int user_code) throws Exception { 
		return	stimProfileMapper.SelectById(user_code); 
	}
	
	// 유저 정보 수정
	@Transactional
	@Override
	public void UpdateUserInfo(UserVO uVo) throws Exception{
		stimProfileMapper.UpdateUserInfo(uVo);
	}

	
	@Override
	@Transactional
	public int InsertComment(ProFileVO pVo) throws Exception {
		return stimProfileMapper.InsertComment(pVo);
	}

	// 프로필 댓글 목록 출력
	@Override
	public List<ProFileVO> getCommentInfo(int user_code) throws Exception {
		return stimProfileMapper.getCommentInfo(user_code);
	}

	// 보유 게임 목록 출력
	@Override
	public List<ProFileVO> SelectMyGames(int user_code) throws Exception {
		return stimProfileMapper.SelectMyGames(user_code);
	}

	// 친구 목록 출력
	@Override
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception {	
		return stimProfileMapper.SelectMyFriends(user_code);
	}
	
	// 프로필 사진 업데이트
	@Override
	public void UpdatePicture(ProFileVO pVo) throws Exception {
		stimProfileMapper.UpdatePicture(pVo);
	}


	// 댓글 삭제
	@Override
	public void DeleteCommentByCode(int comment_code) throws Exception {
		stimProfileMapper.DeleteCommentByCode(comment_code);
	}

	// 친구 추가 요청 목록 출력
	@Override
	public List<ProFileVO> selectFriendRequest(int user_code) throws Exception {
		return stimProfileMapper.selectFriendRequest(user_code);
	}

	// 친구 추가 요청 거절
	@Override
	public void deleteFriendRequest(int friend_code) throws Exception {
		stimProfileMapper.deleteFriendRequest(friend_code);
	}

	// 친구 추가 요청 승인
	@Override
	public void updateFriendRequest(int friend_code) throws Exception {
		stimProfileMapper.updateFriendRequest(friend_code);
	}

	@Override
	public void deleteMyFriend(int friend_code) throws Exception {
		stimProfileMapper.deleteMyFriend(friend_code);
		
	}
	

}
