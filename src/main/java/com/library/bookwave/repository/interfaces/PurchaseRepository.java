package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.PurchaseHistory;

@Mapper
public interface PurchaseRepository {

	int createPurchaseHistory(PurchaseHistory purchaseHistory);
	
	int createBalanceHistory(BalanceHistory balanceHistory);
	
	int updateWallet(@Param("userId") Integer userId, @Param("wave") Integer wave, @Param("mileage") Integer mileage);
	
	int createSubscribe(Integer userId);
}
