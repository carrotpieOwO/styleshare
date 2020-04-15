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
public class ReqEmailCheckDto {
	@Size(min=5, max=30, message="이메일 길이가 잘못되었습니다.")
	@Email(message="이메일 양식을 확인하세요.") //이메일 양식에 안맞으면 튕겨나감
	@NotBlank(message="이메일을 입력하세요.")
	private String email;	
}
