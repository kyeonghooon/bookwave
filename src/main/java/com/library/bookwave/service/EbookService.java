package com.library.bookwave.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.EbookDTO;
import com.library.bookwave.dto.EbookReorderDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.handler.exception.RedirectException;
import com.library.bookwave.repository.interfaces.BookRepository;
import com.library.bookwave.repository.interfaces.EbookRepository;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.UserEbook;
import com.library.bookwave.repository.model.UserEbookCategory;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EbookService {

	private final EbookRepository ebookRepository;
	private final BookRepository bookRepository;

	/**
	 * 해당 ebook 상세 내역 조회
	 */
	public UserEbook readUserEbook(int userId, int bookId) {
		UserEbook userEbookEntity = null;
		try {
			userEbookEntity = ebookRepository.readUserEbook(userId, bookId);
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return userEbookEntity;
	}

	/**
	 * ebook path 조회
	 */
	public Book findEbookPathByBookId(int bookId) {
		Book bookEntity = null;
		try {
			bookEntity = bookRepository.readBook(bookId);
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		// 현재 ebook은 하나 밖에 없어서 임시로 작성됨
		String ebookPath = bookEntity.getEbookPath() == null ? "/ebooks/2.epub/" : bookEntity.getEbookPath();
		bookEntity.setEbookPath(ebookPath);
		return bookEntity;
	}

	/**
	 * ebook의 마지막 읽은 위치 저장
	 */
	@Transactional
	public boolean updateUserEbookWithLastPoint(double lastPoint, int userId, int bookId) {
		try {
			ebookRepository.updateUserEbookWithLastPoint(lastPoint, userId, bookId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 해당 유저의 ebook 목록 받아오기
	 */
	public List<EbookDTO> findEbookListByUserIdAndCategory(int userId, int category) {
		List<EbookDTO> bookList = null;
		try {
			bookList = ebookRepository.findEbookListByUserIdAndCategory(userId, category);
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return bookList;
	}

	/**
	 * 해당 유저의 ebook 카테고리 목록 받아오기
	 */
	public List<UserEbookCategory> findEbookCategoryListByUserId(int userId) {
		List<UserEbookCategory> categoryList = null;
		try {
			categoryList = ebookRepository.findEbookCategoryListByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return categoryList;
	}

	/**
	 * 최대 카테고리 수 조회 (없으면 3)
	 */
	public int findEbookCategoryLimitByUserId(int userId) {
		int result = 0;
		try {
			result = ebookRepository.readEbookCategoryLimit(userId);
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return result;
	}

	/**
	 * 현재 카테고리 수 조회
	 */
	public int countEbookCategoryByUserId(int userId) {
		int result = 0;
		try {
			result = ebookRepository.countEbookCategoryByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return result;
	}

	/**
	 * ebook 카테고리 생성
	 */
	@Transactional
	public boolean createUserEbookCategory(int userId, String category) {
		int result = 0;
		try {
			result = ebookRepository.createUserEbookCategory(userId, category);
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	/**
	 * ebook 카테고리 명 변경
	 */
	@Transactional
	public boolean updateUserEbookCategoryName(int userId, String categoryName, int categoryId) {
		int result = 0;
		try {
			result = ebookRepository.updateUserEbookCategoryName(userId, categoryName, categoryId);
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	/**
	 * ebook 순서 변경
	 */
	@Transactional
	public boolean updateUserEbookPriority(int userId, List<EbookReorderDTO> ebookReorderList) {
		int result = 0;
		try {
			result = ebookRepository.updateUserEbookPriority(userId, ebookReorderList);
			System.err.println(result);
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	/**
	 * user ebook의 카테고리 변경
	 */
	@Transactional
	public boolean updateUserEbookCategory(int categoryId, int userId, int bookId) {
		int result = 0;
		try {
			result = ebookRepository.updateUserEbookCategory(categoryId, userId, bookId);
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	/**
	 * ebook 등록 (구독 서비스 이용자)
	 */
	public boolean createEbookWithSubscribe(int userId, int bookId) {
		int result = 0;
		try {
			result = ebookRepository.createEbookWithSubscribe(userId, bookId);
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}
}
