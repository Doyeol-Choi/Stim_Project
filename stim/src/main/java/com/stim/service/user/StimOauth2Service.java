package com.stim.service.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.StimUserMapper;
import com.stim.vo.UserVO;
import com.stim.vo.oauth2.GoogleUserVO;
import com.stim.vo.oauth2.KakaoUserVO;
import com.stim.vo.oauth2.NaverUserVO;
import com.stim.vo.oauth2.OAuth2UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StimOauth2Service extends DefaultOAuth2UserService {

	private final StimUserMapper stimUserMapper;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        OAuth2UserVO oAuth2UserVO = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();
        
        if(provider.equals("google")){
            oAuth2UserVO = new GoogleUserVO(oAuth2User.getAttributes());
        } else if(provider.equals("naver")){
            oAuth2UserVO = new NaverUserVO(oAuth2User.getAttributes());
        } else if(provider.equals("kakao")){
        	oAuth2UserVO = new KakaoUserVO(oAuth2User.getAttributes());
        }
        
        String providerId = oAuth2UserVO.getProviderId();
        String userId = provider+"_"+providerId;  			// ID를 임의로 조합하여 생성

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("pwd"+uuid);  // 비밀번호 조합

        String email = oAuth2UserVO.getEmail();

        UserVO uVo = stimUserMapper.getUserAccount(userId);
        
        //DB에 없는 사용자라면 회원가입처리
        if(uVo == null){
        	uVo = new UserVO();
        	uVo.setUser_id(userId);
        	uVo.setUser_password(password);
        	uVo.setUser_email(email);
        	uVo.setUser_phone("01012345678");
        	uVo.setUser_admin("N");
        	int idx = email.indexOf("@");
        	uVo.setUser_nickname(email.substring(0, idx));
        	stimUserMapper.registerUser(uVo);
        }

        return new UserVO(uVo, oAuth2UserVO);
    }
}
