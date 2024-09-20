package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.LendDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.MyReserved;
import com.library.bookwave.service.MyReservedService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/my-reserved")
@RequiredArgsConstructor
public class MyReservedController {

	private final MyReservedService reservedService;

	@GetMapping("/list")
	public String myBooksPage(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, Model model) {
		int userId = principal.getUserId();

		List<MyReserved> list = reservedService.readAllById(userId);
		Map<Integer, Integer> countBeforeMap = new HashMap<>();

		for (MyReserved reserved : list) {
			int bookId = reserved.getBookId();
			int before = reservedService.findCountBeforeByUserIdAndBookId(userId, bookId);
			countBeforeMap.put(bookId, before);
		}

		model.addAttribute("myReservedList", list);
		model.addAttribute("countBeforeMap", countBeforeMap);

		return "myLibrary/reservedBooks";
	}

	@PostMapping("/lend/{id}")
	public String lendProc(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable("id") int id, @RequestParam(value = "bookId", required = false) Integer bookId, Model model) {
		int userId = principal.getUserId();

		if (bookId == null || bookId <= 0) {
			model.addAttribute("errorMessage", "유효하지 않은 요청입니다.");
			return "myLibrary/reservedBooks";
		}

		// Validate if the user has reserved the book
		List<MyReserved> list = reservedService.readAllById(userId);
		if (!list.stream().anyMatch(book -> book.getId() == bookId)) {
			model.addAttribute("errorMessage", "예약하지 않았거나 유효하지 않은 도서입니다.");
			return "myLibrary/reservedBooks";
		}

		LendDTO dto = LendDTO.builder().bookId(bookId).userId(userId).build();
		reservedService.processLendAndUpdateStatus(dto, id);

		return "redirect:/my-reserved/list";
	}

	@PostMapping("/cancel/{bookId}")
	public String cancelProc(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable("bookId") int bookId, Model model) {
		int userId = principal.getUserId();

		// Validate if the user has reserved the book
		List<MyReserved> list = reservedService.readAllById(userId);
		if (!list.stream().anyMatch(book -> book.getId() == bookId)) {
			model.addAttribute("errorMessage", "예약하지 않았거나 유효하지 않은 도서입니다.");
			return "myLibrary/reservedBooks";
		}

		reservedService.deleteReservedById(bookId);

		return "redirect:/my-reserved/list";
	}
}
