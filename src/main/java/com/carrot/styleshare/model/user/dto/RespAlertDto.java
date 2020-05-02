package com.carrot.styleshare.model.user.dto;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespAlertDto {
	private int fromUser;
	private int toUser;
	private String username;
	private String profile;
	private String content;
	private int styleId;
	private Timestamp createDate;
	private boolean follow;
	private String image1;
	private boolean like;
	private boolean followMe;
	private boolean commentMe;

	

}

