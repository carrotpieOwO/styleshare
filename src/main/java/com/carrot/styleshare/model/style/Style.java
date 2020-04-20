package com.carrot.styleshare.model.style;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Style {
	private int id;
	private String image1;
	private String image2;
	private String image3;
	private String content;
	private int userId;
	private Timestamp createDate;
	private Boolean like;
	
	@Builder
	public Style(String image1, String image2, String image3, String content, 
			int userId, Timestamp createDate) {
		super();
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.content = content;
		this.userId = userId;
		this.createDate = createDate;
	}
}
