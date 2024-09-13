package com.library.bookwave.repository.model;

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
public class BookCategory {
	private int id; // 카테고리 ID
	private String name; // 카테고리명
}
