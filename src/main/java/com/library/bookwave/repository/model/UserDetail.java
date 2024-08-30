package com.library.bookwave.repository.model;

import java.sql.Date;

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

// user_detail_tb
public class UserDetail {
	
	private int userId;
	private String email;
	private Date birthDate;
	private Boolean gender;
	private String phone;
	private String zip;
	private String addr1;
	private String addr2;

}
