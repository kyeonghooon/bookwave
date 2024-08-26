package com.library.bookwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EBookController {
	
	@GetMapping("/test")
	public String test(HttpServletResponse response) {
		return "ebook/ebookRead";
	}
	
}
