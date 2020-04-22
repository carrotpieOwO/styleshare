package com.carrot.styleshare.model.comment.dto;

import java.sql.Timestamp;

import com.carrot.styleshare.model.RespCM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespDetailDto {
	private RespCM status;
	private int id; //key 값 반환을 위해
	private int userId;
	private int reviewId;
	private String content;
	private String profile;
	private Timestamp createDate;
	private String username;
}
