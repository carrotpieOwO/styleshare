package com.carrot.styleshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.model.product.dto.ReqProductDto;
import com.carrot.styleshare.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	// 글쓰기
	public int write(String image, String title, String link, int lprice, int styleId, int userId, String brand, String productId) {
		try {
			return productRepository.write(image, title, link, lprice, styleId, userId, brand, productId);


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();

		}
	}
	
	//불러오기
	public List<ReqProductDto> products(int styleId){
		return productRepository.findById(styleId);
	}
	
	//삭제
	public int delete(int styleId) {
		return productRepository.delete(styleId);
	}

}
