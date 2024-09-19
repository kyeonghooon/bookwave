package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.Wallet;

@Mapper
public interface WalletRepository {
	
	// 지갑 조회
	Wallet findWalletByUserId(Integer userId);
	
}
