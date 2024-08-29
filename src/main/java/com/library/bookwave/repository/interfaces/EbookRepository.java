package com.library.bookwave.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.UserEbook;

@Mapper
public interface EbookRepository {
	
	// user_ebook_tb 조회
	UserEbook readUserEbook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
	
	// ebook path 조회
	String findEbookPathByBookId(Integer bookId);
}
