package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.bookwave.repository.interfaces.MyFavoriteRepository;
import com.library.bookwave.repository.model.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyFavoriteService {

	private final MyFavoriteRepository favoriteRepository;

	public List<Book> findAllByUserId(int userId) {
		List<Book> list = new ArrayList<>();
		try {
			list = favoriteRepository.findAllByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
