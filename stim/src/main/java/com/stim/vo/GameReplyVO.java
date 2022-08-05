package com.stim.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GameReplyVO {
   
   private int grade_code;
   private int user_code;
   private String user_nickname;
   private int game_code;
   private String grade_context;
   private Date grade_regdate;
   private String grade_rate;
   private String user_picture;
   
}