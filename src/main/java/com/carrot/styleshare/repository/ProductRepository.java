package com.carrot.styleshare.repository;

public interface ProductRepository {
	public int write(String image, String title, String link, int lprice, int styleId, int userId);

}
