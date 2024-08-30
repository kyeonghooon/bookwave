package com.library.bookwave.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

// 에러 발생시 페이지로 이동

@Getter
public class RedirectException extends RuntimeException {

	private HttpStatus status;
	
	public RedirectException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
}
