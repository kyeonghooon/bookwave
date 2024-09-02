package com.library.bookwave.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.bookwave.dto.EbookDTO;
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

	@GetMapping
	public String listPage(Model model) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = user == null ? 1 : user.getId();
		List<EbookDTO> bookList = ebookservice.findEbookListByUserId(userId);
		model.addAttribute("bookList", bookList);
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
		
		// 2. 해당 ebook의 path 받아옴
		// TODO 제목도 받아와야함 model 만들어 지면 추가
		String ebookPath = ebookservice.findEbookPathByBookId(userId);
		// 3. model에 attribute 추가
		model.addAttribute("ebook", userEbook);
		model.addAttribute("ebookPath", ebookPath);
		return "ebook/ebookRead";
	}
	
	/**
	 * 읽은 위치 저장
	 */
	@PostMapping("/save/{bookId}")
	@ResponseBody
	public ResponseEntity<?> savePage(@RequestBody Double progress, //
			@PathVariable(name = "bookId") Integer bookId) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = user == null ? 1 : user.getId();
		int result = ebookservice.updateUserEbookWithLastPoint(progress, userId, bookId);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장 실패");
		} else {
			return ResponseEntity.ok().body("저장 성공");
		}
	}
	
	@GetMapping("/remove")
	public String removeBook(@RequestParam(name = "bookId") Integer bookId) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		// TODO 테스트용 코드 로그인 구현되면 제거 예정
		int userId = user == null ? 1 : user.getId();
		ebookservice.updateUserEbookWithStatus(-1, userId, bookId);
		return "redirect:/ebook";
	}
}
