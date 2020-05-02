package com.carrot.styleshare.model.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSearchKeywordDto {
	private int id;
	private String image1;
	private String image2;
	private String username;
	private String profile;
	private String keyword;
	private int likeCount;
	private int clippingCount;
}
