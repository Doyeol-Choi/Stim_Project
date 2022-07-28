package com.stim.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProFileVO {
	// 수정 필요할듯 regDate는 필요에 따라 타입 변경
	private int comment_code;
	private int user_code;
	private String comment_nickname;
	private String comment_context;
	private Date profile_regdate;
	private int writer_code;
	private String user_picture;
	
	public ProFileVO() {
	}
	
}
