package com.library.bookwave.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.repository.model.Book;
import com.library.bookwave.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	// 책 리스트 페이지
	@GetMapping("/list")
	public String showBooks(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "30") int size,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "search", required = false) String search) {

		Set<String> categoryList = bookService.getCategories();
		List<Book> books = bookService.readBook(category, search, page, size);
		int booksCount = bookService.countByBook(category, search);

		// 페이지네이션 처리
		int totalPages = (int) Math.ceil((double) booksCount / size);
		int startBlock = Math.max(1, ((page - 1) / 5) * 5 + 1);
		int endBlock = Math.min(totalPages, startBlock + 4);

		// 모델에 필요한 정보 추가
		model.addAttribute("books", books);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("startBlock", startBlock);
		model.addAttribute("endBlock", endBlock);
		model.addAttribute("selectedCategory", category);
		model.addAttribute("searchQuery", search);
		model.addAttribute("categories", categoryList);

		return "book/bookList";
	}

}
