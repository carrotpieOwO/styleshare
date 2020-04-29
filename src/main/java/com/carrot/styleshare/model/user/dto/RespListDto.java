package com.carrot.styleshare.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespListDto {

	private int id;
	private String image1;
	private String image2;
	private int userId;
	private String username;
	private String profile;
	private String insta;
	private String site;
	private String introduction;
	private String gender;
	private int height;
	private String age;
	private int count;
	private int likeCount;
	private int clippingCount;
	private int myClippingCount;
	
}
