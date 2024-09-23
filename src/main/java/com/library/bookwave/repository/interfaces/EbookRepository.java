package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.EbookDTO;
import com.library.bookwave.dto.EbookReorderDTO;
import com.library.bookwave.repository.model.UserEbook;
import com.library.bookwave.repository.model.UserEbookCategory;

@Mapper
public interface EbookRepository {
	
	// user_ebook_tb 조회
	UserEbook readUserEbook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
	
	// ebook path 조회
	String findEbookPathByBookId(Integer bookId);
	
	// user_ebook lastPoint 업데이트
	Integer updateUserEbookWithLastPoint(@Param("lastPoint") Double lastPoint, @Param("userId") Integer userId, @Param("bookId") Integer bookId);
	
	// 해당 유저의 ebookList 조회 (카테고리 필터 포함)
	List<EbookDTO> findEbookListByUserIdAndCategory(@Param("userId") Integer userId, @Param("category") Integer category);
	
	// 해당 유저의 ebook_category_list 조회
	List<UserEbookCategory> findEbookCategoryListByUserId(Integer userId);
	
	// 해당 유저의 user_ebook_category_limit 조회
	Integer readEbookCategoryLimit(Integer userId);
	
	// 해당 유저의 현재 카테고리 수 조희
	Integer countEbookCategoryByUserId(Integer userId);
	
	// ebook 카테고리 생성
	Integer createUserEbookCategory(@Param("userId") Integer userId, @Param("category") String category);

	// ebook 카테고리 이름 변경
	Integer updateUserEbookCategoryName(@Param("userId") Integer userId, @Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId);
	
	// ebook 카테고리 순서 변경
	Integer updateUserEbookPriority(@Param("userId") Integer userId, @Param("ebookReorderList") List<EbookReorderDTO> ebookReorderList);
	
	// user_ebook category_id 변경
	Integer updateUserEbookCategory(@Param("categoryId") Integer categoryId, @Param("userId") Integer userId, @Param("bookId") Integer bookId);
	
	// user_ebook_cate_limit 한도 증가
	Integer updateEbookCategoryLimit(@Param("userId") Integer userId, @Param("extend") Integer extend);
	
	// user_ebook_cate_limit insert
	Integer createEbookCategoryLimit(Integer userId);
	
	// user_ebook 등록 (구독 서비스 이용자)
	Integer createEbookWithSubscribe(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
	
	// user_ebook 구매 
	Integer createEbook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
