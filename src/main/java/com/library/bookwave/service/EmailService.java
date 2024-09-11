package com.library.bookwave.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	// 이메일 인증 토큰 전송 메서드
	public void sendVerificationEmail(String email, String token) {
		// TODO 현재 도메인 없어서 localhost:8080으로 대체
		String domain = "localhost:8080";
		String emailContent = loadEmailTemplate(token, domain);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setTo(email);
			helper.setSubject("이메일 인증 요청");
			helper.setText(emailContent, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 토큰을 서버에 저장하고 5분 후 자동 삭제
		storeToken(token, email);
	}

	public void storeToken(String token, String email) {
		tokenStore.put(token, email);
		scheduler.schedule(() -> tokenStore.remove(token), 5, TimeUnit.MINUTES);
	}

	// 토큰 생성 로직
	public String generateVerificationToken() {
		return UUID.randomUUID().toString();
	}
	
	// 토큰 확인 로직
	public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

	// JSP 템플릿을 로드하여 문자열로 변환하는 메서드
	private String loadEmailTemplate(String token, String domain) {
		try {
			ClassPathResource resource = new ClassPathResource("template/emailVerification.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			return reader.lines().collect(Collectors.joining()).replace("${token}", token).replace("${domain}", domain);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
