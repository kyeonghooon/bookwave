package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.LendDTO;
import com.library.bookwave.repository.interfaces.MyReservedRepository;
import com.library.bookwave.repository.model.MyReserved;

@Service
public class MyReservedService {

	@Autowired
	MyReservedRepository reservedRepository;

	public List<MyReserved> readAllById(Integer userId) {
		List<MyReserved> list = new ArrayList<>();

		try {
			list = reservedRepository.findAllByUserId(userId);
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
	public int deleteReservedById(Integer id) {

		int result = 0;
		try {
			result = reservedRepository.deleteReservedById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == 0) {
			System.err.println("데이터가 삭제되지 않음");
		}

		return result;
	}

	@Transactional
	public int updateStatusById(Integer id) {
		int result = 0;
		try {
			result = reservedRepository.updateStatusById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == 0) {
			System.err.println("데이터가 업데이트되지 않음");
		}

		return result;

	}

	@Transactional
	public int createLend(LendDTO dto) {
		int result = 0;
		try {
			result = reservedRepository.createLend(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == 0) {
			System.err.println("데이터 인서트 x");
		}

		return result;

	}

	@Transactional
	public void processLendAndUpdateStatus(LendDTO dto, Integer id) {
		try {
			int result = reservedRepository.createLend(dto);
			if (result == 0) {
				throw new RuntimeException("Failed to insert into lend_tb");
			}
			reservedRepository.updateStatusById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int findCountBeforeByUserIdAndBookId(Integer userId, Integer bookId) {

		int result = 0;
		try {
			result = reservedRepository.findCountBeforeByUserIdAndBookId(userId, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}