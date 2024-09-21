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
public class ComputerReservation {
	
	private Integer id;
	private Integer computerId;
	private Integer userId;
	private Timestamp startTime;
	private Timestamp endTime;
	private Boolean status;
		
}
