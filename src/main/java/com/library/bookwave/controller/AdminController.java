package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.bookwave.repository.model.User;
import com.library.bookwave.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/main")
	public String mainPage() {
		return "admin/dashboard";
	}

	@GetMapping("/info")
	public String infoPage() {
		return "admin/info";
	}

	@GetMapping("/user")
	public String userPage(Model model) {
		// 샘플 계정 100개 생성
		for (int i = 1; i <= 100; i++) {
			User user = User.builder().loginId("a" + i).socialId(null).password("1").name("이름" + i).role("admin").subscribe(1).wave(10000).mileage(5000).status(0).build();
			adminService.createUser(user);
		}

		List<User> userList = adminService.readAllUser();
		model.addAttribute("userList", userList);

		return "admin/userList";
	}

	@GetMapping("/book")
	public String bookPage() {
		return "admin/bookList";
	}
}
