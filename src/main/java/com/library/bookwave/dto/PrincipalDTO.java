package com.library.bookwave.dto;

import java.sql.Timestamp;

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
	private String subscribe;
}
