package com.library.bookwave.repository.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyReserved {

	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Timestamp reservationDate;
	private Timestamp waitDate;
	private Integer status; // '0:진행중 -1: 완료 1: 대기'

}