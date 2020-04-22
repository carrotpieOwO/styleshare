package com.carrot.styleshare.model.follow;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Follow {
	private int id;
	private int formUser;
	private int toUser;
	private Timestamp createDate;
	
	@Builder
	public Follow(int formUser, int toUser, Timestamp createDate) {
		super();
		this.formUser = formUser;
		this.toUser = toUser;
		this.createDate = createDate;
	}
	
	
}
