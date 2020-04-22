package com.carrot.styleshare.model.like.dto;

import com.carrot.styleshare.model.RespCM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLikeDto {
	private RespCM status;
	private int id;
	private int userId;
	private int styleId;

}
