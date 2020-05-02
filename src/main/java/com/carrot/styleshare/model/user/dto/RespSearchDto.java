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
public class RespSearchDto {
	private int id;
	private int count;
	private String username;
	private String profile;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private int follower;
}

