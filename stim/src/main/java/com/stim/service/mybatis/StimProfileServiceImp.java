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
	
	@Override
	public UserVO SelectById(int user_code) throws Exception { 
		return	stimProfileMapper.SelectById(user_code); 
	}
	
	@Transactional
	@Override
	public void UpdateUserInfo(UserVO uVo) throws Exception{
		stimProfileMapper.UpdateUserInfo(uVo);
	}

	@Override
	@Transactional
	public void InsertComment(ProFileVO pVo) throws Exception {
		stimProfileMapper.InsertComment(pVo);
	}

	@Override
	public List<ProFileVO> getCommentInfo(int user_code) throws Exception {
		return stimProfileMapper.getCommentInfo(user_code);
	}

	@Override
	public List<ProFileVO> SelectMyGames(int user_code) throws Exception {
		return stimProfileMapper.SelectMyGames(user_code);
	}

	@Override
	public List<ProFileVO> SelectMyFriends(int user_code) throws Exception {	
		return stimProfileMapper.SelectMyFriends(user_code);
	}
	
	@Override
	public void UpdatePicture(ProFileVO pVo) throws Exception {
		stimProfileMapper.UpdatePicture(pVo);
	}


	@Override
	public void DeleteCommentByCode(int comment_code) throws Exception {
		stimProfileMapper.DeleteCommentByCode(comment_code);
	}

	@Override
	public List<ProFileVO> selectFriendRequest(int user_code) throws Exception {
		return stimProfileMapper.selectFriendRequest(user_code);
	}

	@Override
	public void deleteFriendRequest(int friend_code) throws Exception {
		stimProfileMapper.deleteFriendRequest(friend_code);
	}

	@Override
	public void updateFriendRequest(int friend_code) throws Exception {
		stimProfileMapper.updateFriendRequest(friend_code);
	}

	@Override
	public void updateProfileContext(ProFileVO pVo) throws Exception {
		stimProfileMapper.updateProfileContext(pVo);
	}


	
	 
//	public void fileUpload(MultipartFile multipartFile) {
//		String uploadDir = "";
//		Path serverPath = Paths.get(
//                uploadDir +
//                        File.separator +
//                        StringUtils.cleanPath(multipartFile.getOriginalFilename()));
//
//        try {
//            Files.copy(multipartFile.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            log.error("fail to store file : name={}, exception={}",
//                      multipartFile.getOriginalFilename(),
//                      e.getMessage());
//            throw new FileStorageException("fail to store file");
//        }
//    }

	
}
