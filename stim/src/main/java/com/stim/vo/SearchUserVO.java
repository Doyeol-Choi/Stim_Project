package com.stim.vo;

import lombok.Data;

@Data
public class SearchUserVO {
	
	private int user_code;
	private String user_nickname;
	private String user_picture;
	private String friend_accepted;
	
}
