package com.stim.vo;

import lombok.Data;

@Data
public class UserVO {

	private int code;
	private String id;
	private String password;
	private String email;
	private String phone;
	private String admin;
}
