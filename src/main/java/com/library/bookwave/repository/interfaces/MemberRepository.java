package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;

@Mapper
public interface MemberRepository {

	// ID 중복확인
	public User readUserId(String loginId); 
	
	// email 조회
	public String eFindByEmail(String email); 
	
	
}
