package com.stim.vo;

import lombok.Data;

@Data
public class UserVO {

//	private int code;
//	private String id;
//	private String password;
//	private String email;
//	private String phone;
//	private String admin;
//	
	private int user_code;
	private String user_id;
	private String user_nickname;
	private String user_password;
	private String user_email;
	private String user_phone;
	private String user_picture;
	private char user_admin;
}
