package com.carrot.styleshare.model.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSearchTagDto {
	private int id;
	private String image1;
	private String location;
	private String category;
	private String username;
	private String profile;
	private String tag;
	private int likeCount;
	private int clippingCount;
}
