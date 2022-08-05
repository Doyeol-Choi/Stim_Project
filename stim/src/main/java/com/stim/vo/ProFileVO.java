package com.stim.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	//보유 게임 목록
	//private int game_code;
	private String game_name;
	private String game_picture;
	
	//친구 목록
	private int friend_code;
	private String friend_picture;
	private String friend_nickname;
	
	//친구 요청
	private int requester;
	private String req_nickname;
	private String req_picture;
	
	private char friend_accepted;
	
	//할 말
	private String profile_context;
	
	//Lombok은 파라미터 다 채운 생성자만 만들어 줌
	public ProFileVO() {
	}
	
}
