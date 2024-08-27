package com.library.bookwave.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.repository.model.MyLibrary;
import com.library.bookwave.service.MyLibraryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my-library")
public class MyLibraryController {

	@Autowired
	MyLibraryService libraryService;

	/**
	 * 대출 현황 페이지
	 * 
	 * @return myBooks.jsp
	 */
	@GetMapping("/my-lend")
	public String myBooksPage(Model model, HttpSession session) {

		List<MyLibrary> list = new ArrayList<>();

		// TODO 테스트용으로 하드코딩
		// 로그인기능 완성시 세션 id로 변경
		// int id = (int) session.getAttribute("userId");
		list = libraryService.readAllById(1);

		model.addAttribute("myLendList", list);

		System.err.println(list.getClass().getName());

		return "myLibrary/myBooks";
	}

	@PostMapping("/return/{bookId}")
	public String returnProc(Model model, @PathVariable(name = "bookId") Integer bookId) {

		libraryService.updateStatusById(bookId);

		return "redirect:/my-library/my-lend";
	}

	@GetMapping("/renew/{bookId}")
	public String getMethodName(Model model, @PathVariable(name = "bookId") Integer bookId) {
		return "myLibrary/renew";
	}

	@PostMapping("/renew/{bookId}")
	public String renewProc(Model model, @PathVariable(name = "bookId") Integer bookId,
			@RequestParam(name = "day") Integer days, @RequestParam(name = "point") Integer point) {

		System.err.println("Bookid : " + bookId);
		System.err.println("Days Selected: " + days);
		System.err.println("Points: " + point);
		libraryService.updateReturnDateById(bookId, days);
		// TODO 포인트 변경
		// int id = (int) session.getAttribute("userId");
		// userService.updatePointById(id, point);

		return "redirect:/my-library/my-lend";
	}

}
