package com.library.bookwave.controller;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.library.bookwave.dto.SignInDTO;
import com.library.bookwave.dto.SignUpDTO;
import com.library.bookwave.dto.api.GoogleOAuthToken;
import com.library.bookwave.dto.api.GoogleProfile;
import com.library.bookwave.dto.api.KakaoProfile;
import com.library.bookwave.dto.api.NaverProfile;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.service.UserService;
import com.library.bookwave.utils.LoginAPIUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final HttpSession session;
	private final LoginAPIUtil loginAPI;

	/*
	 * 회원가입 주소설계: http://localhost:8080/user/sign-up
	 */

	// 회원가입 화면 요청
	@GetMapping("/sign-up")
	public String signUpPage(Model model) {
		String socialId = (String) model.asMap().get("socialId");
//		//TODO 임시값
//		String password = "298p3047y2w98p4uhtrle";
//		model.addAttribute("socialPassword", password);
		model.addAttribute("socialId", socialId);
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
		if (dto.getSocialId() == null) {
			String loginId = dto.getLoginId();
			if (loginId.startsWith("kakao_") || loginId.startsWith("naver_") || loginId.startsWith("google_")) {
				String[] strs = loginId.split("_");
				throw new DataDeliveryException("사용할 수 없는 형식입니다. (" + strs[0] + "_)", HttpStatus.BAD_REQUEST);
			}
		}
		// 서비스 객체로 전달
		userService.registerUser(dto);

		return "redirect:/user/sign-in";
	}

	/*
	 * 로그인 주소설계: http://localhost:8080/user/sign-in
	 */

	// 로그인 화면 요청
	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";
	}
	
	// 아이디, 비밀번호 찾기 화면 요청
	@GetMapping("/find-login")
	public String findLoginPage() {
		return "user/findLogin";
	}
	
//	@PostMapping("/sign-up")
//	public String findLoginProc(SignUpDTO dto) {
//		System.out.println("DTO:" + dto);
//		if (dto.getName() == null || dto.getName().isEmpty()) {
//			throw new DataDeliveryException("이름을 입력해주세요.", HttpStatus.BAD_REQUEST);
//		}
//		if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
//			throw new DataDeliveryException("email을 입력해주세요.", HttpStatus.BAD_REQUEST);
//		}
//
//		// TODO 수정
//		return "redirect:/user/sign-in";
//	}

	// 로그인 요청 처리
	@PostMapping("/sign-in")
	public String signInProc(SignInDTO dto) {
		System.out.println("DTO:" + dto);

		if (dto.getLoginId() == null || dto.getLoginId().isEmpty()) {
			throw new DataDeliveryException("아이디를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		String loginId = dto.getLoginId();
		if (loginId.startsWith("kakao_") || loginId.startsWith("naver_") || loginId.startsWith("google_")) {
			String[] strs = loginId.split("_");
			throw new DataDeliveryException("사용할 수 없는 형식입니다. (" + strs[0] + "_)", HttpStatus.BAD_REQUEST);
		}

		User principal = userService.readUser(dto);

		// 세션 메모리에 등록 처리
		session.setAttribute("principal", principal);

		// TODO 수정
		return "redirect:/user/sign-in";
	}

	/*
	 * 소셜 로그인
	 * 
	 */
	@GetMapping("/social")
	public String socialLogin(@RequestParam(name = "type") String type) {
		URI uri = null;
		switch (type) {
		case "naver":
			System.out.println("11네이버 왔삼");
			uri = UriComponentsBuilder.fromUriString("https://nid.naver.com/oauth2.0/authorize")
					.query("response_type=code")
					.query("client_id=" + loginAPI.getNaverClientId())
					.query("redirect_uri=" + "http://localhost:8080/user/naver")
					.query("state=STATE_STRING")
					.build().toUri();
			System.out.println("uri : " + uri);
			return "redirect:" + uri;
		case "kakao":
			System.out.println("22카카오 왔삼");
			uri = UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
					.query("client_id=" + loginAPI.getKakaoRestApt())
					.query("redirect_uri=" + "http://localhost:8080/user/kakao").query("response_type=" + "code")
					.build().toUri();
			System.out.println("uri : " + uri);
			return "redirect:" + uri;
		case "google":
			
			System.out.println("33구글 왔삼");
			uri = UriComponentsBuilder.fromUriString("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount")
					.query("client_id=" + loginAPI.getGoogleClientId())
					//.query("client_secret=" + loginAPI.getGoogleClientSecret())
					.query("redirect_uri=http://localhost:8080/user/google")
					.query("response_type=code")
					.query("scope=openid")
					//.query("grant_type=" + "authorization_code")
					.build().toUri();
			System.out.println("uri : " + uri);
			return "redirect:" + uri;
			
		default:
			break;
		}
		return null;
	}

	// 구글
	@GetMapping("/google")
	public String google(@RequestParam(name = "code") String code, RedirectAttributes redirectAttributes) {
		System.out.println("code : " + code);
		System.out.println("구글 들어오세요");

		// Header, body 구성
		RestTemplate rt1 = new RestTemplate();
		// Header
		HttpHeaders header1 = new HttpHeaders();
		header1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body
		MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
		params1.add("code", code);
		params1.add("client_id", loginAPI.getGoogleClientId());
		params1.add("client_secret", loginAPI.getGoogleClientSecret());
		params1.add("redirect_uri", "http://localhost:8080/user/google");
		params1.add("grant_type", "authorization_code");

		// Header + body 결합
		HttpEntity<MultiValueMap<String, String>> reqGoogleMessage = new HttpEntity<>(params1, header1);

		// 통신 요청
		ResponseEntity<GoogleOAuthToken> reponse1 = rt1.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST,
				reqGoogleMessage, GoogleOAuthToken.class);

		System.out.println("GoogleAuthToken : " + reponse1.getBody().toString());

		// 리소스서버 사용자 정보 가져오기
		RestTemplate rt2 = new RestTemplate();
		System.out.println("제발 들어와와" + rt2);
		HttpHeaders headers2 = new HttpHeaders();
		// Bearer 공백 확인!
		headers2.add("Authorization", "Bearer " + reponse1.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP Entity
		HttpEntity<MultiValueMap<String, String>> reqGoogleInfoMessage = new HttpEntity<>(headers2);

		// 통신 요청
		ResponseEntity<GoogleProfile> resposne2 = rt2.exchange("https://www.googleapis.com/userinfo/v2/me",
				HttpMethod.GET, reqGoogleInfoMessage, GoogleProfile.class);
		System.out.println("googleProfile : " + resposne2.getBody().toString());

		GoogleProfile googleProfile = resposne2.getBody();
		String socialId = "google_" + googleProfile.getId();
		System.out.println(socialId);
		// TODO
		// 1. 최초 로그인 확인
		User principal = userService.searchLoginId(socialId);
		System.out.println("최초 로그인 했니 안 했니");
		if (principal == null) {
			redirectAttributes.addFlashAttribute("socialId", socialId);
			return "redirect:/user/sign-up";
		} else {
			session.setAttribute("principal", principal);
			return "redirect:/"; // TODO 수정 (임시)
		}

	}


	// 카카오
	@GetMapping("/kakao")
	public String kakao(@RequestParam(name = "code") String code, RedirectAttributes redirectAttributes) {
		System.out.println("카카오 들어오세요");

		// Header, body 구성
		RestTemplate rt1 = new RestTemplate();
		// Header
		HttpHeaders header1 = new HttpHeaders();
		header1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body
		MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
		params1.add("code", code);
		params1.add("client_id", loginAPI.getKakaoRestApt());
		params1.add("redirect_uri", "http://localhost:8080/user/kakao");
		params1.add("grant_type", "authorization_code");

		// Header + body 결합
		HttpEntity<MultiValueMap<String, String>> reqKakaoMessage = new HttpEntity<>(params1, header1);

		// 통신 요청
		ResponseEntity<GoogleOAuthToken> reponse1 = rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				reqKakaoMessage, GoogleOAuthToken.class);

		System.out.println("KakaoAuthToken : " + reponse1.getBody().toString());

		// 리소스서버 사용자 정보 가져오기
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		// Bearer 공백 확인!
		headers2.add("Authorization", "Bearer " + reponse1.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP Entity
		HttpEntity<MultiValueMap<String, String>> reqKakaoInfoMessage = new HttpEntity<>(headers2);

		// 통신 요청
		ResponseEntity<KakaoProfile> resposne2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				reqKakaoInfoMessage, KakaoProfile.class);
		System.out.println("KakaoProfile : " + resposne2.getBody().toString());

		KakaoProfile kakaoProfile = resposne2.getBody();
		String socialId = "kakao_" + kakaoProfile.getId();
		System.out.println(socialId);
		// TODO
		// 1. 최초 로그인 확인
		User principal = userService.searchLoginId(socialId);
		System.out.println("최초 로그인 했니 안 했니");
		if (principal == null) {
			redirectAttributes.addFlashAttribute("socialId", socialId);
			return "redirect:/user/sign-up";
		} else {
			session.setAttribute("principal", principal);
			return "redirect:/"; // TODO 수정 (임시)
		}
	
	}

	// 네이버
	@GetMapping("/naver")
	public String naver(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, RedirectAttributes redirectAttributes) {
		System.out.println("네이버 들어오세요");
		System.out.println("code : " + code);
		System.out.println("state : " + state);

		// Header, body 구성
		RestTemplate rt1 = new RestTemplate();
		// Header
		HttpHeaders header1 = new HttpHeaders();
		header1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body
		MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
		params1.add("code", code);
		params1.add("state", state);
		params1.add("client_id", loginAPI.getNaverClientId());
		params1.add("client_secret", loginAPI.getNaverClientSecret());
		params1.add("grant_type", "authorization_code");

		// Header + body 결합
		HttpEntity<MultiValueMap<String, String>> reqNaverMessage = new HttpEntity<>(params1, header1);

		// 통신 요청
		ResponseEntity<GoogleOAuthToken> reponse1 = rt1.exchange("https://nid.naver.com/oauth2.0/token",
				HttpMethod.POST, reqNaverMessage, GoogleOAuthToken.class);

		System.out.println("NaverAuthToken : " + reponse1.getBody().toString());

		// 리소스서버 사용자 정보 가져오기
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		// Bearer 공백 확인!
		headers2.add("Authorization", "Bearer " + reponse1.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP Entity
		HttpEntity<MultiValueMap<String, String>> reqNaverInfoMessage = new HttpEntity<>(headers2);

		// 통신 요청
		ResponseEntity<NaverProfile> resposne2 = rt2.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET,
				reqNaverInfoMessage, NaverProfile.class);
		System.out.println("NaverProfile : " + resposne2.getBody().toString());

		NaverProfile naverProfile = resposne2.getBody();
		String socialId = "naver_" + naverProfile.getId();
		System.out.println(socialId);
		
		// TODO
		// 1. 최초 로그인 확인
		User principal = userService.searchLoginId(socialId);
		System.out.println("최초 로그인 했니 안 했니");
		if (principal == null) {
			redirectAttributes.addFlashAttribute("socialId", socialId);
			return "redirect:/user/sign-up";
		} else {
			session.setAttribute("principal", principal);
			return "redirect:/"; // TODO 수정 (임시)
		}

	}

}//
