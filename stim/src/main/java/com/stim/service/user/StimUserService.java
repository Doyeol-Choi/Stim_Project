package com.stim.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.stim.model.mapper.StimUserMapper;
import com.stim.vo.SearchUserVO;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	// 생성자 자동 생성 및 final 변수를 의존관계를 자동으로 설정해 준다.
public class StimUserService implements UserDetailsService {
	
	private final StimUserMapper stimUserMapper;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional	// 트랜잭션 보장이 된 메소드로 설정 해준다.
    public void registerUser(UserVO uVo) throws Exception {	// 회원가입
        uVo.setUser_password(encoder.encode(uVo.getPassword()));
        uVo.setUser_admin("N");
        stimUserMapper.registerUser(uVo);
    }
	
	public int checkById(String user_id) {
		int result = stimUserMapper.checkById(user_id);
		
		return result;
	}
	
	// 회원가입 유효성 체크
	@Transactional(readOnly = true)
	public Map<String, String> validateHandling(Errors errors) throws Exception {
		Map<String, String> validatorResult = new HashMap<>();
		
		// 유효성 검사에 실패한 목록
		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		
		return validatorResult;
	}	
	
	@Override
	public UserVO loadUserByUsername(String user_id) throws UsernameNotFoundException {
		//여기서 받은 유저 패스워드와 비교하여 유저 정보 받기
		UserVO userVo = stimUserMapper.getUserAccount(user_id);
		
		if (userVo == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
		
		return userVo;
	}
	
	// 아이디 찾기
	public String findIdByEmail(String user_email) throws Exception {
		String email = stimUserMapper.findIdByEmail(user_email);
		
		return email;
	}

	// 비밀번호 찾기
	public int findPwByUserId(String user_id, String user_email) throws Exception {
		int result = stimUserMapper.findPwByUserId(user_id, user_email);
		
		return result;
	}

	// 비밀번호 찾기 => 새로운 비밀번호
	@Transactional
	public void changePwByUserId(String user_id, String password) throws Exception {
		String user_password = encoder.encode(password);
		stimUserMapper.changePwByUserId(user_id, user_password);
	}
	
	// 유저 찾기에서 닉네임으로 유저 목록 검색
	public List<SearchUserVO> SearchUserByNickname(String user_nickname) throws Exception {
		return stimUserMapper.SearchUserByNickname(user_nickname);
	}

	// 유저 찾기에서 유저 코드로 유저 검색
	public SearchUserVO SearchUserByCode(int user_code) throws Exception {
		return stimUserMapper.SearchUserByCode(user_code);
	}

	// 램덤으로 유저코드 뽑기
	public List<Integer> randomCode(int count) {
		Set<Integer> set = new HashSet<>();
		Random random = new Random();
		while (set.size() < 5) {
			int num = random.nextInt(count)+1;			
			set.add(num);
		}
		List<Integer> list = new ArrayList<>(set);
		return list;
	}

	// 로그인중 닉네임으로 유저 검색
	public List<SearchUserVO> SearchUserByNicknameLogin(String user_nickname, int login_code) throws Exception {
		return stimUserMapper.SearchUserByNicknameLogin(user_nickname, login_code);
	}

	// 로그인중 공백으로 유저 검색
	public SearchUserVO SearchUserByCodeLogin(int code, int login_code) throws Exception {
		return stimUserMapper.SearchUserByCodeLogin(code, login_code);
	}

	// 친구 요청 전 확인
	public List<Integer> FriendRequestCheck(int login_code, int user_code) throws Exception {
		return stimUserMapper.FriendRequestCheck(login_code, user_code);
	}

	// 친구 요청 하기
	@Transactional
	public void AddFriendRequest(int login_code, int user_code) throws Exception {
		stimUserMapper.AddFriendRequest(login_code, user_code);
	}
	
	// 친구 요청 취소 전 확인
	public String FriendRequestAcceptCheck(int login_code, int user_code) throws Exception {
		return stimUserMapper.FriendRequestAcceptCheck(login_code, user_code);
	}

	// 친구 요청 취소
	@Transactional
	public void CancleFriendRequset(int login_code, int user_code) throws Exception {
		stimUserMapper.CancleFriendRequset(login_code, user_code);
	}
	
	// 전체 유저 검색
	public List<UserVO> SelectUserAll() throws Exception {
		return stimUserMapper.SelectUserAll();
	}

	// 회원 탈퇴
	@Transactional
	public void deleteUser(int user_code) throws Exception {
		stimUserMapper.deleteUser(user_code);
	}
	
	// 유저코드로 유저정보 받기
	public UserVO selectUserByUserCode(int user_code) throws Exception {
		return stimUserMapper.selectUserByUserCode(user_code);
	}
	
	// 기본 프로필 사진으로 변경
	@Transactional
	public void profilePictureRe(int user_code) throws Exception {
		stimUserMapper.profilePictureRe(user_code);
	}
	
}
