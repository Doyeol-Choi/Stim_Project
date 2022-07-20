package com.stim.vo;

import lombok.Data;

@Data
public class CartVO {

	// 안쓸듯?3 UserDTO에 wishCode만 함쳐 쓰는게 나을지도
	private int code;
	private int userCode;
	private int gameCode;
	private String gameName;
	private int price;
	private int totalPrice;
	private String picture;
	
}
