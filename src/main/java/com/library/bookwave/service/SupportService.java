package com.library.bookwave.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.QnaDTO;
import com.library.bookwave.repository.interfaces.SupportRepository;
import com.library.bookwave.repository.model.Answer;
import com.library.bookwave.repository.model.Faq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupportService {

	private final SupportRepository supportRepository;

	@Transactional
	public void createFaq(Faq faq) {
		int result = 0;
		try {
			result = supportRepository.createFaq(faq);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("FAQ 생성 실패");
		}
	}

	@Transactional
	public void updateFaqById(Faq faq) {
		int result = 0;
		try {
			result = supportRepository.updateFaqById(faq);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("FAQ 수정 실패");
		}
	}

	@Transactional
	public List<Faq> readAllFaq() {
		return supportRepository.readAllFaq();
	}

	@Transactional
	public List<String> readAllCategory() {
		return supportRepository.readAllCategory();
	}

	@Transactional
	public Faq readFaqById(Integer id) {
		return supportRepository.readFaqById(id);
	}

	@Transactional
	public void deleteFaqById(Integer id) {
		int result = 0;
		try {
			result = supportRepository.deleteFaqById(id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("FAQ 삭제 실패");
		}
	}

	@Transactional
	public List<QnaDTO> readAllQna(int page, int size) {
		List<QnaDTO> qnaListEntity = null;
		int limit = size;
		int offset = (page - 1) * size;
		try {
			qnaListEntity = supportRepository.readAllQna(limit, offset);
		} catch (DataAccessException e) {
			//			throw new DataDeliveryException("잘못된 처리 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			//			throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
			e.printStackTrace();
		}
		return qnaListEntity;
	}

	// qna 수정하기 페이지 이동
	@Transactional
	public QnaDTO readQnaById(Integer id) {
		return supportRepository.readQnaById(id);
	}

	// qna 답변하기
	@Transactional
	public void createAnswerByQid(Answer answer) {
		int result = 0;
		try {
			result = supportRepository.createAnswerByQid(answer);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("Q&A 답변 실패");
		}
	}

	// qna 카운트
	@Transactional
	public int countAllQna() {
		return supportRepository.countAllQna();
	}

	// qna 수정하기
	@Transactional
	public void updateAnswerByQid(Answer answer) {
		int result = 0;
		try {
			result = supportRepository.updateQnaById(answer);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("Q&A 수정 실패");
		}
	}

	// qna 검색하기
	@Transactional
	public List<QnaDTO> findQnaByKeyword(String keyword, int page, int size) {
		List<QnaDTO> qnaListEntity = null;
		int limit = size;
		int offset = (page - 1) * size;
		try {
			qnaListEntity = supportRepository.findQnaByKeyword(keyword, limit, offset);
		} catch (DataAccessException e) {
			//			throw new DataDeliveryException("잘못된 처리 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			//			throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
			e.printStackTrace();
		}
		return qnaListEntity;
	}

	
	// qna 검색 카운트
	@Transactional
	public int countQnaByKeyword(String keyword) {
		return supportRepository.countQnaByKeyword();
	}
}
