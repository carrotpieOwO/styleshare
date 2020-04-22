package com.carrot.styleshare.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.model.user.dto.ReqEmailCheckDto;
import com.carrot.styleshare.model.user.dto.ReqIdCheckDto;
import com.carrot.styleshare.model.user.dto.ReqJoinDto;
import com.carrot.styleshare.model.user.dto.ReqPwdUpdateDto;
import com.carrot.styleshare.model.user.dto.ReqPwdCheckDto;
import com.carrot.styleshare.service.UserService;

@Controller
public class UserController {
	@Value("${file.path}")
	private String fileRealPath;
	
	@Autowired
	private UserService userService;

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
