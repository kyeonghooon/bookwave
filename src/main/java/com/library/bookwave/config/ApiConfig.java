package com.library.bookwave.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ApiConfig {
	@Value("${payment.clientKey}")
	private String clientKey;
	@Value("${payment.secretKey}")
	private String secretKey;
	@Value("${payment.IdempotencyKey")
	private String IdempotencyKey;
}
