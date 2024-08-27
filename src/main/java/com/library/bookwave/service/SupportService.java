package com.library.bookwave.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.repository.interfaces.SupportRepository;
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
}
