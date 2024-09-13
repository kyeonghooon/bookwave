package com.library.bookwave.dto.bookParsing;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Item {
	private int id;
	private String title; // 책 제목
	private String author; // 책 저자
	private Date pubDate; // 출판일
	private String description; // 설명
	private String cover; // 책 표지 URL
	private String categoryName; // 카테고리 이름
	private String publisher; // 출판사

	public String getCategoryName() {
		String[] tokens = categoryName.split(">");
		return tokens[1];
	}
}
