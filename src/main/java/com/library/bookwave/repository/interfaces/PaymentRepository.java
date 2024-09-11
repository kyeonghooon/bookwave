package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.Payment;

@Mapper
public interface PaymentRepository {
	public int createPayment(Payment payment);

	public List<Payment> readAllPayment();

	public Payment readPaymentById(Integer id);

	public int updateWalletById(@Param("userId") Integer userId, @Param("wave") Integer wave);

	public int updateBalanceHistoryById(BalanceHistory balanceHistory);

	public int updatePaymentCancel(Payment payment);

}