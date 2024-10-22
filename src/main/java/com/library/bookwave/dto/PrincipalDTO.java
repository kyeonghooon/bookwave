package com.library.bookwave.dto;

import java.sql.Timestamp;

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
	private Boolean subscribe;
	private Timestamp endDate;

	public PrincipalDTO(User user) {
		this.userId = user.getId();
		this.loginId = user.getLoginId();
		this.socialId = user.getSocialId();
		this.password = user.getPassword();
		this.name = user.getName();
		this.role = user.getRole();
		this.status = user.getStatus();
		this.createdAt = user.getCreatedAt();
	}

}
