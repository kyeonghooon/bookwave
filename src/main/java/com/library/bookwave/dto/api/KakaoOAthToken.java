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
// 카카오 api (응답 정보)
public class KakaoOAthToken {

	private String accessToken; // 사용자 액세스 토큰
	private Integer expiresIn; // 만료시간
	private String scope; // 권한 범위
	private String tokenType; // bearer로 고정
	private String idToken;
	private String refreshToken; // 사용자 리프레시 토큰
	
	
}
