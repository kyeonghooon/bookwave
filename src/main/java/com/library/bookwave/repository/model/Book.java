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
public class Book {
	private int id;
	private String title; // 책 제목
	private String description; // 설명
	private String author; // 책 저자
	private String publisher; // 출판사
	private String cover; // 책 표지 URL
	private String category; // 카테고리 이름
	private String publishDate; // 출판일
	private int totalStock; // 책 총 갯수
	private int currentStock; // 남은 책 갯수
	private int ebook; // ebook 여부 확인
	private String ebookPath; // ebook 경로
	private int likes; // 좋아요 수
	private double score; // 평점
	private Timestamp createdAt;
	
}
