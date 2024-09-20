package com.library.bookwave.repository.model;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Review {

	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Integer score;
	private String content;
	private Timestamp createdAt;
	private Timestamp editedAt;
	private Integer status;
	private Integer daysSinceCreated;
	private Integer daysUntilClaim;

}
