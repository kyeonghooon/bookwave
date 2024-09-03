package com.library.bookwave.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.bookwave.dto.SignInDTO;
import com.library.bookwave.dto.SignUpDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user") // 수정
public class UserController {

//	@Autowired
//	private MembersMapper mapper;

	private UserService userService;
	private final HttpSession session; // 이거 해주는 이유 찾아 보기

	// 로그인 , 회원가입 화면 요청 기능 추가

	public UserController(UserService service, HttpSession session) {
		this.userService = service;
		this.session = session;
	}

	/*
	 * 주소설계: http://localhost:8080/user/sign-up
	 */
	// 회원가입 화면 요청
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}

	// 회원가입 요청 처리
	// TODO Define 파일 생성 후 수정하기
	@PostMapping("/sign-up")
	public String signUpProc(SignUpDTO dto) {
		System.out.println("");
		System.out.println("DTO:" + dto);
		if (dto.getLoginId() == null || dto.getLoginId().isEmpty()) {
			throw new DataDeliveryException("id를 입력해 주세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException("password를 입력해 주세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getName() == null || dto.getName().isEmpty()) {
			throw new DataDeliveryException("이름을 입력해 주세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getEmail1() == null || dto.getEmail1().isEmpty()) {
			throw new DataDeliveryException("email을 입력해 주세요", HttpStatus.BAD_REQUEST);

		}

		if (dto.getGender() == null) {
			throw new DataDeliveryException("성별을 선택해 주세요", HttpStatus.BAD_REQUEST);
		}

//		if(dto.getPhone() == null || dto.getPhone().isEmpty()) {
//			throw new DataDeliveryException("번호를 입력해 주세요", HttpStatus.BAD_REQUEST);
//		}
		// 서비스 객체로 전달
		userService.registerUser(dto);

		return "redirect:/user/sign-in";
	}

	// 로그인 화면 요청
	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";
	}

	// 로그인 요청 처리
	// 주소 설계: http://localhost:8080/user/sign-in
	@PostMapping("/sign-in")
	public String signInProc(SignInDTO dto) {
		System.out.println("DTO:" + dto);
		
		if(dto.getLoginId() == null || dto.getLoginId().isEmpty()) {
			throw new DataDeliveryException("아이디를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		
		User principal = userService.readUser(dto);
		
		// 세션 메모리에 등록 처리
		session.setAttribute("paincipal", principal);
		
		userService.readUser(dto);
		
		// TODO 수정
		return "redirect:/user/sign-up";
	}

}//
