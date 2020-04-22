package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.product.dto.ReqProductDto;

public interface ProductRepository {
	public int write(String image, String title, String link, int lprice, int styleId, int userId, String brand);
	public List<ReqProductDto> findById(int styleId);

}