package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.User;

@Mapper
public interface MemberRepository {

	// ID 중복확인
	public User readUserId(String loginId); 
	
	
}
