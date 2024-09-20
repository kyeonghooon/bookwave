package com.library.bookwave.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserDetailDTO {
	private int userId;
	private String loginId;
	private String socialId;
	private String name;
	private String role;
	private Integer status;
	private String email;
	private Date birthDate;
	private Boolean gender;
	private String phone;
	private String zip;
	private String addr1;
	private String addr2;
	private Timestamp createdAt;

	private Integer wave;
	private Integer mileage;
	private Timestamp endDate;
}
