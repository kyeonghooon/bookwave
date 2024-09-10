package com.library.bookwave.dto;

import com.library.bookwave.repository.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

// 로그인 DTO
public class SignInDTO {
	
	private String loginId; // 아이디
	private String password; // 비밀번호
	
	public User toUser() {
		return User.builder()
				   .loginId(this.loginId)
				   .password(this.password)
				   .build();
	}
	

}
