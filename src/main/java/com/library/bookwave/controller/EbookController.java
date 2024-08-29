package com.library.bookwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserEbook;
import com.library.bookwave.service.EbookService;
import com.library.bookwave.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ebook")
@RequiredArgsConstructor
public class EbookController {

	private final EbookService ebookservice;
	private final HttpSession session;

	public String listPage(Model model) {
		// TODO book 모델 필요
		return "ebook/ebookList";
	}

	/**
	 * Ebook view 페이지 호출 // 
	 * TODO 주소 제거 예정 localhost:8080/ebook/view/2
	 * @return
	 */
	@GetMapping("/view/{bookId}")
	public String viewPage(@PathVariable(name = "bookId") Integer bookId, //
			Model model) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = user == null ? 1 : user.getId();
		
		// 1. 해당 유저의 해당 ebook에 대한 내역 확인
		UserEbook userEbook = ebookservice.readUserEbook(userId, bookId);
		// TODO 현재 null이라 안됨 샘플 데이터
		userEbook = UserEbook.builder().lastPoint(0.05).build();
		
		// 2. 해당 ebook의 path 받아옴
		// TODO 제목도 받아와야함 model 만들어 지면 추가
		String ebookPath = ebookservice.findEbookPathByBookId(userId);
		// 3. model에 attribute 추가
		model.addAttribute("ebook", userEbook);
		model.addAttribute("ebookPath", ebookPath);
		return "ebook/ebookRead";
	}

}
