package com.stim.vo;

import lombok.Data;

@Data
public class WishVO {
	// 안쓸듯?2 UserDTO에 wishCode만 함쳐 쓰는게 나을지도
	private int code;
	private int userCode;
	private int gameCode;
}
