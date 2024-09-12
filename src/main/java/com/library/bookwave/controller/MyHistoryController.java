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
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.model.MyBookHistory;
import com.library.bookwave.repository.model.MyEbookHistory;
import com.library.bookwave.service.MyHistoryService;
import com.library.bookwave.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class MyHistoryController {

	private final MyHistoryService historyService;

	@GetMapping("/list")
	public String showList(@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal,
			@RequestParam(value = "type", required = false, defaultValue = "all") String type,
			@RequestParam(value = "search", required = false) String search, Model model, HttpSession session) {

		int userId = principal == null ? 1 : principal.getUserId();

		List<Object> list = new ArrayList<>();
		final Map<String, Integer> categoryData = new HashMap<>();
		final Map<String, Integer> monthlyData = new HashMap<>();
		Integer totalCountCategory = null;
		Integer totalCountMonth = null;

		// Fetch reviewed book ids for the current user
		List<Integer> reviewedBookIds = historyService.getReviewedBookIdsByUserId(userId);

		if (type.equals("all")) {
			List<MyBookHistory> bookList = (search != null && !search.isEmpty())
					? historyService.findBooksByTitle(userId, search)
					: historyService.findAllBookByUserId(userId);

			List<MyEbookHistory> ebookList = (search != null && !search.isEmpty())
					? historyService.findEbooksByTitle(userId, search)
					: historyService.findAllEbookByUserId(userId);

			list.addAll(bookList);
			list.addAll(ebookList);

			// Combine category data
			Map<String, Integer> bookCategoryData = historyService.findBookCategoryDataByUserId(userId);
			Map<String, Integer> ebookCategoryData = historyService.findEbookCategoryDataByUserId(userId);

			bookCategoryData.forEach((k, v) -> categoryData.merge(k, v, Integer::sum)); // Merge category data
			ebookCategoryData.forEach((k, v) -> categoryData.merge(k, v, Integer::sum));

			// Combine monthly data
			Map<String, Integer> bookMonthlyData = historyService.findMonthlyBookLendsByUserId(userId);
			Map<String, Integer> ebookMonthlyData = historyService.findMonthlyEbookLendsByUserId(userId);

			bookMonthlyData.forEach((k, v) -> monthlyData.merge(k, v, Integer::sum)); // Merge monthly data
			ebookMonthlyData.forEach((k, v) -> monthlyData.merge(k, v, Integer::sum));

			totalCountCategory = list.size();
			totalCountMonth = list.size();

		} else if (type.equals("book")) {
			list = (search != null && !search.isEmpty())
					? new ArrayList<>(historyService.findBooksByTitle(userId, search))
					: new ArrayList<>(historyService.findAllBookByUserId(userId));
			categoryData.putAll(historyService.findBookCategoryDataByUserId(userId));
			totalCountCategory = list.size();
			monthlyData.putAll(historyService.findMonthlyBookLendsByUserId(userId));
			totalCountMonth = list.size();
		} else if (type.equals("ebook")) {
			list = (search != null && !search.isEmpty())
					? new ArrayList<>(historyService.findEbooksByTitle(userId, search))
					: new ArrayList<>(historyService.findAllEbookByUserId(userId));
			categoryData.putAll(historyService.findEbookCategoryDataByUserId(userId));
			totalCountCategory = list.size();
			monthlyData.putAll(historyService.findMonthlyEbookLendsByUserId(userId));
			totalCountMonth = list.size();
		}

		// Adding attributes to the model
		model.addAttribute("myHistoryList", list);
		model.addAttribute("categoryData", categoryData);
		model.addAttribute("monthlyData", monthlyData);
		model.addAttribute("totalCountMonth", totalCountMonth);
		model.addAttribute("totalCountCategory", totalCountCategory);
		model.addAttribute("reviewedBookIds", reviewedBookIds);

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
