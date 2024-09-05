package com.library.bookwave.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.bookwave.repository.model.MyHistory;
import com.library.bookwave.service.MyHistoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/review")
	public String showReview(@RequestParam String param) {

		return "myHistory/review";
	}

}
