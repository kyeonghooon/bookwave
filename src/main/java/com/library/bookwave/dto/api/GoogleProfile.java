package com.library.bookwave.dto.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(value=PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoogleProfile {
	
	private String id;
	private String email;
	private String verifiedEmail;
	private String name;
	private String givenName;
	private String familyName;
	private String picture;
	private String locale;

	public User googleUser() {
		return User.builder()
				.socialId(id)
				.build();
	}
	
}

