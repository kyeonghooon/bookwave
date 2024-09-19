package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.Book;

@Mapper
public interface MainRepository {
	
	// 지난 달의 인기도서 조회
	List<Book> findBooksByMonthlyLikes();
	
	// 이달의 신규 도서 조회
	List<Book> findBooksByCurrentMonth();
	
	// 전체 평점 순위
	List<Book> findBooksByScore();
}
