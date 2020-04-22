package com.carrot.styleshare.model.clipping.dto;

import com.carrot.styleshare.model.RespCM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqClipDto {
	private RespCM status;
	private int id;
	private int userId;
	private int styleId;
}
