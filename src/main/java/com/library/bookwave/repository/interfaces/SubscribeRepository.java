package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.Subscribe;

@Mapper
public interface SubscribeRepository {
	
	// 구독 여부 조회
	Subscribe findSubscribeByUserId(Integer userId);
	
}
