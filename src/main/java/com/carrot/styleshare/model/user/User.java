package com.carrot.styleshare.model.user;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements UserDetails{
	private int id;
	private String username;
	private String password;
	private String email;
	private String profile;
	private String introduction;
	private String gender;
	private String age;
	private int height;
	private String insta;
	private String site;
	private Timestamp createDate;
	
	@Builder
	public User(String username, String password, String email, String profile, String introduction,
			String gender, String age, int height, String insta, String site, Timestamp createDate) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.profile = profile;
		this.introduction = introduction;
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.insta = insta;
		this.site = site;
		this.createDate = createDate;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
