package com.carrot.styleshare.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespSearchDto {
	private int id;
	private int count;
	private String username;
	private String profile;
	private String introduction;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private int follower;
}

