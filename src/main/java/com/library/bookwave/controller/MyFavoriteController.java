package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.Book;
import com.library.bookwave.service.MyFavoriteService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/my-favorite")
@RequiredArgsConstructor
public class MyFavoriteController {

	private final MyFavoriteService favoriteService;

	@GetMapping("/list")
	public String showList(@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal,
			Model model) {

		// int userId = principal.getUserId();

		int userId = 1;

		List<Book> list = favoriteService.findAllByUserId(userId);

		model.addAttribute("favoriteList", list);

		return "myFavorite/favorite";
	}

}
