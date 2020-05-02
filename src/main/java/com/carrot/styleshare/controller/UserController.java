package com.carrot.styleshare.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.carrot.styleshare.model.RespCM;
import com.carrot.styleshare.model.ReturnCode;
import com.carrot.styleshare.model.comment.dto.ReqCommentMeDto;
import com.carrot.styleshare.model.follow.Follow;
import com.carrot.styleshare.model.follow.dto.ReqFollowInfoDto;
import com.carrot.styleshare.model.follow.dto.ReqFollowMeDto;
import com.carrot.styleshare.model.like.dto.ReqLikeMeDto;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.model.user.dto.ReqEmailCheckDto;
import com.carrot.styleshare.model.user.dto.ReqIdCheckDto;
import com.carrot.styleshare.model.user.dto.ReqJoinDto;
import com.carrot.styleshare.model.user.dto.ReqPwdCheckDto;
import com.carrot.styleshare.model.user.dto.ReqPwdUpdateDto;
import com.carrot.styleshare.model.user.dto.RespAlertDto;
import com.carrot.styleshare.model.user.dto.RespListDto;
import com.carrot.styleshare.repository.ClippingRepository;
import com.carrot.styleshare.repository.CommentRepository;
import com.carrot.styleshare.repository.FollowRepository;
import com.carrot.styleshare.repository.LikesRepository;
import com.carrot.styleshare.repository.UserRepository;
import com.carrot.styleshare.service.UserService;

@Controller
public class UserController {
	@Value("${file.path}")
	private String fileRealPath;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LikesRepository likesRepository;
	
	@Autowired
	private ClippingRepository clippingRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	//화면이동
	@GetMapping("user/join")
	public String join() {
		return "/user/join";
	}

	@GetMapping("user/login")
	public String login() {
		return "/user/login";
	}

	@GetMapping("user/profile/{id}")
	public String profile(@PathVariable int id, @AuthenticationPrincipal User principal) {
		if(principal.getId()==id) {
			return "/user/profile";
		}
		return "/user/login";
	}
	
	@GetMapping("user/password")
	public String password() {
		return "/user/password";
	}

	//마이페이지

	  @GetMapping("user/mypage/{username}")
		public String mypage(Model model, @PathVariable String username, @AuthenticationPrincipal User principal) {
		  User user = userRepository.authentication(username);

		  List<RespListDto> dtos = userService.mypageList(username, user.getId());
		  List<String> locations = new ArrayList<>();
		  ReqFollowInfoDto followInfo = new ReqFollowInfoDto();
		 		  
		  for(RespListDto dto : dtos) {
			  int likeCount = likesRepository.likeCount(dto.getId());
			  dto.setLikeCount(likeCount);
			  int myClippingCount = clippingRepository.myClippingCount(user.getId());
			  dto.setMyClippingCount(myClippingCount);
			  int clippingCount = clippingRepository.clippingCount(dto.getId());
			  dto.setClippingCount(clippingCount);
		  }
		  	if(principal!=null) {
		  		Follow follow = followRepository.findByFromUserAndToUser(principal.getId(),user.getId());
		  	
		  		if(follow != null) {
					  followInfo.setFromUser(principal.getId());
					  followInfo.setToUser(user.getId());
					  followInfo.setFollow(true);
					  
				  }
		  	}
			  
	  		int followCount = followRepository.followCount(user.getId());
			  int followerCount = followRepository.followerCount(user.getId());
			  followInfo.setFollowCount(followCount);
			  followInfo.setFollowerCount(followerCount);
			  
			System.out.println(followInfo);
		    System.out.println(dtos);
		  	model.addAttribute("user", user);
			model.addAttribute("styles", dtos);
			model.addAttribute("followInfo",followInfo);
			
			return "/user/mypage";
		}

	//무한스크롤
		@GetMapping("/mypage/scrollDown/{bno}/{username}")
		public @ResponseBody List<RespListDto> scrollDown(@PathVariable int bno, @PathVariable String username){
			//유저랭킹
			System.out.println(username);
			System.out.println("아이디값 "+bno);
			List<RespListDto> feeds = userService.scrollDownMypage(bno, bno, username);
			System.out.println(feeds);

			for(int i=0; i<feeds.size(); i++) {
			 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			  feeds.get(i).setLikeCount(likeCount);
			  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			  feeds.get(i).setClippingCount(clippingCount); 
			}
			
			return feeds;
		}
	
		//알림 모달
		  @GetMapping("/user/alert")
			public @ResponseBody List<RespAlertDto> alert(Model model, @AuthenticationPrincipal User principal) {
			  System.out.println("알림컨트롤러");
				List<ReqLikeMeDto> likeDtos = likesRepository.findByLikeMe(principal.getId());
				List<ReqFollowMeDto> followDtos = followRepository.findByFollowMe(principal.getId());
				List<ReqCommentMeDto> commentDtos = commentRepository.findByCommentMe(principal.getId());
				ArrayList<RespAlertDto> dtos = new ArrayList<RespAlertDto>();
				
		
				  for(ReqLikeMeDto likeDto : likeDtos) {
					  RespAlertDto dto = new RespAlertDto();
					  dto.setStyleId(likeDto.getStyleId());
					  dto.setUsername(likeDto.getUsername());
					  dto.setProfile(likeDto.getProfile());
					  dto.setImage1(likeDto.getImage1());
					  dto.setCreateDate(likeDto.getCreateDate());
					  dto.setLike(true);
					  dtos.add(dto);
				  }

				  for(ReqFollowMeDto followDto : followDtos) {
					  RespAlertDto dto = new RespAlertDto();
					  int userId = followDto.getFromUser();
					  Follow follow = followRepository.findByFromUserAndToUser(principal.getId(), userId);
					  dto.setFromUser(followDto.getFromUser());
					  dto.setUsername(followDto.getUsername());
					  dto.setProfile(followDto.getProfile());
					  dto.setCreateDate(followDto.getCreateDate());
					  dto.setFollowMe(true);
					  if(follow!=null) {
						  dto.setFollow(true);
					  }
					  dtos.add(dto);
				  
			  }		
				  
				  for(ReqCommentMeDto commentDto : commentDtos) {
					  RespAlertDto dto = new RespAlertDto();
					  dto.setStyleId(commentDto.getStyleId());
					  dto.setFromUser(commentDto.getFromUser());
					  dto.setToUser(commentDto.getToUser());
					  dto.setUsername(commentDto.getUsername());
					  dto.setProfile(commentDto.getProfile());
					  dto.setContent(commentDto.getContent());
					  dto.setImage1(commentDto.getImage1());
					  dto.setCreateDate(commentDto.getCreateDate());
					  dto.setCommentMe(true);
					  dtos.add(dto);
				  }
				  
			  	System.out.println(followDtos);
			  	System.out.println(likeDtos);
				System.out.println(dtos);
				
				Collections.sort(dtos, new Ascending());
				System.out.println("정렬후:" + dtos);
				
				return dtos;
			}
		  
		  class Ascending implements Comparator<RespAlertDto>{

			@Override
			public int compare(RespAlertDto a1, RespAlertDto a2) {
				// TODO Auto-generated method stub
				return a2.getCreateDate().compareTo(a1.getCreateDate());
			}
			  
		  }
	//ID 유효성 검사
		@PostMapping("user/username")
		public ResponseEntity<?> idCheck(@Valid @RequestBody ReqIdCheckDto dto, BindingResult bindingResult) {
			System.out.println(dto.getUsername());
			if(bindingResult.hasErrors()) {
				Map<String, String> errorMap = new HashMap<>();
				
				for(FieldError error:bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
				}
				System.out.println(errorMap);
				return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
			}
			
			int result = userService.idCheck(dto.getUsername());

			if(result == -2) {
				return new ResponseEntity<RespCM>(new RespCM(ReturnCode.아이디중복,"아이디중복"), HttpStatus.OK);
			}else if(result == 1) {
				return new ResponseEntity<RespCM>(new RespCM(200,"ok"), HttpStatus.OK);
			}else {
				return new ResponseEntity<RespCM>(new RespCM(500,"fail"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
		
	//email 유효성 검사
		@PostMapping("user/email")
		public ResponseEntity<?> emailCheck(@Valid @RequestBody ReqEmailCheckDto dto, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) {
				Map<String, String> errorMap = new HashMap<>();
				
				for(FieldError error:bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
				}
				System.out.println(errorMap);
				return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<RespCM>(new RespCM(200,"ok"), HttpStatus.OK);
			}
	}
	
	//password 유효성 검사
		@PostMapping("user/password")
		public ResponseEntity<?> passwordCheck(@Valid @RequestBody ReqPwdCheckDto dto, BindingResult bindingResult) {
			if(bindingResult.hasErrors()) {
				Map<String, String> errorMap = new HashMap<>();
				
				for(FieldError error:bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
				}
				System.out.println(errorMap);
				return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<RespCM>(new RespCM(200,"ok"), HttpStatus.OK);
			}
	}
	
	//회원가입
	@PostMapping("user/join")
	public ResponseEntity<?> join(@Valid @RequestBody ReqJoinDto dto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			System.out.println(errorMap);
			return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
		}
		
		int result = userService.join(dto);

		if(result == 1) {
			return new ResponseEntity<RespCM>(new RespCM(200,"ok"), HttpStatus.OK);
		}else {
			return new ResponseEntity<RespCM>(new RespCM(500,"fail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//비밀번호 변경 - password체크
		@PostMapping("user/password/certify")
		public ResponseEntity<?> pwdCertify(@RequestBody ReqPwdUpdateDto dto) {
			System.out.println(dto);
			int result = userService.pwdCertify(dto.getUsername(),dto.getPassword());
			if(result==1) {
				return new ResponseEntity<RespCM>(new RespCM(200,"ok"), HttpStatus.OK);

			}else {
				return new ResponseEntity<RespCM>(new RespCM(500,"fail"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//비밀번호 변경 - 업데이트
		@PutMapping("user/password")
		public ResponseEntity<?> passwordUpdate(@Valid @RequestBody ReqPwdUpdateDto dto, BindingResult bindingResult, @AuthenticationPrincipal User principal) {
			if(bindingResult.hasErrors()) {
				Map<String, String> errorMap = new HashMap<>();
				
				for(FieldError error:bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
				}
				System.out.println(errorMap);
				return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
			}else {
				int result = userService.updatePassword(dto, principal);
				
				if(result==1) {
					return new ResponseEntity<RespCM>(new RespCM(200,"ok"), HttpStatus.OK);
				}else {
					return new ResponseEntity<RespCM>(new RespCM(500,"fail"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
	}
		
	//프로필수정
		@PutMapping("user/profile")
		public @ResponseBody String profile
		(@RequestParam int id, @RequestParam MultipartFile profile, @RequestParam String deleteProfile, @RequestParam String introduction,
				@RequestParam String email, @RequestParam int height, @RequestParam String gender, @RequestParam String age,
				@RequestParam String insta, @RequestParam String site, @AuthenticationPrincipal User principal) {
			System.out.println(id);
			UUID uuid = UUID.randomUUID();
			String uuidFilename;
					
			if(profile.getOriginalFilename().equals("") && !deleteProfile.equals("true")) {
				uuidFilename= principal.getProfile();
			}else if(deleteProfile.equals("true")){
				uuidFilename="";
			}
			else{
				uuidFilename= uuid+"_"+profile.getOriginalFilename();
			}
			System.out.println(uuidFilename);
			
			if(uuidFilename != null && !uuidFilename.equals("") && !uuidFilename.equals(principal.getProfile())) {
				Path filePath = Paths.get(fileRealPath+uuidFilename);
				try {
					Files.write(filePath, profile.getBytes());
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			int result = userService.updateProfile(id, uuidFilename, introduction, email, height, gender, age, insta, site, principal);
			
			StringBuffer sb = new StringBuffer();
			if(result==1) {
				sb.append("<script>");
				sb.append("alert('수정완료');");
				sb.append("location.href='/';");
				sb.append("</script>");
				return sb.toString();
				
			}else {
				sb.append("<script>");
				sb.append("alert('수정실패');");
				sb.append("history.back();");
				sb.append("</script>");
				return sb.toString();
			}		

		}
	

}
