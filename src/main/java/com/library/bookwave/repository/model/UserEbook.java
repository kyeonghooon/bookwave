package com.library.bookwave.repository.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEbook {
	
	private Integer userId;
	private Integer bookId;
	private Double lastPoint; // 마지막 읽은 위치
	private Timestamp lastReadDate; // 마지막 읽은 날짜 + 시간
	private Integer status; // 상태 0: 읽는 중 / -1: 종료
	
}
