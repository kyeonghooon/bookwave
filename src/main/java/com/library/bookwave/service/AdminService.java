package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.repository.interfaces.AdminRepository;
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

}
