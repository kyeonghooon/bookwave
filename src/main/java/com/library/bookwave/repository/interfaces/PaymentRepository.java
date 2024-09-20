package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.PaymentMonthDTO;
import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.Payment;

@Mapper
public interface PaymentRepository {
	// 결제내역 생성
	public int createPayment(Payment payment);

	// 모든 결제내역 조회
	public List<Payment> readAllPayment();

	// 아이디로 결제내역 조회
	public Payment readPaymentById(Integer id);

	// 아이디로 wallet 조회
	public int updateWalletById(@Param("userId") Integer userId, @Param("wave") Integer wave);

	// 아이디로 balance 변동내역 조회
	public int updateBalanceHistoryById(BalanceHistory balanceHistory);

	// 취소사유 추가
	public int updatePaymentCancel(Payment payment);

	// 월 별 결제금액 조회
	public List<PaymentMonthDTO> paymentMonth();

	// 이번 달 결제 금액 조회
	public Long paymentThisMonth();

}
