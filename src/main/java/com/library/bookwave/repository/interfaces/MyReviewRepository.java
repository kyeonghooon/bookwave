package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.BalanceHistoryDTO;
import com.library.bookwave.repository.model.Review;

@Mapper
public interface MyReviewRepository {

	List<Review> findAllByUserId(Integer id);

	int updateById(@Param("id") Integer id, @Param("score") Integer score, @Param("content") String content);

	int deleteById(Integer id);

	Review findDetailById(Integer id);

	int updateWalletByUserId(Integer userId, int amount);

	int createBalanceHistoryByUserId(BalanceHistoryDTO dto);

	int updateStatusById(Integer id);

	Review selectReviewById(Integer id);
}
