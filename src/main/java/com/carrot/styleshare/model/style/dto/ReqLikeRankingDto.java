package com.carrot.styleshare.model.style.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLikeRankingDto {
	private int rank;
	private int count;
	private int id;
	private String image1;
	private String image2;
	private String username;
	private String profile;
	private int likeCount;
	private int clippingCount;
	
}
