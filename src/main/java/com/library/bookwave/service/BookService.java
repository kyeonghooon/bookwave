package com.library.bookwave.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.repository.interfaces.BookRepository;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.BookCategory;
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
	public List<BookListDTO> readAllBook(Integer userId, Integer category, String search, Integer page, Integer size) {
		List<BookListDTO> books = null;
		try {
			int limit = size;
			int offset = (page - 1) * size;
			books = bookRepository.readAllBook(userId, category, search, limit, offset);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return books;

	}

	// 카테고리 조회
	public List<BookCategory> readAllBookCategory() {
		List<BookCategory> categories = null;
		try {
			categories = bookRepository.readAllBookCategory();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return categories;
	}

	// (테이블구조 수정) 카테고리 조회
	public List<String> readAllBookCategory2() {
		List<String> categories = null;
		try {
			categories = bookRepository.readAllBookCategory2();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return categories;
	}

	// 필터링 걸린 도서 갯수 조회하기
	public int countAllBook(Integer category, String search) {
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

	// 도서 등록
	@Transactional
	public void createBook(Book book) {
		int result = 0;
		try {
			result = bookRepository.createBook(book);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("도서 수정 실패");
		}
	}

	// 도서 수정
	@Transactional
	public void updateBookById(Book book) {
		int result = 0;
		try {
			result = bookRepository.updateBookById(book);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("도서 수정 실패");
		}
	}

	// 도서 삭제
	@Transactional
	public void deleteBookById(Integer bookId) {
		int result = 0;
		try {
			result = bookRepository.deleteBookById(bookId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("도서 삭제 실패");
		}

	}

	// 총 도서 수
	@Transactional
	public int countBook() {
		int count = 0;
		try {
			count = bookRepository.countBook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 대출 중인 도서 수
	@Transactional
	public int countLendBook() {
		int count = 0;
		try {
			count = bookRepository.countLendBook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 반납 하루 남은 도서 수
	@Transactional
	public int countDueBook() {
		int count = 0;
		try {
			count = bookRepository.countDueBook();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
