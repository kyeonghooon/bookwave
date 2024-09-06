package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.repository.model.Favorite;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.Like;
import com.library.bookwave.repository.model.Reservation;

@Mapper
public interface BookRepository {

	// 도서 목록 조회하기
	public List<BookListDTO> readAllBook(@Param("userId") Integer userId, @Param("category") String category,
			@Param("search") String search, @Param("limit") Integer limit, @Param("offset") Integer offset);

	// 카테고리 조회
	public List<String> readAllBookCategory();

	// 필터링 걸린 도서 갯수 조회하기
	public int countAllBook(@Param("category") String category, @Param("search") String search);

	// 도서 아이디로 도서 조회하기
	public Book readBook(int bookId);

	// 좋아요 기능
	public Like readLike(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public void createLike(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public void updateBookLikeUp(int bookId);

	public void deleteLike(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public void updateBookDown(Integer bookId);

	// 관심 등록
	public Favorite readFavorite(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public void createFavorite(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public void deleteFavorite(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	// 예약하기
	public Reservation findReservationByBookIdAndUserId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public void createReservation(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	public int countReservationByUserId(Integer userId);

	// 도서 남은 개수
	public int readBookCurrentStock(Integer bookId);

	// 재고 업데이트
	public void updateBookCurrentStock(Integer bookId);

	// 대여기록 생성
	// TODO MySQL에서 작동하지 않는 쿼리문 사용
	public void createLend(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	// 대여 여부
	public Lend readLend(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

	// 대여 카운트
	public int countLendByUserId(Integer userId);
	
	// eBook등록
	public void createUserEbook(@Param("userId") Integer userId, @Param("bookId") Integer bookId, @Param("subscribe") Boolean subscribe);

	// eBook여부조회
	public int readUserEbook (@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
