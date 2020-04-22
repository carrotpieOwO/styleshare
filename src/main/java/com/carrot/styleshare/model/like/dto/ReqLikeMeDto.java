package com.carrot.styleshare.model.like.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLikeMeDto {
	private int id;
	private int styleId;
	private String username;
	private String profile;
	private String image1;
	private Timestamp createDate;
}
