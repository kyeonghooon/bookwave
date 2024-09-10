package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.model.MyHistory;

@Mapper
public interface MyHistoryRepository {

	public List<MyHistory> findAllByUserId(Integer userId);

	public int createReview(ReviewDTO dto);

	// 카테고리, 월별 조회
	public List<String> findAllCategoryByUserId(Integer userId);

	public int findAllCategoryCountByUserId(@Param("userId") int userId, @Param("category") String category);

}
