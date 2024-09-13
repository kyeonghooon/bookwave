package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.bookwave.service.EmailService;
import com.library.bookwave.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/controller-user")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserService userService;
	private final EmailService emailService;

	// 주소
	// http://localhost:8080/controller-user/check-userId
	// ID 중복 확인
	@GetMapping("/check-userId")
	public boolean getUserId(@RequestParam(name = "loginId") String loginId) {
		// return userService.confirmUid(loginId);
		System.out.println(loginId);
		boolean isUse = userService.readUserId(loginId) == null ? true : false;
		return isUse;

	}

	// TODO 이메일
	// 데이터 전송.. email을 추출해서 서버에 데이터 전송해야 됨 -> 사용자의 ID을 찾는다
	@PostMapping("/find-id")
	public ResponseEntity<Map<String, Object>> eFindByEmail(@RequestBody Map<String, String> request) {
		// 이메일 추출
		String email = request.get("email");
		// String loginId = request.get("loginId");
		System.out.println("받은 이메일: " + email);

		Map<String, Object> response = new HashMap<>();

		// 이메일 유효성 검사
		if (email == null || email.isEmpty()) {
			response.put("success", false);
			response.put("message", "이메일을 입력해주세요.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// 이메일로 사용자 조회
		String loginId = userService.eFindByEmail(email);

		// 사용자를 찾지 못한 경우
		if (loginId == null) {
			response.put("success", false);
			response.put("message", "해당 이메일로 사용자를 찾을 수 없습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		// 이메일 보내는 로직 필요
		if (emailService.sendFindLoginIdEmail(email, loginId)) {
			response.put("success", true);
			response.put("message", "사용자의 ID가 이메일로 발송되었습니다.");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "이메일 전송 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		// 성공 응답 반환
	}

}// end of class
