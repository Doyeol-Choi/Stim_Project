package com.stim.dto;

import lombok.Data;

@Data
public class UserDTO {

	private int code;
	private String id;
	private String password;
	private String email;
	private String phone;
	private String admin;
}
