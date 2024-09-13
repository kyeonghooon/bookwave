package com.library.bookwave.dto;

import java.sql.Timestamp;

import com.library.bookwave.utils.ValueFormatter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class EbookDTO extends ValueFormatter {
	
	private Integer id;
	private String title; // 책 제목
	private String cover; // 책 표지 URL
	private Double progress; // 진행도
	private Timestamp lastReadDate; // 마지막 읽은 날짜
	
}
