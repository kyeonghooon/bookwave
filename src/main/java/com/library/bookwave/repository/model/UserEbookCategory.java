package com.library.bookwave.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEbookCategory {
	
	private Integer id;
	private Integer userId;
	private String name; // 카테고리명
	private Integer priority; // 우선순위
	
}
