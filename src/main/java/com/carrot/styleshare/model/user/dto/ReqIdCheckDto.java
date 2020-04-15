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
public class ReqIdCheckDto {
	@Size(min=4, max=15, message="ID는 4~15자까지 입력 가능합니다.")
	@NotBlank(message="ID를 입력하세요")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문,숫자만 입력가능합니다.")
	private String username;	
}
