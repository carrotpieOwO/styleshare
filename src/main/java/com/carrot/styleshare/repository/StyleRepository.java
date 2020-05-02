package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.style.dto.ReqAllDto;
import com.carrot.styleshare.model.style.dto.ReqLikeRankingDto;
import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;

public interface StyleRepository {
	public int write(RespWriteDto dto);
	public  RespDetailDto findByStyleId(int id);
	public int update(String content, int id);
	public int delete(int id);
	public List<ReqLikeRankingDto> findAllRanking();
	public List<ReqAllDto> findAll();
	public List<ReqAllDto> scrollDown(int id1, int id2);
	public List<ReqLikeRankingDto> findCategoryRanking(String gender);
	public List<ReqAllDto> findCategory(String gender);
	public List<ReqAllDto> scrollDownCategory(int id1, int id2, String gender);
	public List<ReqLikeRankingDto> findDetailRanking(int userId);
	public List<ReqAllDto> findByFollow(int userId);
	public List<ReqAllDto> scrollDownFollow(int id1, int id2, int userId);
	public List<ReqAllDto> searchAll(String content, String tag, String keyword);

}
