package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.comment.dto.ReqCommentMeDto;
import com.carrot.styleshare.model.comment.dto.ReqWriteDto;
import com.carrot.styleshare.model.comment.dto.RespDetailDto;

public interface CommentRepository {
	public int save(ReqWriteDto dto);
	public List<RespDetailDto> findByStyleId(int id);
	public RespDetailDto findById(int id);
	public int delete(int id);
	public List<ReqCommentMeDto> findByCommentMe(int userId);
}
