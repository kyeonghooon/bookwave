package com.library.bookwave.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.BalanceHistoryDTO;
import com.library.bookwave.repository.interfaces.MyReviewRepository;
import com.library.bookwave.repository.model.Review;

@Service
public class MyReviewService {

	@Autowired
	MyReviewRepository reviewRepository;

	public List<Review> findAllByUserId(Integer userId) {
		List<Review> reviewList = new ArrayList<>();
		try {
			reviewList = reviewRepository.findAllByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reviewList;
	}

	public int updateById(Integer id, Integer score, String content) {

		int result = 0;
		try {
			result = reviewRepository.updateById(id, score, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == 0) {
			System.err.println("update error");
		}

		return result;
	}

	public int deleteById(Integer id) {

		int result = 0;
		try {
			result = reviewRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == 0) {
			System.err.println("delete error");
		}

		return result;
	}

	public Review findDetailById(Integer id) {

		Review review = reviewRepository.findDetailById(id);

		return review;
	}

	public boolean validation(Integer userId, Integer id) {

		List<Review> reviewList = findAllByUserId(userId);

		return reviewList.stream().anyMatch(review -> review.getId().equals(id));
	}

	public Timestamp trimTimestamp(Timestamp timestamp) {
		timestamp.setTime(timestamp.getTime() / 1000 * 1000);
		timestamp.setNanos(0);
		return timestamp;
	}

	public Date getCurrentDate() {
		long currentTimeInSeconds = System.currentTimeMillis() / 1000;
		return new Date(currentTimeInSeconds * 1000);
	}

	@Transactional
	public int updateWalletByUserId(Integer userId, Integer amount) {
		int result = 0;
		try {
			result = reviewRepository.updateWalletByUserId(userId, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Transactional
	public int createBalanceHistoryByUserId(BalanceHistoryDTO dto) {
		int result = 0;
		try {
			result = reviewRepository.createBalanceHistoryByUserId(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Transactional
	public int updateStatusById(Integer id) {
		int result = 0;
		try {
			result = reviewRepository.updateStatusById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean selectReviewById(Integer id) {

		Review review = reviewRepository.selectReviewById(id);

		if (review.getStatus() == 1) {
			return true;
		} else {
			return false;
		}

	}

}
