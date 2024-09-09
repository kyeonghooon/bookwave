package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;

@Mapper
public interface UserRepository {
	
	// user 생성
	public int createUser(User user);
	// userId 값 추출
	public User findById(String login_id);
	// 
	public List<UserDetail> findAll();
	// 회원가입
	public int createUserDetail(@Param("id") Integer id, @Param("userDetail") UserDetail userDetail);
	

//	 (로그인 (아이디, 비밀번호))
	public int findAllByUser(User user);
	
	// 로그인 아이디
	public User findByUserIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
	
	/**
	 * 소셜
	 */
	// 
	public int createSocialId(User user);
	public User findBySocialId(String social_id);

}
