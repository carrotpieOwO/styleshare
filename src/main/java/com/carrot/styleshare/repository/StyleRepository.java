package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.style.dto.ReqAllDto;
import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;

public interface StyleRepository {
	public int write(RespWriteDto dto);
	public  RespDetailDto findByStyleId(int id);
	public int update(String content, int id);
	public int delete(int id);
	public List<ReqAllDto> findAll();
	public List<ReqAllDto> scrollDown(int id1, int id2);
}
