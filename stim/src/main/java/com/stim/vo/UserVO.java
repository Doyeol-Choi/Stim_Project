package com.stim.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.stim.vo.oauth2.OAuth2UserVO;

import lombok.Data;

@Data
public class UserVO implements UserDetails, OAuth2User {

	private int user_code;
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String user_id;
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-z0-9-_]{4,12}$", message = "비밀번호는 4~12자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String user_password;
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String user_email;
	@NotBlank(message = "전화번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{7}|\\d{8})$", message = "전화번호는 -을 제외한 10~11자리를 입력해주세요")
	private String user_phone;
	private String user_admin;
	private String user_picture;
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
	private String user_nickname;
	
	private OAuth2UserVO oAuth2UserVO;
	
	//OAuth2User : OAuth2 로그인 시 사용
    public UserVO(UserVO uVo, OAuth2UserVO oAuth2UserVO) {
        this.user_code = uVo.getUser_code();
		this.user_id = uVo.getUser_id();
		this.user_password = uVo.getUser_password();
		this.user_email = uVo.getUser_email();
		this.user_phone = uVo.getUser_phone();
		this.user_admin = uVo.getUser_admin();
		this.user_picture = uVo.getUser_picture();
		this.user_nickname = uVo.getUser_nickname();
        this.oAuth2UserVO = oAuth2UserVO;
    }
	
	public UserVO() {}

	// 해당 유저의 권한목록 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(this.user_admin));
	}

	@Override
	public String getPassword() {
		return this.user_password;
	}

	@Override
	public String getUsername() {
		return this.user_id;
	}

	// 계정 만료 여부 => true : 만료안됨
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠김 여부 => true : 잠기지 않음
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정 비밀번호 만료 여부 => true : 만료 안됨
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화 여부 =>true : 활성화됨
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2UserVO.getAttributes();
	}

	@Override
	public String getName() {
		return oAuth2UserVO.getProviderId();
	}

}
