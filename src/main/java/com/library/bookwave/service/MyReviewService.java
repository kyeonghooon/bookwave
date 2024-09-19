package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
