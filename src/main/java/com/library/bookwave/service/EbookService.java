package com.library.bookwave.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.EbookDTO;
import com.library.bookwave.dto.EbookReorderDTO;
import com.library.bookwave.repository.interfaces.EbookRepository;
import com.library.bookwave.repository.model.UserEbook;
import com.library.bookwave.repository.model.UserEbookCategory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EbookService {
	
	private final EbookRepository ebookRepository;
	
	/**
	 *  해당 ebook 상세 내역 조회
	 */
	public UserEbook readUserEbook(int userId, int bookId) {
		UserEbook userEbookEntity = null;
		try {
			userEbookEntity = ebookRepository.readUserEbook(userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (userEbookEntity == null) {
			// TODO : 처리 필요
		}
		return userEbookEntity;
	}
	
	/**
	 * ebook path 조회
	 */
	public String findEbookPathByBookId(int bookId) {
		String ebookPath = null;
		ebookPath = ebookRepository.findEbookPathByBookId(bookId);
		// TODO 테스트 코드 변경 예정
		return ebookPath == null ? "/ebooks/2.epub/" : ebookPath;
	}
	
	/**
	 *  ebook의 마지막 읽은 위치 저장
	 */
	@Transactional
	public int updateUserEbookWithLastPoint(double lastPoint, int userId, int bookId) {
		int result = 0;
		try {
			result = ebookRepository.updateUserEbookWithLastPoint(lastPoint, userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (result == 0) {
			// TODO : 처리 필요
		}
		return result;
	}
	
	/**
	 *  해당 유저의 ebook 목록 받아오기
	 */
	public List<EbookDTO> findEbookListByUserIdAndCategory(int userId, int category){
		List<EbookDTO> bookList = null;
		try {
			bookList = ebookRepository.findEbookListByUserIdAndCategory(userId, category);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (bookList.isEmpty()) {
			// TODO : 처리 필요
		}
		return bookList;
	}
	
	/**
	 *  해당 유저의 ebook 카테고리 목록 받아오기
	 */
	public List<UserEbookCategory> findEbookCategoryListByUserId(int userId){
		List<UserEbookCategory> categoryList = null;
		try {
			categoryList = ebookRepository.findEbookCategoryListByUserId(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (categoryList.isEmpty()) {
			// TODO : 처리 필요
		}
		return categoryList;
	}
	
	/**
	 *  최대 카테고리 수 조회 (없으면 3)
	 */
	public int findEbookCategoryLimitByUserId(int userId) {
		int result = 0;
		try {
			result = ebookRepository.findEbookCategoryLimitByUserId(userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (result == 0) {
			// TODO : 처리 필요
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (result == 0) {
			// TODO : 처리 필요
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
	 *  user ebook의 카테고리 변경
	 */
	@Transactional
	public int updateUserEbookCategory(int categoryId, int userId, int bookId) {
		int result = 0;
		try {
			result = ebookRepository.updateUserEbookCategory(categoryId, userId, bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (result == 0) {
			// TODO : 처리 필요
		}
		return result;
	}
}
