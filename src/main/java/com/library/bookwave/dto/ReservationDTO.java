package com.library.bookwave.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationDTO {

	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Timestamp reservationDate;
	private Timestamp waitDate;
	private Integer status;

}
