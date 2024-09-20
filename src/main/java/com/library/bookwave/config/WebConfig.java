package com.library.bookwave.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.library.bookwave.handler.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*") // 모든 출처 허용 (필요에 따라 특정 도메인으로 제한 가능)
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*") // 모든 헤더 허용
			.allowCredentials(true) // 쿠키를 허용하려면 true로 설정
			.maxAge(3600); // preflight 요청의 캐시 기간 (초 단위)
	}

	//
	@Bean
	 PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludeList = new ArrayList<>();
		
		// 제외 url 지정
		excludeList.add("/"); // 메인페이지
		
		// 정적 리소스 경로 제외
		excludeList.add("/css/**");
		excludeList.add("/js/**");
		excludeList.add("/ebooks/**");
		excludeList.add("/images/**");
		excludeList.add("/img/**");
		excludeList.add("/scss/**");
		excludeList.add("/vendor/**");
		
		// 로그인 안해도 이용할 수 있는 페이지
		excludeList.add("/user/**");
		excludeList.add("/book/list");
		
		excludeList.add("/email/sendVerification"); // 회원가입시 이메일 인증용
		excludeList.add("/email/validate"); // 회원가입시 이메일 인증용
		
		registry.addInterceptor(authInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns(excludeList);
	}
	
}
