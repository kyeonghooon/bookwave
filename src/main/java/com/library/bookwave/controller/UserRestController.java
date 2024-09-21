package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.bookwave.service.EmailService;
import com.library.bookwave.service.MemberService;
import com.library.bookwave.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/controller-user")
@RequiredArgsConstructor
public class UserRestController {

	private final UserService userService;
	private final EmailService emailService;
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	// ID 중복 확인
	@GetMapping("/check-userId")
	public boolean getUserId(@RequestParam(name = "loginId") String loginId) {
		// return userService.confirmUid(loginId);
		System.out.println(loginId);
		boolean isUse = userService.readUserId(loginId) == null ? true : false;
		return isUse;

	}

	// 데이터 전송.. email을 추출해서 서버에 데이터 전송해야 됨 -> 사용자의 ID을 찾는다
	@PostMapping("/find-id")
	public ResponseEntity<?> eFindByEmail(@RequestBody Map<String, String> request) {
		// 이메일 추출
		String email = request.get("email");
		// String loginId = request.get("loginId");

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

	@PostMapping("/find-pw")
	public ResponseEntity<?> eFindByIdAndEmail(@RequestBody Map<String, String> request) {
		// 이메일 추출
		String loginId = request.get("loginId");
		String email = request.get("email");

		Map<String, Object> response = new HashMap<>();

		// 유효성 검사
		if (loginId == null || loginId.isEmpty()) {
			response.put("success", false);
			response.put("message", "아이디를 입력해주세요.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (email == null || email.isEmpty()) {
			response.put("success", false);
			response.put("message", "이메일을 입력해주세요.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// 이메일로 사용자 조회
		if (userService.eFindByIdAndEmail(loginId, email) == 0) {
			response.put("success", false);
			response.put("message", "해당 이메일로 사용자를 찾을 수 없습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		// 랜덤 비밀번호 생성
		String newPassword = memberService.getRamdomPassword(10);
		System.out.println(newPassword);

		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(newPassword);

		// 비밀번호 업데이트
		if (userService.newPassword(loginId, encryptedPassword) == 0) {
			response.put("success", false);
			response.put("message", "비밀번호 발급 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		// 이메일 보내는 로직 필요
		if (emailService.sendFindLoginPwdEmail(email, newPassword)) {
			response.put("success", true);
			response.put("message", "사용자의 패스워드가 이메일로 발송되었습니다.");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "이메일 전송 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		// 성공 응답 반환
	}

}// end of class
