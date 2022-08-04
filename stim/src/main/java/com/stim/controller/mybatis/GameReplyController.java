package com.stim.controller.mybatis;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stim.service.mybatis.GameReplyService;
import com.stim.vo.GameReplyVO;

@RestController
@RequestMapping("/reply")
public class GameReplyController {

	@Resource
	private GameReplyService gameReplyService;
	
	//댓글등록
	@PostMapping("/enroll")
	public void enrollerReplyPost(GameReplyVO rVo) throws Exception {
		gameReplyService.enrollReply(rVo);
	}
	
}
