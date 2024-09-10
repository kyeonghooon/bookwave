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
public class Reservation {
	private int id;
	private int userId;
	private int bookId;
	private Timestamp reservationDate;
	private Timestamp waitDate;
	private int status;
}
