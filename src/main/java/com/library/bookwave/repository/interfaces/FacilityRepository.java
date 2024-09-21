package com.library.bookwave.repository.interfaces;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.repository.model.Computer;
import com.library.bookwave.repository.model.ComputerReservation;

@Mapper
public interface FacilityRepository {

	// 컴퓨터 목록 조회
	List<Computer> readAllComputers();

	// 컴퓨터 예약 내역 조회
	List<ComputerReservation> findComputerReservationByDateAndComputerId(@Param("date") String date, @Param("computerId") Integer computerId);

	// 해당 시간에 예약이 가능 한지 조회
	Integer countComputerReservationByComputerIdAndTime(//
			@Param("userId") Integer userId,//
			@Param("computerId") Integer computerId,//
			@Param("startTime") Timestamp startTime,//
			@Param("endTime") Timestamp endTime);

	Integer createComputerReservation(//
			@Param("userId") Integer userId,//
			@Param("computerId") Integer computerId,//
			@Param("startTime") Timestamp startTime,//
			@Param("endTime") Timestamp endTime);
}
