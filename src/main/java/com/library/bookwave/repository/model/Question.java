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
public class Question {
	private Integer id;
	private Integer userId;
	private String title;
	private String content;
	private Integer answerId;
	private Timestamp createdAt;
}