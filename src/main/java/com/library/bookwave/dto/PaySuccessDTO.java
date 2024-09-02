package com.library.bookwave.dto;

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
public class PaySuccessDTO {
	private String paymentType;
	private String orderId;
	private String paymentKey;
	private String amount;
	private String orderName;
	
	private Timestamp createdAt;
}
