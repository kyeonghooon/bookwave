package com.library.bookwave.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.repository.interfaces.PaymentRepository;
import com.library.bookwave.repository.model.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	@Transactional
	public void createPayment(Payment payment, Long wave) {
		int result1 = 0;
		try {
			result1 = paymentRepository.createPayment(payment);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result1 != 1) {
			System.out.println("결제내역 생성 실패");
		}

		int result2 = 0;
		try {
			result2 = paymentRepository.updateWaveById(payment.getUserId(), wave);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result2 != 1) {
			System.out.println("wave충전 업데이트 실패");
		}
	}

	@Transactional
	public List<Payment> readAllPayment() {
		return paymentRepository.readAllPayment();
	}

	@Transactional
	public Payment readPaymentById(Integer id) {
		return paymentRepository.readPaymentById(id);
	}

	// 결제 취소 후 상태,wave 수정
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
			System.out.println("결제상태 수정 실패");
		}

		int result2 = 0;
		try {
			result2 = paymentRepository.updateWaveById(payment.getUserId(), -payment.getTotalAmount());
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result2 != 1) {
			System.out.println("유저 wave 업데이트 실패");
		}
	}
}
