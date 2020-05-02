package com.carrot.styleshare.model.product.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqProductDto {
	private String image;
	private String title;
	private String link;
	private int lprice;
	private String keyword;
	private String productId;
	
}
