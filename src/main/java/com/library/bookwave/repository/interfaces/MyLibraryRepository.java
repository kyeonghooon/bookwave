package com.library.bookwave.repository.interfaces;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.MyLibrary;

@Mapper
public interface MyLibraryRepository {

	public List<MyLibrary> findAllByUserId(Integer userId);

	public int updateStatusById(Integer id);

	public int updateReturnDateById(@Param("id") Integer id, @Param("returnDate") Timestamp returnDate);

	public Timestamp getCurrentReturnDateById(Integer Id);

}
