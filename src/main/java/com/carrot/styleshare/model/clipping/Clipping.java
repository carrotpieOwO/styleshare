package com.carrot.styleshare.model.clipping;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Clipping {
	private int id;
	private int userId;
	private int styleId;
	private Timestamp createDate;
	
	@Builder
	public Clipping(int userId, int styleId, Timestamp createDate) {
		super();
		this.userId = userId;
		this.styleId = styleId;
		this.createDate = createDate;
	}

	
}
