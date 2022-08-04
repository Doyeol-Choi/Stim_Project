package com.stim.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stim.model.mapper.GameReplyMapper;
import com.stim.paging.Criteria;
import com.stim.vo.GameReplyVO;

@Service
public class GameReplyServiceImp implements GameReplyService {

	@Autowired
	GameReplyMapper gameReplyMapper;
	
	@Override
	public int enrollReply(GameReplyVO rVo) throws Exception {
		int result = gameReplyMapper.enrollReply(rVo);
		return result;
	}

	@Override
	public GameReplyVO replyList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
