package com.library.bookwave.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.bookwave.repository.model.Review;
import com.library.bookwave.service.MyReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class MyReviewController {

	private final MyReviewService reviewService;

	@GetMapping("/list")
	public String showList(HttpSession session, Model model) {
		List<Review> list = new ArrayList<>();

		// int id = (int) session.getAttribute("id");
		// list = reviewService.findAllByUserId(id);

		list = reviewService.findAllByUserId(1);

		model.addAttribute("reviewList", list);

		return "myReview/review";
	}

	@PostMapping("/delete/{id}")
	public String deleteProc(@PathVariable(name = "id") int id) {

		reviewService.deleteById(id);

		return "redirect:/review/list";
	}

	@GetMapping("/update/{id}")
	public String showDetail(@PathVariable("id") int id, Model model) {

		Review review = reviewService.findDetailById(id);

		model.addAttribute("review", review);

		return "myReview/update";
	}

	// 주소 확인
	// http:localhost:8080/review/update/1
	@PostMapping("/update/{id}")
	public String updateProc(Model model, @PathVariable(name = "id") int id,
			@RequestParam(name = "score") Integer score, @RequestParam(name = "content") String content) {

		reviewService.updateById(id, score, content);
		return "redirect:/review/list";
	}

}
