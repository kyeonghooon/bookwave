package com.library.bookwave.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.BalanceHistoryDTO;
import com.library.bookwave.dto.PaymentHistroyDTO;
import com.library.bookwave.dto.UserInfoDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.repository.interfaces.MyPageRepository;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.Wallet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final MyPageRepository myPageRepository;

	public User findUserById(Integer Id) {
		User user = null;
		try {
			user = myPageRepository.findUserById(Id);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	public UserInfoDTO findUserDetailById(Integer userId) {
		UserInfoDTO userInfoDTO = null;
		try {
			userInfoDTO = myPageRepository.findUserDetailById(userId);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
		return userInfoDTO;
	}

	@Transactional
	public void updateAddress(String zip, String addr1, String addr2, Integer userId) {
		if (zip == null || zip.isEmpty()) {
			throw new DataDeliveryException("우편번호가 비어있습니다.", HttpStatus.BAD_REQUEST);
		}
		try {
			myPageRepository.updateAddress(zip, addr1, addr2, userId);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public void updatePhone(String phone, Integer userId) {
		if (phone == null || phone.isEmpty()) {
			throw new DataDeliveryException("전화번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if (!(phone.length() == 13) && !(phone.length() == 12)) {
			throw new DataDeliveryException("전화번호를 제대로 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		try {
			myPageRepository.updatePhone(phone, userId);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public boolean updateUserByPassword(String newPassword, Integer id) {
		int result = 0;
		try {
			result = myPageRepository.updateUserByPassword(newPassword, id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int countBalanceHistory(Integer userId, String historyType) {
		int count = 0;
		try {
			count = myPageRepository.countBalanceHistory(userId, historyType);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
		return count;
	}

	public List<BalanceHistoryDTO> findBalanceHistory(Integer userId, Integer page, Integer size, String sortBy,
			String sortOrder, String historyType) {
		List<BalanceHistoryDTO> balanceHistory = null;
		try {
			int limit = size;
			int offset = (page - 1) * size;
			balanceHistory = myPageRepository.findBalanceHistory(userId, limit, offset, sortBy, sortOrder, historyType);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
		return balanceHistory;
	}

	public List<PaymentHistroyDTO> findPaymentByUserId(Integer userId) {
		List<PaymentHistroyDTO> payment = null;
		try {
			payment = myPageRepository.findPaymentByUserId(userId);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
		return payment;
	}

	public PaymentHistroyDTO findPaymentById(Integer id) {
		PaymentHistroyDTO payment = null;
		try {
			payment = myPageRepository.findPaymentById(id);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
		return payment;
	}

	@Transactional
	public void updatePayment(Integer id, String cancelReason) {
		try {
			myPageRepository.updatePayment(id, cancelReason);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public void updateUserStatus(Integer userId) {
		try {
			myPageRepository.updateUserStatus(userId);
		} catch (Exception e) {
			throw new DataDeliveryException("오류 발생", HttpStatus.NOT_FOUND);
		}
	}
	
	public Wallet readAllWalletByUserId(Integer userId) {
		Wallet wallet = null;
		try {
			wallet = myPageRepository.readAllWalletByUserId(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return wallet;
	}
	
	public void updateWallet(Integer totalAmount, Integer userId) {
		try {
			myPageRepository.updateWallet(totalAmount, userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean updateUserEmailByUserId(String email, Integer userId) {
		int result = 0;
		try {
			result = myPageRepository.updateUserEmailByUserId(email, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
