package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.tag.Tag;
import com.carrot.styleshare.model.tag.dto.ReqSearchTagDto;

public interface TagRepository {
	public int write(String tag, int styleId);
	public List<String> findById(int styleId);
	public int delete(int styleId);
	public List<ReqSearchTagDto> searchByTag(String tag);
	public List<Tag> selectDistinctTag(String tag);

}
