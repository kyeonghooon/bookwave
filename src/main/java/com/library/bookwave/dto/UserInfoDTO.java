package com.library.bookwave.dto;

import java.sql.Date;

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

public class UserInfoDTO {
	
	private int userId;
	private String name;
	private String email;
	private Date birthDate;
	private String zip; // 주소 - 우편번호
	private String addr1; // 지번 주소
	private String addr2; // 상세 주소
	private String phone;
}
