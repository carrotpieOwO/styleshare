package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.like.Likes;
import com.carrot.styleshare.model.like.dto.ReqLikeInfoDto;
import com.carrot.styleshare.model.like.dto.ReqLikeMeDto;

public interface LikesRepository {
	public int like(int styleId, int userId);
	public Likes findByUserIdAndstyleID(int styleId, int userId);
	public int dislike(int id);
	public int likeCount(int id);
	public List<ReqLikeInfoDto> likeList(int styleId);
	public List<ReqLikeMeDto> findByLikeMe(int userId);
}
