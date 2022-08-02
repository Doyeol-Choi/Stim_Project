package com.stim.vo;

import java.util.Date;

import lombok.Data;

@Data
public class WishVO {
	//찜목록 VO
	private int wish_code ;
	private int user_code ;
	private int game_code ;
	private String game_name ;
	private String game_price;
	private String genre_1;
	private String genre_2;
	private String genre_3;
	private String game_picture;
	private Date game_releaseDate ;
	private String game_finalPrice;
	
}
