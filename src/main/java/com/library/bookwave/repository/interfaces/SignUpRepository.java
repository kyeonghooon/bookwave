package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;

@Mapper
public interface SignUpRepository {
	
	// user 생성
	public int createUser(User user);
	// userId 값 추출
	public User findById(String login_id);
	// 
	public List<UserDetail> findAll();
	
//	TODO 삭제 예정 (로그인 (아이디, 비밀번호))
//	public int findAllByUser(User user);

	// 회원가입
	public int createUserDetail(@Param("id") Integer id, @Param("userDetail") UserDetail userDetail);

}
