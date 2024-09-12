package com.library.bookwave.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrincipalDTO {
	
	private Integer userId;
	private String loginId;
	private String socialId;
	private String password;
	private String name;
	private String role;
	private Integer status;
	private Timestamp createdAt;
	private Integer wave;
	private Integer mileage;
	
}
