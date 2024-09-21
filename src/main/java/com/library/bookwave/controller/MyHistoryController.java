package com.library.bookwave.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.MyBookHistory;
import com.library.bookwave.repository.model.MyEbookHistory;
import com.library.bookwave.service.MyHistoryService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class MyHistoryController {

	private final MyHistoryService historyService;

	@GetMapping("/list")
	public String showList(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@RequestParam(value = "type", required = false, defaultValue = "all") String type,
			@RequestParam(value = "search", required = false) String search, Model model) {

		int userId = principal.getUserId();
    
		List<Object> list = new ArrayList<>();
		final Map<String, Integer> categoryData = new HashMap<>();
		final Map<String, Integer> monthlyData = new HashMap<>();
		Integer totalCountCategory = null;
		Integer totalCountMonth = null;


		// Validate search parameter
		if (search != null && search.length() > 20) {
			model.addAttribute("errorMessage", "제목은 20자 이상 입력할 수 없습니다.");
			return "myHistory/history";
		}

		// Validate type parameter
		if (!type.equals("all") && !type.equals("book") && !type.equals("ebook")) {
			model.addAttribute("errorMessage", "유효하지 않은 타입입니다.");
			return "myHistory/history";
		}


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


			Map<String, Integer> bookCategoryData = historyService.findBookCategoryDataByUserId(userId);
			Map<String, Integer> ebookCategoryData = historyService.findEbookCategoryDataByUserId(userId);
			bookCategoryData.forEach((k, v) -> categoryData.merge(k, v, Integer::sum));
			ebookCategoryData.forEach((k, v) -> categoryData.merge(k, v, Integer::sum));

			Map<String, Integer> bookMonthlyData = historyService.findMonthlyBookLendsByUserId(userId);
			Map<String, Integer> ebookMonthlyData = historyService.findMonthlyEbookLendsByUserId(userId);
			bookMonthlyData.forEach((k, v) -> monthlyData.merge(k, v, Integer::sum));

			ebookMonthlyData.forEach((k, v) -> monthlyData.merge(k, v, Integer::sum));

			totalCountCategory = list.size();
			totalCountMonth = list.size();

		} else if (type.equals("book")) {

			List<MyBookHistory> bookList = (search != null && !search.isEmpty())
					? historyService.findBooksByTitle(userId, search)
					: historyService.findAllBookByUserId(userId);
			list.addAll(bookList);

			categoryData.putAll(historyService.findBookCategoryDataByUserId(userId));
			totalCountCategory = list.size();
			monthlyData.putAll(historyService.findMonthlyBookLendsByUserId(userId));
			totalCountMonth = list.size();
		} else if (type.equals("ebook")) {

			List<MyEbookHistory> ebookList = (search != null && !search.isEmpty())
					? historyService.findEbooksByTitle(userId, search)
					: historyService.findAllEbookByUserId(userId);
			list.addAll(ebookList);

			categoryData.putAll(historyService.findEbookCategoryDataByUserId(userId));
			totalCountCategory = list.size();
			monthlyData.putAll(historyService.findMonthlyEbookLendsByUserId(userId));
			totalCountMonth = list.size();
		}


		model.addAttribute("myHistoryList", list);
		model.addAttribute("categoryData", categoryData);
		model.addAttribute("monthlyData", monthlyData);
		model.addAttribute("totalCountMonth", totalCountMonth);
		model.addAttribute("totalCountCategory", totalCountCategory);
		model.addAttribute("reviewedBookIds", reviewedBookIds);

		return "myHistory/history";
	}

}
