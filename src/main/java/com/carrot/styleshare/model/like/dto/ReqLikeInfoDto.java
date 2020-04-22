package com.carrot.styleshare.model.like.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLikeInfoDto {
	private int id;
	private int userId;
	private String username;
	private String profile;
	private int styleId;
	private int followCount;
	private int followerCount;
	private boolean follow;
}
