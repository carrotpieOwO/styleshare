package com.carrot.styleshare.model.style.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAllDto {
	private int rank;
	private int id;
	private int userId;
	private String image1;
	private String image2;
	private String username;
	private String profile;
	private int likeCount;
	private int clippingCount;
}
