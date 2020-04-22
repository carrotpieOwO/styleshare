package com.carrot.styleshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.repository.LikesRepository;

@Service
public class LikeService {

	@Autowired
	private LikesRepository likesRepository;

	// 글쓰기
	public int write(int styleId, int userId) {
		
		try {
			return likesRepository.like(styleId,userId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();

		}
	}
	
	// 불러오기
//	public List<String> tags(int styleId){
//		return tagRepository.findById(styleId);
//	}
	
	
}