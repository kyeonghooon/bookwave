package com.library.bookwave.dto.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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

// 구글 api (응답 정보)
public class GoogleOAuthToken {
	
	private String accessToken;
	private String expiresIn;
	private String scope;
	private String tokenType;
	private String idToken;

}
