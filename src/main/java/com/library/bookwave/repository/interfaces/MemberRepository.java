package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.User;

@Mapper
public interface MemberRepository {

	// ID 중복확인
	public User readUserId(String loginId); 
	
	// email 조회 (아이디 찾기)
	public String eFindByEmail(String email); 
	
	// 비밀번호 찾기
	public Integer eFindByIdAndEmail(@Param("loginId") String loginId, @Param("email") String email);
	
	// 새로운 비밀번호 발급
	public int newPassword(@Param("loginId") String loginId, @Param("password") String password);
	
}
