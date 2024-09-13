package com.library.bookwave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LendDTO {

	private Integer userId;
	private Integer bookId;

}
