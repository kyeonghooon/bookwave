package com.library.bookwave.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.library.bookwave.repository.interfaces.BookRepository;
import com.library.bookwave.repository.model.Book;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> readAllBook() {
		return bookRepository.readAllBook();
	}

	public List<Book> readBook(String category, String search, int page, int size) {
		int limit = size;
		int offset = (page - 1) * size;
		return bookRepository.readBook(category, search, limit, offset);
	}

	public int countByBook(String category, String search) {
		return bookRepository.countByBook(category, search);
	}

	public Book readId(int id) {
		return bookRepository.readId(id);
	}

	// 카테고리 목록을 생성하는 메서드
	public Set<String> getCategories() {
		List<Book> books = bookRepository.readAllBook();
		Set<String> categories = new HashSet<>();
		for (Book book : books) {
			categories.add(book.getCategory());
		}
		return categories;
	}

}
