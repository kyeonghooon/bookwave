package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.dto.PointDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.UserDetailDTO;
import com.library.bookwave.repository.interfaces.AdminRepository;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;

	// 계정 생성
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

	// 모든 유저 목록 조회
	@Transactional
	public List<PrincipalDTO> readAllUser() {
		List<PrincipalDTO> userList = new ArrayList<>();
		userList = adminRepository.readAllUser();
		return userList;
	}

	// 모든 대출 현황 조회
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
	// 연체기간 지나면 연체로 상태 바꾸기
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateLendStatusAtMidnight() {
		System.out.println("---------------------------");
		System.out.println("자정입니다");
		System.out.println("---------------------------");
		int result = 0;
		try {
			result = adminRepository.updateLendStatus();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 0) {
			System.out.println("연체될 대출현황이 없습니다");
		}
	}

	// 도서 목록 조회
	@Transactional
	public List<BookListDTO> readAllBook() {
		List<BookListDTO> bookList = new ArrayList<>();
		bookList = adminRepository.readAllBook();
		return bookList;
	}

	// 관리자 유저 상세보기
	@Transactional
	public UserDetailDTO readUserById(int userId) {
		return adminRepository.readUserById(userId);
	}

	// 회원 수 조회
	@Transactional
	public int countUser() {
		int count = 0;
		try {
			count = adminRepository.countUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 구독자 수 조회
	@Transactional
	public int countSubscribe() {
		int count = 0;
		try {
			count = adminRepository.countSubscribe();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 모든 유저에게 wave, mileage 지급
	@Transactional
	public void updatePointAllUser(PointDTO point) {
		int result1 = 0;
		try {
			result1 = adminRepository.updateWallet(point);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result1 != 1) {
			System.out.println("wallet 수정 실패");
		}

		int result2 = 0;
		try {
			result2 = adminRepository.createBalanceHisotry(point);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result2 != 1) {
			System.out.println("balance_history 추가 실패");
		}
	}

	// 연령대 회원수 조회
	@Transactional
	public int countAge(int age) {
		int count = 0;
		count = adminRepository.countAge(age);
		return count;
	}

	// 회원 정지
	@Transactional
	public void updateUserStopById(Integer userId) {
		int result = 0;
		try {
			result = adminRepository.updateUserStopById(userId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("회원 정보 수정 실패");
		}

	}
}
