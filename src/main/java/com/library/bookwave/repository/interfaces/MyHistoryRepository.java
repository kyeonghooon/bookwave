package com.library.bookwave.repository.interfaces;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.model.MyBookHistory;
import com.library.bookwave.repository.model.MyEbookHistory;

@Mapper
public interface MyHistoryRepository {

	public int createReview(ReviewDTO dto);

	public List<MyBookHistory> findAllBookByUserId(Integer userId);

	public List<MyEbookHistory> findAllEbookByUserId(Integer userId);

	// 카테고리, 월별 조회
	public List<String> findAllBookCategoryByUserId(Integer userId);

	public List<String> findAllEbookCategoryByUserId(Integer userId);

	public int findAllBookCategoryCountByUserId(@Param("userId") int userId, @Param("category") String category);

	public int findAllEbookCategoryCountByUserId(@Param("userId") int userId, @Param("category") String category);

	public List<Map<String, Object>> findMonthlyBookLendsByUserId(Integer userId);

	public List<Map<String, Object>> findMonthlyEbookLendsByUserId(Integer userId);

	public List<Integer> findReviewedBookIdsByUserId(Integer userId);

	public List<MyBookHistory> findBooksByTitle(@Param("userId") int userId, @Param("search") String search);

	public List<MyEbookHistory> findEbooksByTitle(@Param("userId") int userId, @Param("search") String search);

}
