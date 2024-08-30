package com.library.bookwave.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.dto.QnaDTO;
import com.library.bookwave.repository.model.Answer;
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
	public String qnaPage(@RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "page", defaultValue = "1") int page, Model model) {

		int totalRecords = supportService.countAllQna();
		int totalPages = (int) Math.ceil((double) totalRecords / size);

		List<QnaDTO> qnaList = supportService.readAllQna(page, size);
		if (qnaList.isEmpty()) {
			model.addAttribute("qnaList", null);
		} else {
			model.addAttribute("qnaList", qnaList);
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);
		return "support/qnaList";
	}

	// qna 답변 페이지로 이동
	@GetMapping("/answer-create")
	public String createAnswerPage(@RequestParam(name = "id") Integer id, Model model) {
		// TODO session 에서 pricipal 불러오기

		QnaDTO qnaDTO = supportService.readQnaById(id);
		// TODO model.addAttribute("aid", principal.getId());
		model.addAttribute("aid", 1); // 임시로 번호 1이 답변하게 
		// TODO model.addAttribute("aname", principal.getName());
		model.addAttribute("qna", qnaDTO);
		return "support/answerCreate";
	}

	// qna 답변하기
	@PostMapping("/answer-create")
	public String createQnaProc(@RequestParam(name = "id") Integer qid, @RequestParam(name = "aid") Integer aid, @RequestParam(name = "acontent") String acontent) {
		Answer answer = Answer.builder().questionId(qid).userId(aid).content(acontent).build();
		supportService.createAnswerByQid(answer);

		return "redirect:/support/qna";
	}

	// qna 수정 페이지로 이동
	@GetMapping("/answer-update")
	public String updateAnswerPage(@RequestParam(name = "id") Integer id, Model model) {
		// TODO session 에서 pricipal 불러오기

		QnaDTO qnaDTO = supportService.readQnaById(id);
		// TODO model.addAttribute("aid", principal.getId());
		model.addAttribute("aid", 1); // 임시로 번호 1이 답변하게 
		// TODO model.addAttribute("aname", principal.getName());
		model.addAttribute("qna", qnaDTO);
		return "support/answerUpdate";
	}

	// qna 수정하기
	@PostMapping("/answer-update")
	public String updateQnaProc(@RequestParam(name = "id") Integer qid, @RequestParam(name = "aid") Integer aid, @RequestParam(name = "acontent") String acontent) {
		Answer answer = Answer.builder().questionId(qid).userId(aid).content(acontent).build();
		supportService.updateAnswerByQid(answer);

		return "redirect:/support/qna";
	}

	// qna 검색하기
	@GetMapping("/qna-find")
	public String findQnaProc(@RequestParam(name = "keyword") String keyword, @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "page", defaultValue = "1") int page,
			Model model) {
		int totalRecords = supportService.countQnaByKeyword(keyword);
		int totalPages = (int) Math.ceil((double) totalRecords / size);

		List<QnaDTO> qnaList = supportService.findQnaByKeyword(keyword, page, size);
		if (qnaList.isEmpty()) {
			model.addAttribute("qnaList", null);
		} else {
			model.addAttribute("qnaList", qnaList);
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);
		return "support/qnaList";
	}

}
