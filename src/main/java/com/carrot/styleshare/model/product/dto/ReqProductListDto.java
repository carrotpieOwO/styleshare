package com.carrot.styleshare.model.product.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqProductListDto {
	private String lastBuildDate;
	private int total;
	private int start;
	private int display; 
	private ArrayList<ReqProductDto> items;
	
}
