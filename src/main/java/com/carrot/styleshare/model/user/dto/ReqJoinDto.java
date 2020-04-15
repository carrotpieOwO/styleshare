package com.carrot.styleshare.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqJoinDto {
	@Size(min=4, max=15, message="ID는 4~15자까지 입력 가능합니다.")
	@NotBlank(message="ID를 입력하세요")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문,숫자만 입력가능합니다.")
	private String username;
	
	@Size(min=4, max=15, message="패스워드는 4~15자까지 입력 가능합니다.")
	@NotBlank(message="패스워드를 입력하세요.")
	private String password;
	
	@Size(min=5, max=30, message="이메일 길이가 잘못되었습니다.")
	@Email(message="이메일 양식을 확인하세요.") //이메일 양식에 안맞으면 튕겨나감
	@NotBlank(message="이메일을 입력하세요.")
	private String email;
	
}
