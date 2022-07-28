package com.stim.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.stim.model.mapper.StimUserMapper;
import com.stim.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	// 생성자 자동 생성 및 final 변수를 의존관계를 자동으로 설정해 준다.
public class StimUserService implements UserDetailsService {
	
	private final StimUserMapper stimUserMapper;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional	// 트랜잭션 보장이 된 메소드로 설정 해준다.
    public void registerUser(UserVO uVo){	// 회원가입
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
	public Map<String, String> validateHandling(Errors errors) {
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
	public String findIdByEmail(String user_email) {
		String email = stimUserMapper.findIdByEmail(user_email);
		
		return email;
	}

	// 비밀번호 찾기
	public int findPwByUserId(String user_id, String user_email) {
		int result = stimUserMapper.findPwByUserId(user_id, user_email);
		
		return result;
	}

	// 비밀번호 찾기 => 새로운 비밀번호
	@Transactional
	public void changePwByUserId(String user_id, String password) {
		String user_password = encoder.encode(password);
		stimUserMapper.changePwByUserId(user_id, user_password);
	}

}
