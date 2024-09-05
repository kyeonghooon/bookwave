package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.interfaces.MyHistoryRepository;
import com.library.bookwave.repository.model.MyHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyHistoryService {

	private final MyHistoryRepository historyRepository;

	public List<MyHistory> readAllById(Integer userId) {
		List<MyHistory> list = new ArrayList<>();

		try {
			list = historyRepository.findAllByUserId(userId);
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

}
