package com.carrot.styleshare.model.comment;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {
	private int id;
	private int userId;
	private int styleId;
	private String content;
	private Timestamp createDate;
	
	@Builder
	public Comment(int userId, int styleId, String content, Timestamp createDate) {
		super();
		this.userId = userId;
		this.styleId = styleId;
		this.content = content;
		this.createDate = createDate;
	};
}
