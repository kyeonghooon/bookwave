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
public class Lend {
	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Integer status;
	private Timestamp lendDate;
	private Timestamp returnDate;
	private Timestamp returnedDate;
}
