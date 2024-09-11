package com.library.bookwave.controller;

import java.util.ArrayList;
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

import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.model.MyBookHistory;
import com.library.bookwave.repository.model.MyEbookHistory;
import com.library.bookwave.service.MyHistoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class MyHistoryController {

	private final MyHistoryService historyService;

	@GetMapping("/list")
	public String showList(@RequestParam(value = "type", required = false, defaultValue = "all") String type,
			@RequestParam(value = "search", required = false) String search, Model model, HttpSession session) {

//	    Integer id = (Integer) session.getAttribute("id");
//	    if (id == null || id == 0) {
//	        return "redirect:/login";
//	    }

		List<Object> list = new ArrayList<>();
		final Map<String, Integer> categoryData = new HashMap<>();
		final Map<String, Integer> monthlyData = new HashMap<>();
		Integer totalCountCategory = null;
		Integer totalCountMonth = null;

		if (type.equals("all")) {

			List<MyBookHistory> bookList = historyService.findAllBookByUserId(1);
			List<MyEbookHistory> ebookList = historyService.findAllEbookByUserId(1);

			list.addAll(bookList);
			list.addAll(ebookList);

			// Combine category data
			Map<String, Integer> bookCategoryData = historyService.findBookCategoryDataByUserId(1);
			Map<String, Integer> ebookCategoryData = historyService.findEbookCategoryDataByUserId(1);
			bookCategoryData.forEach((k, v) -> categoryData.merge(k, v, Integer::sum)); // Merge category data
			ebookCategoryData.forEach((k, v) -> categoryData.merge(k, v, Integer::sum));

			// Combine monthly data
			Map<String, Integer> bookMonthlyData = historyService.findMonthlyBookLendsByUserId(1);
			Map<String, Integer> ebookMonthlyData = historyService.findMonthlyEbookLendsByUserId(1);
			bookMonthlyData.forEach((k, v) -> monthlyData.merge(k, v, Integer::sum)); // Merge monthly data
			ebookMonthlyData.forEach((k, v) -> monthlyData.merge(k, v, Integer::sum));

			totalCountCategory = list.size();
			totalCountMonth = list.size();

		} else if (type.equals("book")) {
			list = new ArrayList<>(historyService.findAllBookByUserId(1));
			categoryData.putAll(historyService.findBookCategoryDataByUserId(1));
			totalCountCategory = list.size();
			monthlyData.putAll(historyService.findMonthlyBookLendsByUserId(1));
			totalCountMonth = list.size();
		} else if (type.equals("ebook")) {
			list = new ArrayList<>(historyService.findAllEbookByUserId(1));
			categoryData.putAll(historyService.findEbookCategoryDataByUserId(1));
			totalCountCategory = list.size();
			monthlyData.putAll(historyService.findMonthlyEbookLendsByUserId(1));
			totalCountMonth = list.size();
		}

		// Adding attributes to the model
		model.addAttribute("myHistoryList", list);
		model.addAttribute("categoryData", categoryData);
		model.addAttribute("monthlyData", monthlyData);
		model.addAttribute("totalCountMonth", totalCountMonth);
		model.addAttribute("totalCountCategory", totalCountCategory);

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
