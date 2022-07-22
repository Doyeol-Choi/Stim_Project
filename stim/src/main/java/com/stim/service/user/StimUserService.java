package com.stim.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Override
	public UserVO loadUserByUsername(String user_id) throws UsernameNotFoundException {
		//여기서 받은 유저 패스워드와 비교하여 유저 정보 받기
		UserVO userVo = stimUserMapper.getUserAccount(user_id);
		
		if (userVo == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
		
		return userVo;
	}

}
