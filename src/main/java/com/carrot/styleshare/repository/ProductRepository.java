package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.product.dto.ReqProductDto;
import com.carrot.styleshare.model.product.dto.ReqSearchKeywordDto;

public interface ProductRepository {
	public int write(String image, String title, String link, int lprice, int styleId, int userId, String keyword, String productId);
	public List<ReqProductDto> findById(int styleId);
	public int delete(int styleId);
	public List<ReqSearchKeywordDto> searchByKeyword(String keyword);

}
