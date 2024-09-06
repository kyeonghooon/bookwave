package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.LendDTO;
import com.library.bookwave.repository.model.MyReserved;

@Mapper
public interface MyReservedRepository {

	public List<MyReserved> findAllByUserId(Integer userId);

	public int deleteReservedById(Integer id);

	public int updateStatusById(Integer id);

	public int createLend(LendDTO dto);

	public int findCountBeforeByUserIdAndBookId(@Param("userId") int userId, @Param("bookId") int bookId);

}