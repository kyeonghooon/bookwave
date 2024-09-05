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
// 네이버 api (응답 정보)
public class NaverOAuthToken {

	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private Integer expiresIn;
	private String error; // 에러 코드
	private String errorDescription; // 에러 메세지
	
	
}
