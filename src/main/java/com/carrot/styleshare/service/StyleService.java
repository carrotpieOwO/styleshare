package com.carrot.styleshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;
import com.carrot.styleshare.repository.StyleRepository;

@Service
public class StyleService {
	@Autowired
	private StyleRepository styleRepository;

	// 글쓰기
	public int write(RespWriteDto dto) {
		return styleRepository.write(dto);
	}
	
	//상세보기
	public RespDetailDto detail(int id) {
		return styleRepository.findByStyleId(id);
	}
}
