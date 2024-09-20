package com.library.bookwave.dto;

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
public class BookDetailReviewDTO {
	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Double score;
	private String content;
	private Timestamp createdAt;
	private String name;
}
