package com.carrot.styleshare.model.tag;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Tag {
	private int id;
	private String tag;
	private int styleId;
	private Timestamp createDate;
	
	@Builder
	public Tag(String tag, int userId, int styleId, Timestamp createDate) {
		super();
		this.tag = tag;
		this.styleId = styleId;
		this.createDate = createDate;
	}

	
}
