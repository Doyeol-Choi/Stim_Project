package com.stim.dto;

import lombok.Data;

@Data
public class CartDTO {

	// 안쓸듯?3 UserDTO에 wishCode만 함쳐 쓰는게 나을지도
	private int code;
	private int userCode;
	private int gameCode;
}
