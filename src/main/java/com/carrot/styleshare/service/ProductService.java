package com.carrot.styleshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	// 글쓰기
	public int write(String image, String title, String link, int lprice, int styleId, int userId) {
		try {
			return productRepository.write(image, title, link, lprice, styleId, userId);


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();

		}
	}
}
