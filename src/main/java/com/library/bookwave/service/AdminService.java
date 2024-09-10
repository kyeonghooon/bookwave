package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.repository.interfaces.AdminRepository;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;

	@Transactional
	public void createUser(User dto) {
		int result = 0;
		try {
			result = adminRepository.insert(dto);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("계정생성 실패");
		}
	}

	@Transactional
	public List<User> readAllUser() {
		List<User> userList = new ArrayList<>();
		userList = adminRepository.readAllUser();
		return userList;
	}

	@Transactional
	public List<Lend> readAllLend() {
		List<Lend> lendList = new ArrayList<>();
		lendList = adminRepository.readAllLend();
		return lendList;
	}

	//	// 매 5초마다 실행
	//	@Scheduled(fixedRate = 5000)
	//	public void executeTask() {
	//		System.out.println("5초마다 실행되는 작업");
	//	}

	// 매일 자정에 실행
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateLendStatusAtMidnight() {
		System.out.println("---------------------------");
		System.out.println("자정에 실행");
		System.out.println("---------------------------");
		int result = 0;
		try {
			result = adminRepository.updateLendStatus();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("업데이트 할 데이터가 없습니다");
		}
	}

}
