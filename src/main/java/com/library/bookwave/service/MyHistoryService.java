package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.interfaces.MyHistoryRepository;
import com.library.bookwave.repository.model.MyBookHistory;
import com.library.bookwave.repository.model.MyEbookHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyHistoryService {

	private final MyHistoryRepository historyRepository;

	public List<MyBookHistory> findAllBookByUserId(Integer userId) {
		List<MyBookHistory> list = new ArrayList<>();
		try {
			list = historyRepository.findAllBookByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<MyEbookHistory> findAllEbookByUserId(Integer userId) {
		List<MyEbookHistory> list = new ArrayList<>();
		try {
			list = historyRepository.findAllEbookByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Transactional
	public int createReview(ReviewDTO dto) {
		int result = 0;
		try {
			result = historyRepository.createReview(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// should not be called directly, only by findBookCategoryDataByUserId method
	public List<String> findAllBookCategoryByUserId(Integer userId) {
		List<String> list = new ArrayList<>();
		try {
			list = historyRepository.findAllBookCategoryByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// should not be called directly, only by findEbookCategoryDataByUserId method
	public List<String> findAllEbookCategoryByUserId(Integer userId) {
		List<String> list = new ArrayList<>();
		try {
			list = historyRepository.findAllEbookCategoryByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Map<String, Integer> findBookCategoryDataByUserId(int userId) {
		List<String> categories = historyRepository.findAllBookCategoryByUserId(userId);

		Map<String, Integer> categoryData = new HashMap<>();

		for (String category : categories) {
			int count = historyRepository.findAllBookCategoryCountByUserId(userId, category);
			categoryData.put(category, count);
		}

		return categoryData;
	}

	public Map<String, Integer> findEbookCategoryDataByUserId(int userId) {
		List<String> categories = historyRepository.findAllEbookCategoryByUserId(userId);

		Map<String, Integer> categoryData = new HashMap<>();

		for (String category : categories) {
			int count = historyRepository.findAllEbookCategoryCountByUserId(userId, category);
			categoryData.put(category, count);
		}

		return categoryData;
	}

	public Map<String, Integer> findMonthlyBookLendsByUserId(int userId) {
		List<Map<String, Object>> results = historyRepository.findMonthlyBookLendsByUserId(userId);

		Map<String, Integer> monthlyLends = new HashMap<>();

		for (Map<String, Object> result : results) {
			String month = (String) result.get("MONTH");
			Number totalLends = (Number) result.get("COUNT(*)");

			monthlyLends.put(month, totalLends.intValue());
		}

		return monthlyLends;
	}

	public Map<String, Integer> findMonthlyEbookLendsByUserId(int userId) {
		List<Map<String, Object>> results = historyRepository.findMonthlyEbookLendsByUserId(userId);

		Map<String, Integer> monthlyLends = new HashMap<>();

		for (Map<String, Object> result : results) {
			String month = (String) result.get("MONTH");
			Number totalLends = (Number) result.get("COUNT(*)");

			monthlyLends.put(month, totalLends.intValue());
		}

		return monthlyLends;
	}

	public List<Integer> getReviewedBookIdsByUserId(Integer userId) {
		return historyRepository.findReviewedBookIdsByUserId(userId);
	}

	public List<MyBookHistory> findBooksByTitle(int userId, String search) {
		return historyRepository.findBooksByTitle(userId, search);
	}

	public List<MyEbookHistory> findEbooksByTitle(int userId, String search) {
		return historyRepository.findEbooksByTitle(userId, search);
	}

}
