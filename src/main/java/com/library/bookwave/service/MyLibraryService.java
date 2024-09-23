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
import com.library.bookwave.repository.model.MyBookHistory;
import com.library.bookwave.repository.model.MyEbookHistory;
import com.library.bookwave.repository.model.MyLibrary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyLibraryService {

	private final MyLibraryRepository lendRepository;

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

	public Integer findFirstByBookIdAndStatus(Integer bookId) {
		Integer result = null;
		try {
			result = lendRepository.findFirstByBookIdAndStatus(bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == null) {
			System.err.println("No reservation found for bookId: " + bookId);
		}

		return result;
	}

	public int findBookIdById(Integer id) {
		int result = 0;
		try {
			result = lendRepository.findBookIdById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("err");
		}

		return result;
	}

	@Transactional
	public int updateStatusByIdReservation(Integer id) {
		int result = 0;
		try {
			result = lendRepository.updateStatusByIdReservation(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("err");
		}

		return result;
	}

	@Transactional
	public int updateReturnedDateById(Integer id) {
		int result = 0;
		try {
			result = lendRepository.updateReturnedDateById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("err");
		}

		return result;
	}

	@Transactional
	public int updateWaitDateById(Integer id) {
		int result = 0;
		try {
			result = lendRepository.updateWaitDateById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("err");
		}

		return result;
	}

	@Transactional
	public int updateStockByBookId(Integer id) {
		int result = 0;
		try {
			result = lendRepository.updateStockByBookId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.err.println("err");
		}

		return result;
	}

	public boolean validation(Integer userId, Integer bookId) {
	    System.err.println("Starting validation for userId: " + userId + ", bookId: " + bookId);
	    
	    List<MyLibrary> bookList = readAllById(userId);
	    System.err.println("Retrieved book list for userId " + userId + ": " + bookList);

	    boolean isValid = bookList.stream().anyMatch(book -> book.getBookId().equals(bookId));
	    
	    System.err.println("Validation result for userId " + userId + ", bookId " + bookId + ": " + isValid);
	    
	    return isValid;
	}


}
