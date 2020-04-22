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
import com.carrot.styleshare.model.like.Likes;
import com.carrot.styleshare.model.like.dto.ReqLikeDto;
import com.carrot.styleshare.model.like.dto.ReqLikeInfoDto;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.repository.FollowRepository;
import com.carrot.styleshare.repository.LikesRepository;

@RestController
public class LikesController {
	
	@Autowired
	private LikesRepository likesRepository;
	
	
	@Autowired 
	private FollowRepository followRepository;
	 
	
	@GetMapping("/likeInfo/{styleId}")
	public @ResponseBody List<ReqLikeInfoDto> likeInfo(Model model, @PathVariable int styleId, @AuthenticationPrincipal User principal) {
		List<ReqLikeInfoDto> dtos = likesRepository.likeList(styleId);
		System.out.println("프린시펄"+principal);
		
		if(principal != null) {
			for(ReqLikeInfoDto dto : dtos) {
				Follow follow = followRepository.findByFromUserAndToUser(principal.getId(), dto.getUserId());
				if(follow!=null) {
					dto.setFollow(true);
				}
				int followCount = followRepository.followCount(dto.getUserId());
				dto.setFollowCount(followCount);
				int followerCount = followRepository.followerCount(dto.getUserId());
				dto.setFollowerCount(followerCount);
			}
		}else {
			System.out.println("널로들어옴?");
			for(ReqLikeInfoDto dto : dtos) {
			int followCount = followRepository.followCount(dto.getUserId());
			dto.setFollowCount(followCount);
			int followerCount = followRepository.followerCount(dto.getUserId());
			dto.setFollowerCount(followerCount);
		}
		}
		
		System.out.println("라이크인포디티오"+dtos);
		return dtos;
	}
	
	@PostMapping("/like/{styleId}")
	public @ResponseBody String like(@RequestBody ReqLikeDto dto) {
		int styleId = dto.getStyleId();
		int userId = dto.getUserId();
		System.out.println(dto);
		Likes oldLike = likesRepository.findByUserIdAndstyleID(styleId, userId);
		System.out.println(oldLike);
		
		try {
			if(oldLike==null) {
				likesRepository.like(styleId, userId);
			}else {
				likesRepository.dislike(oldLike.getId());
			}
				return "ok";
		}catch (Exception e){
			e.printStackTrace();
		}
		return "fail";
	}
	
	//리턴타입 불리언으로 바꿔서 해보기
}
