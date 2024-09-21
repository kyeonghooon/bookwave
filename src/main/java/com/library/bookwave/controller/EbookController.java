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
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.EbookDTO;
import com.library.bookwave.dto.EbookReorderDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.Book;
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

	/**
	 * ebook 리스트 페이지 호출
	 */
	@GetMapping
	public String listPage(//
			@RequestParam(name = "category", defaultValue = "-1") Integer category, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, //
			Model model) {

		int userId = principal.getUserId();
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
	 * Ebook view 페이지 호출
	 */
	@GetMapping("/view/{bookId}")
	public String viewPage(//
			@PathVariable(name = "bookId") Integer bookId, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, //
			Model model) {

		int userId = principal.getUserId();

		UserEbook userEbook = ebookService.readUserEbook(userId, bookId);
		Book book = ebookService.findEbookPathByBookId(bookId);
		
		model.addAttribute("ebook", userEbook);
		model.addAttribute("ebookPath", book.getEbookPath());
		model.addAttribute("ebookTitle", book.getTitle());
		return "ebook/ebookRead";
	}

	/**
	 * 읽은 위치 저장
	 */
	@PostMapping("/save/{bookId}")
	public ResponseEntity<?> savePage(//
			@RequestBody Double progress, //
			@PathVariable(name = "bookId") Integer bookId, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		
		int userId = principal.getUserId();
		if (!ebookService.updateUserEbookWithLastPoint(progress, userId, bookId)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장 실패");
		} else {
			return ResponseEntity.ok().body("저장 성공");
		}
	}

	@GetMapping("/add-category")
	public ResponseEntity<?> addCategory(//
			@RequestParam(name = "categoryName") String categoryName, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		int userId = principal.getUserId();
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
	
	/**
	 * 카테고리 이름 변경
	 */
	@GetMapping("/edit-category")
	public ResponseEntity<?> editCategory(//
			@RequestParam(name = "categoryName") String categoryName, //
			@RequestParam(name = "categoryId") Integer categoryId, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		
		int userId = principal.getUserId();
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
	public ResponseEntity<?> reorderEbookCategory(//
			@RequestBody List<Map<String, Integer>> request, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		
		int userId = principal.getUserId();
		List<EbookReorderDTO> ebookReorderList = new ArrayList<>();
		for (Map<String, Integer> map : request) {
			ebookReorderList.add(EbookReorderDTO.builder().categoryId(map.get("categoryId")).priority(map.get("priority")).build());
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
	public ResponseEntity<?> changeEbookCategory(//
			@RequestBody Map<String, Integer> request, //
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		
		Integer userId = principal.getUserId();
		Integer bookId = request.get("bookId");
		Integer categoryId = request.get("categoryId");

		Map<String, Object> response = new HashMap<>();
		if (ebookService.updateUserEbookCategory(categoryId, userId, bookId)) {
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
	public ResponseEntity<?> registEbook(//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, //
			@PathVariable(name = "bookId") Integer bookId) {
		
		Integer userId = principal.getUserId();
		Map<String, Object> response = new HashMap<>();

		if (!principal.getSubscribe()) {
			response.put("success", false);
			response.put("message", "구독 서비스 이용자가 아닙니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		if (ebookService.createEbookWithSubscribe(userId, bookId)) {
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
