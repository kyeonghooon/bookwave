package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.Favorite;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.Like;
import com.library.bookwave.repository.model.Reservation;
import com.library.bookwave.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	/*
	 * 책리스트 페이지
	 */
	@GetMapping("/list")
	public String showBooks(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "30") int size,
			@RequestParam(name = "category", defaultValue = "") String category,
			@RequestParam(name = "search", defaultValue = "") String search) {
		// TODO - 임시 userId 삭제예정
		int userId = 1;

		// 카테고리 불러오기
		List<String> categoryList = bookService.readAllBookCategory();
		
		// 책 조회
		List<BookListDTO> books = bookService.readAllBook(userId, category,search,page , size);
		
		// 필터링 걸린 책 개수 조회
		int booksCount = bookService.countAllBook(category, search);

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

	/*
	 * 상세보기 페이지 
	 */
	@GetMapping("/detail/{bookId}")
	public String showBookDetail(@PathVariable("bookId") int bookId, Model model) {
		// TODO 임시 userId,user1 삭제예정
		int userId = 1; // 임시
		
		// 책 상세보기
		Book bookDetail = bookService.readBook(bookId);
		
		// 대여 조회 
		Lend lend = bookService.readLend(bookId, userId);
		
		// 유저가 도서를 빌린 갯수
		int lendBookCount = bookService.countLendByUserId(userId);

		// 좋아요, 관심등록 체크여부확인 
		Like checkLike = bookService.readLike(userId, bookId);
		boolean isLiked = (checkLike != null);
		Favorite checkFavorite = bookService.readFavorite(userId, bookId);
		boolean isFavorited = (checkFavorite != null);
		
		// 예약 여부 조회
		Reservation reservation = bookService.findReservationByBookIdAndUserId(userId, bookId);
		// 유저가 도서를 예약한 횟수
		int reservationCount = bookService.countReservationByUserId(userId);
		
		// ebook 등록 여부조회
		int userEbook = bookService.readUserEbook(userId, bookId);
		
		model.addAttribute("userEbook",userEbook);
		model.addAttribute("isFavorited", isFavorited);
		model.addAttribute("isLiked", isLiked);
		model.addAttribute("reservation",reservation);
		model.addAttribute("reservationCount",reservationCount);
		model.addAttribute("count",bookDetail.getLikes());
		model.addAttribute("lendBookCount", lendBookCount);
		model.addAttribute("book", bookDetail);
		model.addAttribute("lend", lend);
		return "book/bookDetail";
	}

	/*
	 * 대여기능 추가
	 */
	@GetMapping("/lend/{bookId}")
	public String lendBook(@PathVariable("bookId") int bookId, Model model) {
		// TODO 임시 userId 삭제예정
		int userId = 1; // 임시
		
		boolean isLendSuccessful = bookService.lendBook(bookId, userId);

		// TODO - 필요한가
		if (isLendSuccessful) {
			model.addAttribute("message", "책이 성공적으로 대여되었습니다.");
		} else {
			model.addAttribute("message", "대여에 실패했습니다. 재고가 부족합니다.");
		}

		return "redirect:/book/detail/" + bookId;
	}
	
	/*
	 * 예약하기
	 */
	@GetMapping("/reservation/{bookId}")
	public String reservationBook(@PathVariable("bookId") int bookId, Model model) {
		// TODO 임시 userId 삭제예정
		int userId = 1;
		
		bookService.createReservation(userId, bookId);
		
		return "redirect:/book/detail/" + bookId;
	}

	/*
	 * eBook 등록
	 */
	@GetMapping("/ebook/{bookId}")
	public String createUserEbook(@PathVariable("bookId") int bookId,  Model model) {
		// TODO 임시 userId 삭제예정
		int userId = 1;
		
		// TODO 유저 불리언값 받아오기
		// bookService.createUserEbook(userId, bookId, principal.getSubcribe);
		
		return "redirect:/book/detail/" + bookId;
	}
	
	/*
	 * 좋아요 기능
	 */
	@PostMapping("/like/{bookId}")
	@ResponseBody
	public String createLike(@PathVariable("bookId") int bookId) {
		// TODO 임시 userId 삭제예정
		int userId = 1;

		Like checkLike = bookService.readLike(userId, bookId);

		if (checkLike == null) {
			bookService.createLike(userId, bookId);
			return "liked";
		} else {
			bookService.deleteLike(userId, bookId);
			return "unliked";
		}
	}

	/*
	 * 관심등록 기능
	 */
	@PostMapping("/favorite/{bookId}")
	@ResponseBody
	public String createFavorite(@PathVariable("bookId") int bookId) {
		// TODO 임시 userId 삭제예정
		int userId = 1;
		
		Favorite checkfavorite = bookService.readFavorite(userId, bookId);
		
		if(checkfavorite == null) {
			bookService.createFavorite(userId, bookId);
			return "favorited";
		} else {
			bookService.deleteFavorite(userId, bookId);
			return "unfavorited";
		}
	}
	
	
	
	
	
}
