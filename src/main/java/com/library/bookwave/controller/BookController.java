package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.BookDetailReviewDTO;
import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.BookCategory;
import com.library.bookwave.repository.model.Favorite;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.Like;
import com.library.bookwave.repository.model.Reservation;
import com.library.bookwave.service.BookService;
import com.library.bookwave.service.ItemService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	private final ItemService itemService;


	/* 책리스트 페이지 */
	@GetMapping("/list")
	public String showBooks(//
			Model model,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,//
			@RequestParam(name = "page", defaultValue = "1") int page,//
			@RequestParam(name = "size", defaultValue = "30") int size,//
			@RequestParam(name = "category", defaultValue = "0") Integer category,//
			@RequestParam(name = "search", defaultValue = "") String search) {

		int userId = principal.getUserId();

		// 카테고리 불러오기
		List<BookCategory> categoryList = bookService.readAllBookCategory();

		// 책 조회
		List<BookListDTO> books = bookService.readAllBook(userId, category, search, page, size);

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
		model.addAttribute("categoryList", categoryList);

		return "book/bookList";
	}

	/* 상세보기 페이지 */
	@GetMapping("/detail/{bookId}")
	public String showBookDetail(//
			@PathVariable("bookId") int bookId,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,//
			Model model) {

		int userId = principal.getUserId();

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

		List<BookDetailReviewDTO> review = bookService.readReviewAndUserNameByBookId(bookId);

		model.addAttribute("principal", principal);
		model.addAttribute("review", review);
		model.addAttribute("userEbook", userEbook);

		// 상세 페이지에 들어가는 아이템 리스트 세팅
		String itemsJson = itemService.findItemsByPageName("bookDetail");

		model.addAttribute("isFavorited", isFavorited);
		model.addAttribute("isLiked", isLiked);
		model.addAttribute("reservation", reservation);
		model.addAttribute("reservationCount", reservationCount);
		model.addAttribute("count", bookDetail.getLikes());
		model.addAttribute("lendBookCount", lendBookCount);
		model.addAttribute("book", bookDetail);
		model.addAttribute("lend", lend);
		model.addAttribute("items", itemsJson);
		return "book/bookDetail";
	}

	@PostMapping("/deleteReview/{id}")
	public String deleteReview(@PathVariable("id") int id, @RequestParam(name = "bookId") int bookId) {
		bookService.deleteReviewById(id);
		return "redirect:/book/detail/" + bookId;
	}

	@PostMapping("/updateReview/{id}")
	public String updateReview(//
			@PathVariable("id") Integer id,//
			@RequestParam(name = "content") String content,//
			@RequestParam(name = "score") Integer score,//
			@RequestParam(name = "bookId") Integer bookId) {
		bookService.updateReviewById(content, score, id);
		return "redirect:/book/detail/" + bookId;
	}

	/* 대여기능 추가 */
	@GetMapping("/lend/{bookId}")
	public String lendBook(//
			@PathVariable("bookId") int bookId,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {

		Integer userId = principal.getUserId();

		bookService.lendBook(bookId, userId);

		return "redirect:/book/detail/" + bookId;
	}

	/* 예약하기 */
	@GetMapping("/reservation/{bookId}")
	public String reservationBook(//
			@PathVariable("bookId") int bookId,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {

		Integer userId = principal.getUserId();

		bookService.createReservation(userId, bookId);

		return "redirect:/book/detail/" + bookId;
	}

	/* 좋아요 기능 */
	@PostMapping("/like/{bookId}")
	@ResponseBody
	public String createLike(//
			@PathVariable("bookId") int bookId,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {

		Integer userId = principal.getUserId();

		Like checkLike = bookService.readLike(userId, bookId);

		if (checkLike == null) {
			bookService.createLike(userId, bookId);
			return "liked";
		} else {
			bookService.deleteLike(userId, bookId);
			return "unliked";
		}
	}

	/* 관심등록 기능 */
	@PostMapping("/favorite/{bookId}")
	@ResponseBody
	public String createFavorite(//
			@PathVariable("bookId") int bookId,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {

		Integer userId = principal.getUserId();

		Favorite checkfavorite = bookService.readFavorite(userId, bookId);

		if (checkfavorite == null) {
			bookService.createFavorite(userId, bookId);
			return "favorited";
		} else {
			bookService.deleteFavorite(userId, bookId);
			return "unfavorited";
		}
	}

	// 도서 등록 페이지
	@GetMapping("/create")
	public String bookCreatePage(Model model) {
		List<BookCategory> categoryList = bookService.readAllBookCategory();
		model.addAttribute("categoryList", categoryList);
		return "book/bookCreate";
	}

	// 도서 등록
	@PostMapping("/create")
	public String bookCreateProc(@ModelAttribute Book book) {
		bookService.createBook(book);
		return "redirect:/admin/book";
	}

	// 도서 수정 페이지
	@GetMapping("/update/{bookId}")
	public String bookUpdatePage(@PathVariable("bookId") Integer bookId, Model model) {
		Book book = bookService.readBook(bookId);
		List<BookCategory> categoryList = bookService.readAllBookCategory();
		model.addAttribute("book", book);
		model.addAttribute("categoryList", categoryList);
		return "book/bookUpdate";
	}

	// 도서 수정
	@PostMapping("/update/{bookId}")
	public String bookUpdateProc(@ModelAttribute Book book) {
		bookService.updateBookById(book);
		return "redirect:/admin/book/detail/{bookId}";
	}

	// 도서 삭제
	@GetMapping("/delete/{bookId}")
	public String bookDeleteProc(@PathVariable("bookId") Integer bookId) {
		bookService.deleteBookById(bookId);
		return "redirect:/admin/book";
	}

}
