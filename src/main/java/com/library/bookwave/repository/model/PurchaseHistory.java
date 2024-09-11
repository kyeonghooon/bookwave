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
public class PurchaseHistory {

	private Integer id;
    private Integer userId;
    private Integer itemId;
    private Integer waveUsed; // 사용한 wave
    private Integer mileageUsed; // 사용한 mileage
    private Integer totalAmount; // 총 사용량
    private Timestamp purchasedAt;
	
}
