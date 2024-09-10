package com.library.bookwave.dto;

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
public class PaymentDTO {
	private String clientKey;
	private String customerName;
	private String customerEmail;
	private String customerMobilePhone;
	private String orderName;
	private String orderId;
	private Long price;
	private String customerKey;
}
