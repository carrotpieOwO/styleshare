package com.carrot.styleshare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.carrot.styleshare.model.follow.Follow;
import com.carrot.styleshare.model.follow.dto.ReqFollowDto;
import com.carrot.styleshare.model.follow.dto.ReqFollowInfoDto;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.repository.FollowRepository;

@RestController
public class FollowController {
	@Autowired
	private FollowRepository followRepository;
	
	@GetMapping("/followInfo/{fromUser}")
	public @ResponseBody List<ReqFollowInfoDto> followInfo(Model model, @PathVariable int fromUser, @AuthenticationPrincipal User principal) {
		List<ReqFollowInfoDto> dtos = followRepository.followInfo(fromUser);
		System.out.println("팔로우principal="+principal);
		if(principal != null) {
			for(ReqFollowInfoDto dto : dtos) {
				dto.setFromUser(fromUser);
				Follow follow = followRepository.findByFromUserAndToUser(principal.getId(), dto.getToUser());
				if(follow!=null) {
					dto.setFollow(true);
				}
				int followCount = followRepository.followCount(dto.getToUser());
				dto.setFollowCount(followCount);
				int followerCount = followRepository.followerCount(dto.getToUser());
				dto.setFollowerCount(followerCount);
				
			}
			
		}else {
			System.out.println("principal=null");
			for(ReqFollowInfoDto dto : dtos) {
				int followCount = followRepository.followCount(dto.getToUser());
				dto.setFollowCount(followCount);
				int followerCount = followRepository.followerCount(dto.getToUser());
				dto.setFollowerCount(followerCount);
			}
		}
		
		System.out.println(dtos);
		return dtos;
	}
	
	@GetMapping("/followerInfo/{toUser}")
	public @ResponseBody List<ReqFollowInfoDto> followerInfo(Model model, @PathVariable int toUser, @AuthenticationPrincipal User principal) {
		List<ReqFollowInfoDto> dtos = followRepository.followerInfo(toUser);
		
		if(principal != null) {
			for(ReqFollowInfoDto dto : dtos) {
				dto.setToUser(toUser);
				System.out.println(dto);
				Follow follow = followRepository.findByFromUserAndToUser(principal.getId(), dto.getFromUser());
				if(follow!=null) {
					dto.setFollow(true);
				}
				int followCount = followRepository.followCount(dto.getFromUser());
				dto.setFollowCount(followCount);
				int followerCount = followRepository.followerCount(dto.getFromUser());
				dto.setFollowerCount(followerCount);
			}
		}else {
			System.out.println("principal=null");
			for(ReqFollowInfoDto dto : dtos) {
				int followCount = followRepository.followCount(dto.getFromUser());
				dto.setFollowCount(followCount);
				int followerCount = followRepository.followerCount(dto.getFromUser());
				dto.setFollowerCount(followerCount);
			}

		}
		
		
		
		return dtos;
	}
	
	@PostMapping("/follow")
	public @ResponseBody String follow(@RequestBody ReqFollowDto dto, @AuthenticationPrincipal User principal) {
		System.out.println(dto);
		int fromUserId = principal.getId();
		int toUserId = dto.getToUser();
		Follow oldFollow = followRepository.findByFromUserAndToUser(fromUserId, toUserId);
		System.out.println(oldFollow);
		try {
			if(oldFollow==null) {
				followRepository.follow(fromUserId, toUserId);
			}else {
				followRepository.unfollow(oldFollow.getId());
			}
			return "ok";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
}
