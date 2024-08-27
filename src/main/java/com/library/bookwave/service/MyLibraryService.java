package com.library.bookwave.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.repository.interfaces.MyLibraryRepository;
import com.library.bookwave.repository.model.MyLibrary;

@Service
public class MyLibraryService {

	@Autowired
	MyLibraryRepository lendRepository;

	public List<MyLibrary> readAllById(Integer userId) {
		List<MyLibrary> list = new ArrayList<>();

		try {
			list = lendRepository.findAllByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.isEmpty() || list == null) {
			// TODO delete logging
			System.err.println("list error");
		}

		return list;
	}

	@Transactional
	public int updateStatusById(Integer id) {
		int result = 0;
		try {
			result = lendRepository.updateStatusById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("update error");
		}

		return result;
	}

	@Transactional
	public int updateReturnDateById(Integer bookId, Integer days) {
		int result = 0;
		try {
			// Fetch the current return date from the database
			Timestamp currentReturnDateTimestamp = lendRepository.getCurrentReturnDateById(bookId);

			if (currentReturnDateTimestamp == null) {
				throw new IllegalArgumentException("Current return date is null");
			}

			// Convert Timestamp to LocalDateTime
			LocalDateTime currentReturnDate = currentReturnDateTimestamp.toLocalDateTime();

			// Calculate the new return date
			LocalDateTime newReturnDate = currentReturnDate.plusDays(days);

			// Convert LocalDateTime back to Timestamp
			Timestamp returnDate = Timestamp.from(newReturnDate.atZone(ZoneId.systemDefault()).toInstant());

			System.err.println(bookId);
			System.err.println(bookId);
			System.err.println(bookId);
			System.err.println(returnDate);
			System.err.println(returnDate);
			System.err.println(returnDate);
			// Update the return date in the database
			result = lendRepository.updateReturnDateById(bookId, returnDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("Update error");
		}

		return result;
	}

}
