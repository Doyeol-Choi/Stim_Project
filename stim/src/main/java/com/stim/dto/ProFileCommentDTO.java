package com.stim.dto;

import lombok.Data;

@Data
public class ProFileCommentDTO {
	// 수정 필요할듯 regDate는 필요에 따라 타입 변경
	private int code;
	private int usercode;
	private String writer;
	private String content;
	private String regDate;
	
}
