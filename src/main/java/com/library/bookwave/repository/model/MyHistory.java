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
public class MyHistory {

	private Integer id;
	private String title;
	private String description;
	private String author;
	private String publisher;
	private String cover;
	private String category;
	private Integer likes;
	private Integer userId;
	private Timestamp lendDate;
	private Timestamp returnedDate;

}
