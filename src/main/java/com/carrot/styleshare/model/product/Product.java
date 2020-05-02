package com.carrot.styleshare.model.product;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	private int id;
	private String image;
	private String link;
	private String title;
	private int lprice;
	private int userId;
	private int styleId;
	private String keyword;
	private Timestamp createDate;
	
	@Builder
	public Product(String image, String link, String title, int lprice, 
			int userId, int styleId, String keyword, Timestamp createDate) {
		super();
		this.image = image;
		this.link = link;
		this.title = title;
		this.lprice = lprice;
		this.userId = userId;
		this.styleId = styleId;
		this.keyword = keyword;
		this.createDate = createDate;
	}
}
