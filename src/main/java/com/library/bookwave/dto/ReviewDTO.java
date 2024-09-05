package com.library.bookwave.dto;

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
public class ReviewDTO {

	private Integer userId;
	private Integer bookId;
	private Integer score;
	private String content;

}
