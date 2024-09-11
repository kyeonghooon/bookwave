package com.library.bookwave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.bookwave.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/controller-user")
@RequiredArgsConstructor
public class UserRestController {
	@Autowired
	private UserService userService;

	// 주소
	// http://localhost:8080/controller-user/check-userId
	// ID 중복 확인
	@GetMapping("/check-userId")
	public boolean getUserId(@RequestParam(name = "loginId") String loginId) {
		//return userService.confirmUid(loginId);
		System.out.println(loginId);
		boolean isUse = userService.readUserId(loginId) == null ? true : false;
		return isUse;
		
	}

}// end of main
