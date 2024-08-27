package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.User;

@Mapper
public interface AdminRepository {
	public int insert(User user);

	public List<User> readAllUser();
}
