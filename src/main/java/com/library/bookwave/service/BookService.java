package com.library.bookwave.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.repository.interfaces.BookRepository;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.Favorite;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.Like;
import com.library.bookwave.repository.model.Reservation;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// 도서 목록 조회
	public List<BookListDTO> readAllBook(Integer userId, String catgory, String search, Integer page, Integer size) {
		List<BookListDTO> books = null;
		try {
			int limit = size;
			int offset = (page - 1) * size;
			books = bookRepository.readAllBook(userId, catgory, search, limit, offset);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return books;

	}

	// 카테고리 조회
	public List<String> readAllBookCategory() {
		List<String> categories = null;
		try {
			categories = bookRepository.readAllBookCategory();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return categories;
	}

	// 필터링 걸린 도서 갯수 조회하기
	public int countAllBook(String category, String search) {
		int count = 0;
		try {
			count = bookRepository.countAllBook(category, search);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	// 도서 아이디로 도서 조회하기
	public Book readBook(Integer bookId) {
		Book book = null;
		try {
			book = bookRepository.readBook(bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return book;
	}

	// 좋아요 기능
	public Like readLike(Integer userId, Integer bookId) {
		Like like = null;
		try {
			like = bookRepository.readLike(userId, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return like;
	}

	@Transactional
	public void createLike(Integer userId, Integer bookId) {
		try {
			bookRepository.createLike(userId, bookId);
			bookRepository.updateBookLikeUp(bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void deleteLike(Integer userId, Integer bookId) {
		try {
			bookRepository.deleteLike(userId, bookId);
			bookRepository.updateBookDown(bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 관심등록 기능
	public Favorite readFavorite(Integer userId, Integer bookId) {
		Favorite favorite = null;
		try {
			favorite = bookRepository.readFavorite(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return favorite;
	}

	public void createFavorite(Integer userId, Integer bookId) {
		try {
			bookRepository.createFavorite(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void deleteFavorite(Integer userId, Integer bookId) {
		try {
			bookRepository.deleteFavorite(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 예약하기
	public Reservation findReservationByBookIdAndUserId(Integer userId, Integer bookId) {
		Reservation reservation = null;
		try {
			reservation = bookRepository.findReservationByBookIdAndUserId(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return reservation;
	}

	public void createReservation(Integer userId, Integer bookId) {
		try {
			bookRepository.createReservation(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public int countReservationByUserId(Integer userId) {
		int count = 0;
		try {
			count = bookRepository.countReservationByUserId(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	// 도서 대여
	@Transactional
	public boolean lendBook(Integer bookId, Integer userId) {
		boolean status = false;
		try {
			int currentStock = bookRepository.readBookCurrentStock(bookId);
			if (currentStock > 0) {
				bookRepository.updateBookCurrentStock(bookId);
				bookRepository.createLend(userId, bookId);
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return status;
	}

	public Lend readLend(Integer bookId, Integer userId) {
		Lend lend = null;
		try {
			lend = bookRepository.readLend(bookId, userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lend;
	}

	public int countLendByUserId(Integer userId) {
		int count = 0;
		try {
			count = bookRepository.countLendByUserId(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

	// eBook 등록
	public void createUserEbook(Integer userId, Integer bookId, Boolean subscribe) {

		try {
			bookRepository.createUserEbook(userId, bookId, subscribe);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// eBook 여부 조회
	public int readUserEbook(Integer userId, Integer bookId) {
		int userEbook = 0;
		try {
			userEbook = bookRepository.readUserEbook(userId, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userEbook;
	}
}
