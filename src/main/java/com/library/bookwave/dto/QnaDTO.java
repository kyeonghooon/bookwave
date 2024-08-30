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
public class QnaDTO {
	private Integer qid;
	private String qname;
	private String quserId;	
	private String qtitle;
	private String qcontent;
	private Timestamp qtime;
	private String aname;
	private String acontent;
	private Timestamp atime;
}
