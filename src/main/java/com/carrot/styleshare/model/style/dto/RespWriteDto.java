package com.carrot.styleshare.model.style.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespWriteDto {
	private int id;
	private String image1;
	private String image2;
	private String image3;
	private String content;
	private int userId;
	private Timestamp createDate;
}
