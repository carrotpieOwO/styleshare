package com.carrot.styleshare.model.style.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespDetailDto {
	private int id;
	private String image1;
	private String image2;
	private String image3;
	private String content;
	private int userId;
	private String username;
	private String profile;
	private int height;
	private String gender;
	private String age;
	private Timestamp createDate;
	private boolean like;
	private int likeCount;
	private boolean clipping;
	private int clippingCount;
	private boolean follow;
}
