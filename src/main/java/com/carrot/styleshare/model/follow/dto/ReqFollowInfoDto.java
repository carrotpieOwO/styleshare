package com.carrot.styleshare.model.follow.dto;

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
public class ReqFollowInfoDto {
	private int fromUser;
	private int toUser;
	private String username;
	private String profile;
	private String introduction;
	private boolean follow;
	private int followCount;
	private int followerCount;

}
