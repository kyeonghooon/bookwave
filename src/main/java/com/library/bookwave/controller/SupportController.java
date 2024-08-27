package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.repository.model.Faq;
import com.library.bookwave.service.SupportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportController {

	private final SupportService supportService;

	// faq 리스트 페이지로 이동
	@GetMapping("/faq")
	public String faqPage(Model model) {
		List<Faq> faqList = supportService.readAllFaq();

		model.addAttribute("faqList", faqList);
		return "support/faqList";
	}

	// faq 등록 페이지로 이동
	@GetMapping("/faq-create")
	public String createFaqPage(Model model) {
		List<String> categoryList = supportService.readAllCategory();
		model.addAttribute("categoryList", categoryList);
		return "support/faqCreate";
	}

	// faq 등록하기
	@PostMapping("/faq-create")
	public String createFaqPage(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content, @RequestParam(name = "category") String category) {
		supportService.createFaq(Faq.builder().title(title).content(content).category(category).build());
		return "redirect:/support/faq";
	}

	// faq 수정 페이지로 이동
	@GetMapping("/faq-update")
	public String updateFaqPage(@RequestParam(name = "id") Integer id, Model model) {
		List<String> categoryList = supportService.readAllCategory();
		Faq faq = supportService.readFaqById(id);
		model.addAttribute("faq", faq);
		model.addAttribute("categoryList", categoryList);
		return "support/faqUpdate";
	}

	// faq 수정하기
	@PostMapping("/faq-update")
	public String updateFaqProc(@RequestParam(name = "id") Integer id, @RequestParam(name = "title") String title, @RequestParam(name = "content") String content,
			@RequestParam(name = "category") String category) {
		supportService.updateFaqById(Faq.builder().id(id).title(title).content(content).category(category).build());
		return "redirect:/support/faq";
	}

	// faq 삭제하기
	@GetMapping("/faq-delete")
	public String deleteFaq(@RequestParam(name = "id") Integer id) {
		supportService.deleteFaqById(id);
		return "redirect:/support/faq";
	}

	// qna 리스트 페이지로 이동
	@GetMapping("/qna")
	public String qnaPage(Model model) {
		return "support/qnaList";
	}
}
