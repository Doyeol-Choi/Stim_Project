package com.stim.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GameVO {

	private int game_code;
	private String game_name;
	private int game_price;
	private Date game_releaseDate;
	private String game_developer;
	private String game_distribution;
	private String game_context;
	private int game_salesRate;
	private String game_picture;
	private int game_discount;
	private String genre_1;
	private String genre_2;
	private String genre_3;
	
	private int game_finalPrice;
	
}
