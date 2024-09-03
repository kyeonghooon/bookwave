package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.Book;

@Mapper
public interface BookRepository {

	// 전체 책 조회하기
	public List<Book> readAllBook();

	// 필터링 걸린 책 조회하기
	public List<Book> readBook(@Param("category") String category, @Param("search") String search,
			@Param("limit") Integer limit, @Param("offset") Integer offset);

	// 필터링 걸린 책 갯수 조회하기
	public int countByBook(@Param("category") String category, @Param("search") String search);
}
