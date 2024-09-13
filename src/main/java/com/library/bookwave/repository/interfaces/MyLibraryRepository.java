package com.library.bookwave.repository.interfaces;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.MyLibrary;

@Mapper
public interface MyLibraryRepository {

	public List<MyLibrary> findAllByUserId(Integer userId);

	// updates status in lend_tb
	public Integer updateStatusById(Integer id);

	// 연장시 사용될 코드
	public Integer updateReturnDateById(@Param("id") Integer id, @Param("returnDate") Timestamp returnDate);

	public Timestamp getCurrentReturnDateById(Integer id);

	public Integer updateReturnedDateById(Integer id);

	public Integer findFirstByBookIdAndStatus(Integer bookId);

	public Integer findBookIdById(Integer id);

	// updates status in reservation_tb
	public Integer updateStatusByIdReservation(Integer bookId);

	public Integer updateWaitDateById(Integer id);

	// 책 재고 update
	public Integer updateStockByBookId(Integer bookId);

}
