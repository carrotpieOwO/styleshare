package com.carrot.styleshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.model.product.Product;
import com.carrot.styleshare.model.product.dto.ReqProductDto;
import com.carrot.styleshare.model.product.dto.ReqSearchKeywordDto;
import com.carrot.styleshare.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	// 글쓰기
	public int write(String image, String title, String link, int lprice, int styleId, int userId, String keyword, String productId) {
		try {
			return productRepository.write(image, title, link, lprice, styleId, userId, keyword, productId);


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
	
	//키워드 검색
	public List<ReqSearchKeywordDto> searchByKeyword(String keyword){
		return productRepository.searchByKeyword(keyword);	
	}
	
	//중복제거
	public List<Product> selectDistinctProduct(String keyword){
		return productRepository.selectDistinctProduct(keyword);
	}
}
