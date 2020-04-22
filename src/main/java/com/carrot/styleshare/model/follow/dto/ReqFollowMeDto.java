package com.carrot.styleshare.model.follow.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqFollowMeDto {
	private int id;
	private int fromUser;
	private int toUser;
	private String username;
	private String profile;
	private Timestamp createDate;
	private boolean follow;
}
