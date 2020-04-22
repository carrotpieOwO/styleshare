package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.follow.Follow;
import com.carrot.styleshare.model.follow.dto.ReqFollowInfoDto;
import com.carrot.styleshare.model.follow.dto.ReqFollowMeDto;

public interface FollowRepository {
	public int follow(int fromUserId, int toUserId);
	public Follow findByFromUserAndToUser(int fromUserId, int toUserId);
	public int unfollow(int id);
	public List<ReqFollowInfoDto> followerInfo(int toUserId);
	public List<ReqFollowInfoDto> followInfo(int fromUserId);
	int followerCount(int toUserId);
	int followCount(int fromUserId);
	public List<ReqFollowMeDto> findByFollowMe(int userId);
}
