package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mainService;

	@GetMapping
	public String mainPage(Model model) {
		List<BookListDTO> montlyBookList = mainService.findBooksByMonthlyLikes();
		List<BookListDTO> newBookList = mainService.findBooksByCurrentMonth();
		List<BookListDTO> rankedBookList = mainService.findBooksByScore();
		
		//
		model.addAttribute("montlyBookList", montlyBookList);
		model.addAttribute("newBookList", newBookList);
		model.addAttribute("rankedBookList", rankedBookList);
		return "layout/main";
	}
	
	
}
