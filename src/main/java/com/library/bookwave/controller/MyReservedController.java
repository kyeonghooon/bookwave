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

import com.library.bookwave.dto.LendDTO;
import com.library.bookwave.repository.model.MyReserved;
import com.library.bookwave.service.MyReservedService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my-reserved")
public class MyReservedController {

	@Autowired
	MyReservedService reservedService;
	LendDTO dto;

	/**
	 * 대출 현황 페이지
	 * 
	 * @return myBooks.jsp
	 */
	@GetMapping("/list")
	public String myBooksPage(Model model, HttpSession session) {

		List<MyReserved> list = new ArrayList<>();

		// TODO 테스트용으로 하드코딩
		// 로그인기능 완성시 세션 id로 변경
		// int id = (int) session.getAttribute("userId");
		list = reservedService.readAllById(1);

		model.addAttribute("myReservedList", list);

		return "myLibrary/reservedBooks";
	}

	@PostMapping("/lend/{id}")
	public String lendProc(@PathVariable("id") int id, @RequestParam("userId") int userId,
			@RequestParam("bookId") int bookId) {

		dto = LendDTO.builder().bookId(bookId).userId(userId).build();

		reservedService.processLendAndUpdateStatus(dto, id);

		return "redirect:/my-reserved/list";
	}

	@PostMapping("/cancel/{bookId}")
	public String cancelProc(@PathVariable("bookId") int bookId) {

		reservedService.deleteReservedById(bookId);

		return "redirect:/my-reserved/list";
	}

}