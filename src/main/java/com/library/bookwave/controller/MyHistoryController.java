package com.library.bookwave.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.model.MyHistory;
import com.library.bookwave.service.MyHistoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class MyHistoryController {

	private final MyHistoryService historyService;

	@GetMapping("/list")
	public String showList(Model model, HttpSession session) {

		// int id = (int) session.getAttribute("id");

		List<MyHistory> list = new ArrayList<>();

		list = historyService.readAllById(1);

		model.addAttribute("myHistoryList", list);

		return "myHistory/history";
	}

	@GetMapping("/review/{bookId}")
	public String showReview(@PathVariable("bookId") int bookId, Model model) {

		model.addAttribute("bookId", bookId);

		return "myHistory/review";
	}

	@PostMapping("/review/{bookId}")
	public String postMethodName(@PathVariable("bookId") int bookId, @RequestParam(name = "score") int score,
			@RequestParam(name = "content") String content, HttpSession session) {

		int id = (int) session.getAttribute("id");

		ReviewDTO dto = ReviewDTO.builder().bookId(bookId).content(content).score(score).userId(id).build();

		historyService.createReview(dto);

		return "redirect:/history/list";
	}

}
