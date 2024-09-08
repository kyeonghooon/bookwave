package com.library.bookwave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EbookReorderDTO {
	
	private Integer categoryId;
	private Integer priority;
	
}
