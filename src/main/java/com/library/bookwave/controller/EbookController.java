package com.library.bookwave.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookwave.dto.EbookDTO;
import com.library.bookwave.dto.EbookReorderDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.handler.exception.RedirectException;
import com.library.bookwave.repository.model.UserEbook;
import com.library.bookwave.repository.model.UserEbookCategory;
import com.library.bookwave.service.EbookService;
import com.library.bookwave.service.ItemService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ebook")
@RequiredArgsConstructor
public class EbookController {

	private final EbookService ebookService;
	private final ItemService itemService;


	@GetMapping
	public String listPage(//
			@RequestParam(name = "category", defaultValue = "-1") Integer category, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal, //
			Model model) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		List<EbookDTO> bookList = ebookService.findEbookListByUserIdAndCategory(userId, category);
		List<UserEbookCategory> categoryList = ebookService.findEbookCategoryListByUserId(userId);
		String itemsJson = itemService.findItemsByPageName("ebookList");

		
		model.addAttribute("bookList", bookList);
		model.addAttribute("selectedCategory", category);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("items", itemsJson);
		return "ebook/ebookList";
	}

	/**
	 * Ebook view 페이지 호출 // TODO 주소 제거 예정 localhost:8080/ebook/view/2
	 * 
	 * @return
	 */
	@GetMapping("/view/{bookId}")
	public String viewPage(@PathVariable(name = "bookId") Integer bookId, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal, //
			Model model) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();

		// 1. 해당 유저의 해당 ebook에 대한 내역 확인
		UserEbook userEbook = ebookService.readUserEbook(userId, bookId);

		// 2. 해당 ebook의 path 받아옴
		// TODO 제목도 받아와야함 model 만들어 지면 추가
		String ebookPath = ebookService.findEbookPathByBookId(userId);
		// 3. model에 attribute 추가
		model.addAttribute("ebook", userEbook);
		model.addAttribute("ebookPath", ebookPath);
		return "ebook/ebookRead";
	}

	/**
	 * 읽은 위치 저장
	 */
	@PostMapping("/save/{bookId}")
	@ResponseBody
	public ResponseEntity<?> savePage(@RequestBody Double progress, //
			@PathVariable(name = "bookId") Integer bookId, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		int result = ebookService.updateUserEbookWithLastPoint(progress, userId, bookId);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장 실패");
		} else {
			return ResponseEntity.ok().body("저장 성공");
		}
	}

	@GetMapping("/add-category")
	public ResponseEntity<Map<String, Object>> addCategory(@RequestParam(name = "categoryName") String categoryName, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		Map<String, Object> response = new HashMap<>();
		if (!categoryName.matches("^[가-힣a-zA-Z0-9]+$") || categoryName.length() > 8) {
			response.put("success", "false");
			response.put("message", "이름은 8글자 이하의 한글, 영어, 숫자만 사용 가능합니다.");
			return ResponseEntity.badRequest().body(response);
		}

		int limit = ebookService.findEbookCategoryLimitByUserId(userId);
		int current = ebookService.countEbookCategoryByUserId(userId);
		if (limit <= current) {
			response.put("success", "purchase");
			response.put("message", "결제가 필요합니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (ebookService.createUserEbookCategory(userId, categoryName)) {
			response.put("success", "true");
			response.put("message", "카테고리가 성공적으로 추가되었습니다.");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", "false");
			response.put("message", "알 수 없는 오류로 생성 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@GetMapping("/edit-category")
	public ResponseEntity<Map<String, Object>> editCategory(@RequestParam(name = "categoryName") String categoryName, //
			@RequestParam(name = "categoryId") Integer categoryId, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		Map<String, Object> response = new HashMap<>();
		if (!categoryName.matches("^[가-힣a-zA-Z0-9]+$") || categoryName.length() > 8) {
			response.put("success", "false");
			response.put("message", "이름은 8글자 이하의 한글, 영어, 숫자만 사용 가능합니다.");
			return ResponseEntity.badRequest().body(response);
		}
		if (ebookService.updateUserEbookCategoryName(userId, categoryName, categoryId)) {
			response.put("success", "true");
			response.put("message", "카테고리명이 성공적으로 변경되었습니다.");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", "false");
			response.put("message", "알 수 없는 오류로 변경 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	/**
	 * ebook 순서 변경
	 */
	@PostMapping("reorder-category")
	public ResponseEntity<Map<String, Object>> reorderEbookCategory(@RequestBody List<Map<String, Integer>> request, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		List<EbookReorderDTO> ebookReorderList = new ArrayList<>();
		for (Map<String, Integer> map : request) {
			ebookReorderList.add(
					EbookReorderDTO.builder().categoryId(map.get("categoryId")).priority(map.get("priority")).build());
		}

		Map<String, Object> response = new HashMap<>();
		if (ebookService.updateUserEbookPriority(userId, ebookReorderList)) {
			response.put("success", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "카테고리 순서 변경에 실패했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * ebook 카테고리 변경
	 */
	@PostMapping("change-category")
	public ResponseEntity<Map<String, Object>> changeEbookCategory(@RequestBody Map<String, Integer> request, //
			@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		Integer bookId = request.get("bookId");
		Integer categoryId = request.get("categoryId");

		Map<String, Object> response = new HashMap<>();
		if (ebookService.updateUserEbookCategory(categoryId, userId, bookId) == 1) {
			response.put("success", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "카테고리 변경에 실패했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * 구독 서비스 이용자가 ebook을 등록
	 */
	@GetMapping("regist/{bookId}")
	public ResponseEntity<Map<String, Object>> registEbook(@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal, //
			@PathVariable(name = "bookId") Integer bookId) {
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = principal == null ? 1 : principal.getUserId();
		Map<String, Object> response = new HashMap<>();
		
		// TODO 주석 제거 예정
//		if (!principal.getSubscribe()) {
//			response.put("success", false);
//			response.put("message", "구독 서비스 이용자가 아닙니다.");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		}
		
		if (ebookService.createEbookWithSubscribe(userId, bookId) == 1) {
			response.put("success", true);
			response.put("message", "등록에 성공했습니다. (카테고리 미지정)");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "등록에 실패했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
