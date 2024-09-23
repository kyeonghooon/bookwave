package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.dto.PaymentMonthDTO;
import com.library.bookwave.dto.PointDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.UserDetailDTO;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.Payment;
import com.library.bookwave.repository.model.Printer;
import com.library.bookwave.service.AdminService;
import com.library.bookwave.service.BookService;
import com.library.bookwave.service.FacilityService;
import com.library.bookwave.service.EmailService;
import com.library.bookwave.service.PaymentService;
import com.library.bookwave.service.SupportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final PaymentService paymentService;
	private final BookService bookService;
	private final SupportService supportService;
	private final FacilityService facilityService;

	// 관리자 Home 페이지
	@GetMapping("/main")
	public String mainPage(Model model) {
		int bookCount = bookService.countBook();
		int lendBookCount = bookService.countLendBook();
		int dueBookCount = bookService.countDueBook();
		List<PaymentMonthDTO> paymentMonth = paymentService.paymentMonth();
		Long paymentThisMonth = paymentService.paymentThisMonth();
		int userCount = adminService.countUser();
		int subscribeCount = adminService.countSubscribe();
		int qnaCount = supportService.countQna();
		int countAge10 = adminService.countAge(1);
		int countAge20 = adminService.countAge(2);
		int countAge30 = adminService.countAge(3);
		model.addAttribute("bookCount", bookCount);
		model.addAttribute("lendBookCount", lendBookCount);
		model.addAttribute("dueBookCount", dueBookCount);
		model.addAttribute("paymentMonth", paymentMonth);
		model.addAttribute("paymentThisMonth", paymentThisMonth);
		model.addAttribute("userCount", userCount);
		model.addAttribute("subscribeCount", subscribeCount);
		model.addAttribute("qnaCount", qnaCount);
		model.addAttribute("countAge10",countAge10);
		model.addAttribute("countAge20",countAge20);
		model.addAttribute("countAge30",countAge30);
		return "admin/dashboard";
	}

	// 관리자 유저 목록 페이지
	@GetMapping("/user")
	public String userPage(Model model) {

		List<PrincipalDTO> userList = adminService.readAllUser();
		model.addAttribute("userList", userList);

		return "admin/userList";
	}

	// 관리자 유저 상세보기 페이지
	@GetMapping("/user-detail")
	public String AdminUserDetailPage(@RequestParam(name = "id") int userId, Model model) {

		UserDetailDTO user = adminService.readUserById(userId);
		model.addAttribute("user", user);
		return "admin/adminUserDetail";
	}

	// 관리자 전체유저 wave, mileage 지급 페이지
	@GetMapping("/user-point")
	public String setPointPage() {
		return "admin/userPoint";
	}

	// 관리자 전체유저 wave, mileage 지급
	@PostMapping("/user-point")
	public String setPointProc(@ModelAttribute PointDTO point) {
		adminService.updatePointAllUser(point);
		return "admin/userPointSuccess";
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

	// 프린트 요청 리스트 페이지
	@GetMapping("/printer")
	public String printerPage(Model model) {
		List<Printer> printList = facilityService.readAllPrinter();
		model.addAttribute("printList", printList);
		return "admin/printList";
	}

}
