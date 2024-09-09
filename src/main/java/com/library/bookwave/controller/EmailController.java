package com.library.bookwave.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.bookwave.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;

	@PostMapping("/sendVerification")
	public String sendVerificationEmail(@RequestParam("email") String email) {
		// 토큰 생성
		String token = emailService.generateVerificationToken();
		
		// 이메일 전송
		emailService.sendVerificationEmail(email, token);
		
		return "인증 이메일이 전송되었습니다.";
	}
	
//	@GetMapping("validate")
//	public String velidateEmail(@RequestParam("token") String token) {
//		
//	}
}
