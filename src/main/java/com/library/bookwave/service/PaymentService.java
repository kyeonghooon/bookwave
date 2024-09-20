package com.library.bookwave.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.PaymentMonthDTO;
import com.library.bookwave.repository.interfaces.PaymentRepository;
import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	// payment(결제내역) 생성, wallet 수정, balance_history 수정
	@Transactional
	public void createPayment(Payment payment, Integer wave) {
		int result1 = 0;
		try {
			result1 = paymentRepository.createPayment(payment);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result1 != 1) {
			System.out.println("payment 생성 실패");
		}

		int result2 = 0;
		try {
			result2 = paymentRepository.updateWalletById(payment.getUserId(), wave);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result2 != 1) {
			System.out.println("wallet 수정 실패");
		}

		int result3 = 0;
		BalanceHistory balanceHistory = BalanceHistory.builder().userId(payment.getUserId()).waveChange(wave).description(payment.getOrderName()).build();
		try {
			result3 = paymentRepository.updateBalanceHistoryById(balanceHistory);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result3 != 1) {
			System.out.println("balance_history 생성 실패");
		}
	}

	// 모든 결제 내역 조회
	@Transactional
	public List<Payment> readAllPayment() {
		return paymentRepository.readAllPayment();
	}

	// 결제 내역 아이디로 조회
	@Transactional
	public Payment readPaymentById(Integer id) {
		return paymentRepository.readPaymentById(id);
	}

	// payment(결제내역) 수정, wallet 수정, balance_History 수정
	@Transactional
	public void updatePayment(Payment payment) {
		int result1 = 0;
		try {
			result1 = paymentRepository.updatePaymentCancel(payment);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result1 != 1) {
			System.out.println("payment 수정 실패");
		}

		int result2 = 0;
		try {
			result2 = paymentRepository.updateWalletById(payment.getUserId(), -payment.getTotalAmount());
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result2 != 1) {
			System.out.println("wallet 수정 실패");
		}

		int result3 = 0;
		BalanceHistory balanceHistory = BalanceHistory.builder().userId(payment.getUserId()).waveChange(-payment.getTotalAmount()).description(payment.getOrderName()).build();
		try {
			result3 = paymentRepository.updateBalanceHistoryById(balanceHistory);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result3 != 1) {
			System.out.println("balance_history 생성 실패");
		}
	}

	// 월별 결제 금액
	@Transactional
	public List<PaymentMonthDTO> paymentMonth() {
		List<PaymentMonthDTO> paymentMonthList = null;
		try {
			paymentMonthList = paymentRepository.paymentMonth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentMonthList;
	}

	// 이번달 결제 금액
	public Long paymentThisMonth() {
		Long totalAmount = null;
		try {
			totalAmount = paymentRepository.paymentThisMonth();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalAmount;
	}
}
