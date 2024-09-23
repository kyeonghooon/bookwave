package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.PaymentHistroyDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.UserInfoDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.Wallet;
import com.library.bookwave.service.EmailService;
import com.library.bookwave.service.MyPageService;
import com.library.bookwave.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user-info")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;
	private final EmailService emailService;
	private final HttpSession httpSession;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/change-email-verification")
	public ResponseEntity<Map<String, Object>> changeEmailVerification(@RequestBody Map<String, String> request) {
		// 이메일 추출
		String email = request.get("email");

		Map<String, Object> response = new HashMap<>();

		// 이메일 유효성 검사
		if (email == null || email.isEmpty()) {
			response.put("success", false);
			response.put("message", "이메일을 입력해주세요.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// 인증 토큰 생성
		String token = emailService.generateVerificationToken();

		// 이메일 인증 메일 전송
		emailService.sendVerificationEmail(email, token);

		// 성공 응답 반환
		response.put("success", true);
		response.put("message", "인증 이메일이 발송되었습니다.");
		return ResponseEntity.ok(response);

	}

	@PostMapping("/change-email")
	public ResponseEntity<Map<String, Object>> changeEmail(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @RequestBody Map<String, String> request) {
		// 이메일 추출
		String email = request.get("email");

		int userId = principal.getUserId();

		Map<String, Object> response = new HashMap<>();

		// 이메일 유효성 검사
		if (email == null || email.isEmpty()) {
			response.put("success", false);
			response.put("message", "이메일을 입력해주세요.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		if (myPageService.updateUserEmailByUserId(email, userId)) {
			response.put("success", true);
			response.put("message", "이메일 변경 성공");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response.put("success", false);
			response.put("message", "서버 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

	}

	/**
	 * 개인정보 수정 비밀번호 확인 페이지 이동
	 */
	@GetMapping("/mypageAuth")
	public String myPageAuth(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, Model model) {
		int userId = principal.getUserId();

		User user = myPageService.findUserById(userId);

		model.addAttribute("user", user);

		return "/myPage/myPageAuth";
	}

	/**
	 * 개인정보 수정 비밀번호 확인 기능
	 */
	@PostMapping("/mypageAuth")
	public String myPageAuthpost(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @RequestParam("password") String password) {

		int userId = principal.getUserId();
		User user = myPageService.findUserById(userId);

		if (passwordEncoder.matches(password, user.getPassword())) {
			return "redirect:/user-info/mypage";
		} else {
			throw new DataDeliveryException("비밀번호가 일치하지않습니다.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 개인정보 수정 페이지 이동
	 * 
	 */
	@GetMapping("/mypage")
	public String myPage(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, Model model) {

		int userId = principal.getUserId();
		UserInfoDTO user = myPageService.findUserDetailById(userId);

		model.addAttribute("user", user);
		return "/myPage/myPage";
	}

	/**
	 * 주소 변경 기능
	 */
	@PostMapping("/changeAddress")
	public String addressChange(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @RequestParam("zip") String zip, @RequestParam("addr1") String addr1,
			@RequestParam("addr2") String addr2) {
		int userId = principal.getUserId();
		myPageService.updateAddress(zip, addr1, addr2, userId);

		return "redirect:/user-info/mypage";
	}

	/**
	 * 전화번호 변경 기능
	 */
	@PostMapping("/changePhone")
	public String updatePhone(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @RequestParam("phone") String phone) {

		int userId = principal.getUserId();
		myPageService.updatePhone(phone, userId);

		return "redirect:/user-info/mypage";
	}

	/**
	 * 비밀번호 변경 페이지 이동
	 */
	@GetMapping("/changePassword")
	public String changePassword(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, Model model) {
		int userId = principal.getUserId();
		User user = myPageService.findUserById(userId);
		model.addAttribute("user", user);
		return "/myPage/changePassword";
	}

	/**
	 * 비밀번호 변경 기능
	 */
	@PostMapping("/changePassword")
	@ResponseBody
	public ResponseEntity<?> changePasswordw(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @RequestBody Map<String, String> request) {
		int userId = principal.getUserId();
		User user = myPageService.findUserById(userId);

		String currentPassword = request.get("currentPassword");
		String newPassword = request.get("newPassword");

		Map<String, Object> response = new HashMap<>();

		// 현재 비밀번호와 입력된 비밀번호 비교
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			response.put("success", false);
			response.put("message", "비밀번호가 일치하지 않습니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// 1. newPassword를 암호화
		// 2. 암호화된 패스워드를 update
		// 3. 서비스로 부터 true / false 값을 받아와서
		String encodedNewPassword = passwordEncoder.encode(newPassword);
		if (myPageService.updateUserByPassword(encodedNewPassword, userId)) {
			response.put("success", true);
			response.put("message", "비밀번호 변경 성공");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response.put("success", false);
			response.put("message", "서버 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	/**
	 * 포인트 내역 페이지 이동
	 */
	@GetMapping("/pointHistory")
	public String pointHistory(//
			@RequestParam(value = "historyType", defaultValue = "all") String historyType, //
			@RequestParam(name = "sortOrder", defaultValue = "desc") String sortOrder, //
			@RequestParam(name = "sortBy", defaultValue = "date") String sortBy, //
			@RequestParam(name = "page", defaultValue = "1") int page, //
			@RequestParam(name = "size", defaultValue = "5") int size, //
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, //
			Model model) {
		int userId = principal.getUserId();

		int startBlock = Math.max(1, ((page - 1) / 5) * 5 + 1);

		int countBalance = myPageService.countBalanceHistory(userId, historyType);
		int totalBalancePages = (int) Math.ceil((double) countBalance / size);
		int endBalanceBlock = Math.min(totalBalancePages, startBlock + 4);

		model.addAttribute("balanceHistory", myPageService.findBalanceHistory(userId, page, size, sortBy, sortOrder, historyType));
		model.addAttribute("totalBalancePages", totalBalancePages);
		model.addAttribute("endBalanceBlock", endBalanceBlock);
		model.addAttribute("startBlock", startBlock);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("historyType", historyType);
		return "/myPage/pointHistroy";
	}

	/**
	 * 결제내역 페이지
	 */
	@GetMapping("/paymentHistory")
	public String paymentHistory(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, Model model) {
		int userId = principal.getUserId();
		List<PaymentHistroyDTO> payment = myPageService.findPaymentByUserId(userId);
		model.addAttribute("payment", payment);
		return "/myPage/paymentHistory";
	}

	/* 환불 요청 페이지 */
	@GetMapping("/paymentRefund/{id}")
	public String paymentRefund(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @PathVariable("id") int paymentId, Model model) {

		PaymentHistroyDTO payment = myPageService.findPaymentById(paymentId);
		int userId = principal.getUserId();
		Wallet wallet = myPageService.readAllWalletByUserId(userId);
		model.addAttribute("wallet", wallet);

		model.addAttribute("payment", payment);

		System.out.println(payment);
		return "/myPage/paymentRefund";
	}

	@PostMapping("/paymentRefund")
	@ResponseBody
	public String paymentRefundw(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal, @RequestParam(name = "id") int paymentId,
			@RequestParam(name = "cancelReason") String cancelReason, @RequestParam(name = "totalAmount") int totalAmount) {

		int userId = principal.getUserId();
		myPageService.updatePayment(paymentId, cancelReason);
		myPageService.updateWallet(totalAmount, userId);
		return "환불 요청 완료 창을 닫아주세요";
	}

	/* 회원탈퇴 */
	@GetMapping("/deleteAccount")
	public String deleteAccount() {
		return "/myPage/deleteAccount";
	}

	@PostMapping("/deleteAccount")
	@ResponseBody
	public String delteAccountw(//
			@SessionAttribute(name = Define.PRINCIPAL) PrincipalDTO principal,//
			@RequestParam("password") String password) {
		int userId = principal.getUserId();
		User user = myPageService.findUserById(userId);
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new DataDeliveryException("비밀번호와 일치 하지않습니다.", HttpStatus.BAD_REQUEST);
		}
		myPageService.updateUserStatus(user.getId());
		httpSession.invalidate();
		return "탈퇴가 완료되었습니다";
	}

}
