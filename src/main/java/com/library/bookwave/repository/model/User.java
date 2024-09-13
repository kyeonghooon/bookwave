package com.library.bookwave.repository.model;

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
public class User {
	private Integer id;
	private String loginId;
	private String socialId;
	private String password;
	private String name;
	private String role;
	private Integer status;
	private Timestamp createdAt;
}
