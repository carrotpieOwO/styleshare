package com.carrot.styleshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
