package com.carrot.styleshare.model.style.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.carrot.styleshare.model.product.dto.ReqProductDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdateDto {
	private int id;
	private String content;
	private ArrayList<ReqProductDto> products;
	private Timestamp createDate;
	private String tag;
	
}
