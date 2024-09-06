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
public class UserEbook {
	private Integer userId;
	private Integer bookId;
	private Boolean subscirbe;
}
