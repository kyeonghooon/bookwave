package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.Book;

@Mapper
public interface MyFavoriteRepository {

	public List<Book> findAllByUserId(Integer userId);

}
