package com.stim.service.mybatis;

import com.stim.paging.Criteria;
import com.stim.vo.GameReplyVO;

public interface GameReplyService {

	// 게임 평점 페이지 댓글 등록
	public int enrollReply(GameReplyVO rVo) throws Exception;
		
	//댓글 페이징
	public GameReplyVO replyList(Criteria cri) throws Exception;
	
}
