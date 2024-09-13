package com.library.bookwave.dto;

import java.sql.Date;
import java.util.Calendar;

import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;

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

// 회원가입 DTO
public class SignUpDTO {
	
	private String loginId; // 아이디
	private String password;
	private String name;
	
	private String socialId; // 소셜 ID
	
	private String email; // 이메일
	private String email1; 
	private String email2; 
	
	private Integer year; // 생년월일 - 년
	private Integer month; // 생년월일 - 월
	private Integer day; // 생년월일 - 일
	
	private Boolean gender; // 성별 // 대문자, 소문자 구분!
	
	private String phone; // 전화번호
	private String phone1; 
	private String phone2; 
	private String phone3; 
	
	private String zip; // 우편번호
	private String addr1; // 주소(시/도)
	private String addr2; // 주소(상세주소)
	


	
	public User toUser() {
		return User.builder()
				   .loginId(this.loginId)
				   .password(this.password)
				   .name(this.name)
				   .socialId(this.socialId)
				   .build();
	}
	
	public UserDetail detailUser() {
		return UserDetail.builder()
				  		 .email( this.email1 + "@" +this.email2  )
				  		 .birthDate(Date.valueOf(year + "-" + month + "-" + day))
				  		 .gender(this.gender)
				  		 .phone(this.phone1+"-"+this.phone2 + "-" + this.phone3)
				  		 .zip(this.zip)
				  		 .addr1(this.addr1)
				  		 .addr2(this.addr2)
				  		 .build();
				  		 
	}
	
	

}
