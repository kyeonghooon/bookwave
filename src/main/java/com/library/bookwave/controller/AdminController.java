package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.Payment;
import com.library.bookwave.service.AdminService;
import com.library.bookwave.service.BookService;
import com.library.bookwave.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final PaymentService paymentService;
	private final BookService bookService;

	// 관리자 Home 페이지
	@GetMapping("/main")
	public String mainPage() {
		return "admin/dashboard";
	}

	// 관리자 유저 목록 페이지
	@GetMapping("/user")
	public String userPage(Model model) {

		List<PrincipalDTO> userList = adminService.readAllUser();
		model.addAttribute("userList", userList);

		return "admin/userList";
	}

	// 관리자 모든 결제 조회 페이지
	@GetMapping("/payment")
	public String paymentPage(Model model) {

		List<Payment> paymentList = paymentService.readAllPayment();
		model.addAttribute("paymentList", paymentList);
		return "admin/paymentList";
	}

	// 관리자 대출 현황 조회 페이지
	@GetMapping("/lend")
	public String lendPage(Model model) {

		List<Lend> lendList = adminService.readAllLend();
		model.addAttribute("lendList", lendList);

		return "admin/lendList";
	}

	// 관리자 도서 관리 페이지
	@GetMapping("/book")
	public String bookPage(Model model) {

		List<BookListDTO> bookList = adminService.readAllBook();
		model.addAttribute("bookList", bookList);

		return "admin/bookList";
	}

	// 관리자 도서 상세 페이지
	@GetMapping("/book/detail/{bookId}")
	public String bookDetailPage(@PathVariable("bookId") int bookId, Model model) {

		Book book = bookService.readBook(bookId);
		model.addAttribute("book", book);
		return "admin/bookDetail";
	}

}
