package com.library.bookwave.dto;

import java.sql.Timestamp;

import com.library.bookwave.utils.ValueFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Builder

public class BalanceHistoryDTO extends ValueFormatter {
	private Integer id;
    private Integer userId;
    private Integer waveChange; // wave 변화량 (증가감소)
    private Integer mileageChange; // mileage 변화량 (증가감소)
    private String description; // 설명
    private Timestamp createdAt;
    
}
