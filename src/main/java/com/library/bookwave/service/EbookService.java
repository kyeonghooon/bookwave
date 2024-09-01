package com.library.bookwave.service;

import org.springframework.stereotype.Service;

import com.library.bookwave.repository.interfaces.EbookRepository;
import com.library.bookwave.repository.model.UserEbook;

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
}
