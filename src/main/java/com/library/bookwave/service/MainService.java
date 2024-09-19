package com.library.bookwave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.repository.interfaces.MainRepository;
import com.library.bookwave.repository.model.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {
	
	private final MainRepository mainRepository;
	
	public List<BookListDTO> findBooksByMonthlyLikes(){
		List<BookListDTO> bookList = new ArrayList<>();
		try {
			List<Book> bookListEntity = mainRepository.findBooksByMonthlyLikes();
			for (Book book : bookListEntity) {
				bookList.add(BookListDTO
						.builder()
						.id(book.getId())
						.title(book.getTitle())
						.cover(book.getCover())
						.likes(book.getLikes())
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if (bookList.isEmpty()) {
			// TODO : 처리 필요
		}
		return bookList;
	}
	
	public List<BookListDTO> findBooksByCurrentMonth(){
		List<BookListDTO> bookList = new ArrayList<>();
		try {
			List<Book> bookListEntity = mainRepository.findBooksByCurrentMonth();
			for (Book book : bookListEntity) {
				bookList.add(BookListDTO
						.builder()
						.id(book.getId())
						.title(book.getTitle())
						.cover(book.getCover())
						.createdAt(book.getCreatedAt())
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if (bookList.isEmpty()) {
			// TODO : 처리 필요
		}
		return bookList;
	}
	
	public List<BookListDTO> findBooksByScore(){
		List<BookListDTO> bookList = new ArrayList<>();
		try {
			List<Book> bookListEntity = mainRepository.findBooksByScore();
			for (Book book : bookListEntity) {
				bookList.add(BookListDTO
						.builder()
						.id(book.getId())
						.title(book.getTitle())
						.cover(book.getCover())
						.score(book.getScore())
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if (bookList.isEmpty()) {
			// TODO : 처리 필요
		}
		return bookList;
	}
	
}
