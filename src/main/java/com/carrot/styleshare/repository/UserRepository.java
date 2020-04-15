package com.carrot.styleshare.repository;

import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.model.user.dto.ReqJoinDto;

public interface UserRepository {
	User authentication(String username);
	int save(ReqJoinDto dto);
	int findByUsername(String username);
	int updateProfile(int id, String profile, String introduction, String email, int height, String gender, String age, String insta, String site);
	User findById(int id);
	int updatePassword(String password, String username);
}
