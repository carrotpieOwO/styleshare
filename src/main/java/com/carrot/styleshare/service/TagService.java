package com.carrot.styleshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.model.tag.Tag;
import com.carrot.styleshare.model.tag.dto.ReqSearchTagDto;
import com.carrot.styleshare.repository.TagRepository;

@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;

	// 글쓰기
	public int write(String tag, int styleId) {
		try {
			System.out.println("썼냐");
			return tagRepository.write(tag, styleId);


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();

		}
	}
	
	//불러오기
	public List<String> tags(int styleId){
		return tagRepository.findById(styleId);
	}
	
	//태그 삭제
	public int delete(int styleId) {
		return tagRepository.delete(styleId);
	}

	//태그 검색
	public List<ReqSearchTagDto> searchByTag(String tag){
		return tagRepository.searchByTag(tag);
	}
	
	public List<Tag> selectDistinctTag(String tag){
		return tagRepository.selectDistinctTag(tag);
	}
}
