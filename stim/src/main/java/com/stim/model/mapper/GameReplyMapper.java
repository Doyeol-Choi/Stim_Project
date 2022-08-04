package com.stim.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stim.paging.Criteria;
import com.stim.vo.GameReplyVO;

@Mapper
public interface GameReplyMapper {

	// 댓글 목록
	public List<GameReplyVO> getListPaging(Criteria cri) throws Exception;
	
	// 게임 평점 페이지 댓글 등록
	public int enrollReply(GameReplyVO rVo) throws Exception;
	
	
}
