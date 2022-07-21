package com.stim.vo;

import lombok.Data;

@Data
public class CartVO {

	// 안쓸듯?3 UserDTO에 wishCode만 함쳐 쓰는게 나을지도
	private int cart_code ;
	private int user_code ;
	private int game_code ;
	private String game_name ;
	private int game_price ;
	private int totalPrice;
	private String game_picture ;
	
}
