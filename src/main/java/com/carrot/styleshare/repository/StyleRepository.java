package com.carrot.styleshare.repository;

import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;

public interface StyleRepository {
	public int write(RespWriteDto dto);
	public  RespDetailDto findByStyleId(int id);

}
