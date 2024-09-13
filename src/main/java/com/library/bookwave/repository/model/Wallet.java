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
public class Wallet {
	
	private Integer id;
	private Integer userId;
	private Integer wave;
	private Integer mileage;
	
}
