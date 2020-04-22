package com.carrot.styleshare.model.comment.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqWriteDto {
	private int id; //key 값 반환을 위해
	private int userId;
	private int styleId;
	private String content;
	private  Timestamp createDate;
}
