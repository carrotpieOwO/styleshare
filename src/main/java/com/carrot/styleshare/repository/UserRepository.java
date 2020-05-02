package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.model.user.dto.ReqJoinDto;
import com.carrot.styleshare.model.user.dto.RespListDto;
import com.carrot.styleshare.model.user.dto.RespSearchDto;
import com.carrot.styleshare.model.user.dto.RespStyleListDto;

public interface UserRepository {
	User authentication(String username);
	int save(ReqJoinDto dto);
	int findByUsername(String username);
	int updateProfile(int id, String profile, String introduction, String email, int height, String gender, String age, String insta, String site);
	User findById(int id);
	int updatePassword(String password, String username);
	List<RespListDto> mypageList(String username, int userId);
	List<RespListDto> scrollDownMypage(int id1, int id2, String username);
	List<RespSearchDto> searchByUsername(String username);
	List<RespStyleListDto> imageByUsername(int userId);
}
