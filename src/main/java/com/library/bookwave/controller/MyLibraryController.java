package com.library.bookwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.service.ItemService;
import com.library.bookwave.service.MyLibraryService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/my-library")
@RequiredArgsConstructor
public class MyLibraryController {

	private final MyLibraryService libraryService;
	private final ItemService itemService;

	@GetMapping("/my-lend")
	public String myBooksPage(@SessionAttribute(Define.PRINCIPAL) PrincipalDTO principal, Model model) {
		int userId = principal.getUserId();
		model.addAttribute("myLendList", libraryService.readAllById(userId));
		return "myLibrary/myBooks";
	}

	@PostMapping("/return/{id}")
	public String returnProc(@SessionAttribute(Define.PRINCIPAL) PrincipalDTO principal, Model model,
			@PathVariable(name = "id") Integer id) {
		Integer bookId = libraryService.findBookIdById(id);

		if (!libraryService.validation(principal.getUserId(), bookId)) {
			model.addAttribute("errorMessage", "대출하지 않았거나 유효하지 않은 도서입니다.");
			return "myLibrary/myBooks";
		}

		Integer reservationId = libraryService.findFirstByBookIdAndStatus(bookId);

		if (reservationId != null) {
			libraryService.updateStatusByIdReservation(reservationId);
			libraryService.updateWaitDateById(reservationId);
		} else {
			libraryService.updateStockByBookId(id);
		}

		libraryService.updateStatusById(id);
		libraryService.updateReturnedDateById(id);
		return "redirect:/my-library/my-lend";
	}

	@GetMapping("/renew/{bookId}")
	public String renewPage(@SessionAttribute(Define.PRINCIPAL) PrincipalDTO principal, Model model,
			@PathVariable(name = "bookId") Integer bookId) {

		if (!libraryService.validation(principal.getUserId(), bookId)) {
			model.addAttribute("errorMessage", "대출하지 않았거나 유효하지 않은 도서입니다.");
			return "myLibrary/myBooks";
		}
		String itemsJson = itemService.findItemsByPageName("renew");
		model.addAttribute("bookId", bookId);
		model.addAttribute("items", itemsJson);
		return "myLibrary/renew";
	}

	@PostMapping("/renew/{bookId}")
	public String renewProc(@SessionAttribute(Define.PRINCIPAL) PrincipalDTO principal, Model model,
			@PathVariable(name = "bookId") Integer bookId, @RequestParam(name = "day", required = false) Integer days,
			@RequestParam(name = "point", required = false) Integer point) {

		// Validate if the user has the book
		if (!libraryService.validation(principal.getUserId(), bookId)) {
			model.addAttribute("errorMessage", "대출하지 않았거나 유효하지 않은 도서입니다.");
			return "myLibrary/renew";
		}

		// Validate request parameters
		if (days == null || days < 1 || days > 7 || point == null || point != days * 100) {
			model.addAttribute("errorMessage", "유효하지 않은 요청입니다.");
			return "myLibrary/renew";
		}

		libraryService.updateReturnDateById(bookId, days);
		return "redirect:/my-library/my-lend";
	}
}
