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
public class BalanceHistory {

	private Integer id;
    private Integer userId;
    private Integer waveChange; // wave 변화량 (증가감소)
    private Integer mileageChange; // mileage 변화량 (증가감소)
    private String description; // 설명
    private Timestamp createdAt;
	
}
