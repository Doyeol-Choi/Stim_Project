package com.stim.vo;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserVO implements UserDetails {

	private int user_code;
	private String user_id;
	private String user_password;
	private String user_email;
	private String user_phone;
	private String user_admin;
	private String user_picture;
	private String user_nickname;
	
	
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
}
