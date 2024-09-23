package com.library.bookwave.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.BalanceHistoryDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.ReviewDTO;
import com.library.bookwave.repository.model.Review;
import com.library.bookwave.service.MyHistoryService;
import com.library.bookwave.service.MyReviewService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class MyReviewController {

	private final MyReviewService reviewService;
	private final MyHistoryService historyService;

	@GetMapping("/create/{bookId}")
	public String showReview(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable(value = "bookId") int bookId, Model model) {

		if (!historyService.validation(principal.getUserId(), bookId)) {
			model.addAttribute("errorMessage", "대출하지 않았거나 유효하지 않은 도서입니다.");
			return "myHistory/history";
		}
		model.addAttribute("bookId", bookId);

		return "myReview/create";
	}

	@PostMapping("/create/{bookId}")
	public String postReview(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable("bookId") int bookId, @RequestParam(name = "score") Integer score,
			@RequestParam(name = "content") String content, Model model) {

		int userId = principal.getUserId();

		// Validate score and content
		if (score == null || score < 1 || score > 10) {
			model.addAttribute("errorMessage", "점수는 1 - 10 사이여야 합니다.");
			return "myReview/create";// Redirect to the review page with the error
		}

		if (content == null || content.trim().isEmpty()) {
			model.addAttribute("errorMessage", "빈 리뷰는 작성할 수 없습니다.");
			return "myReview/create"; // Redirect to the review page with the error
		}

		if (!historyService.validation(principal.getUserId(), bookId)) {
			model.addAttribute("errorMessage", "대출하지 않았거나 유효하지 않은 도서입니다.");
			return "myHistory/history";
		}

		ReviewDTO dto = ReviewDTO.builder().bookId(bookId).content(content).score(score).userId(userId).build();

		historyService.createReview(dto);

		return "redirect:/history/list";
	}

	@GetMapping("/list")
	public String showList(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, Model model) {

		List<Review> list = reviewService.findAllByUserId(principal.getUserId());

		list.forEach(review -> {
			if (review.getCreatedAt() != null) {
				review.setCreatedAt(reviewService.trimTimestamp(review.getCreatedAt()));
			}

			if (review.getEditedAt() != null) {
				review.setEditedAt(reviewService.trimTimestamp(review.getEditedAt()));
			}

			// Calculate days since created
			long totalMilliseconds = System.currentTimeMillis() - review.getCreatedAt().getTime();
			int daysSinceCreated = (int) (totalMilliseconds / (1000 * 60 * 60 * 24));
			review.setDaysSinceCreated(daysSinceCreated);

			// Calculate days until claim
			int daysUntilClaim = Math.max(0, 7 - daysSinceCreated);
			review.setDaysUntilClaim(daysUntilClaim);
		});

		model.addAttribute("reviewList", list);
		model.addAttribute("currentTime", reviewService.getCurrentDate());

		return "myReview/review";
	}

	@PostMapping("/delete/{id}")
	public String deleteProc(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable(name = "id") int id, Model model) {

		if (!reviewService.validation(principal.getUserId(), id)) {
			model.addAttribute("errorMessage", "사용자의 리뷰가 아니거나 유효하지 않은 리뷰입니다.");
			return "myReview/review";
		}

		reviewService.deleteById(id);

		return "redirect:/review/list";
	}

	@PostMapping("/claim/{id}")
	public String claimProc(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable(name = "id") int id, Model model) {

		int userId = principal.getUserId();

		if (!reviewService.validation(userId, id)) {
			model.addAttribute("errorMessage", "사용자의 리뷰가 아니거나 유효하지 않은 리뷰입니다.");
			return "myReview/review";
		}

		Review review = reviewService.findDetailById(id);
		Timestamp createdAt = reviewService.trimTimestamp(review.getCreatedAt());
		Date currentTime = reviewService.getCurrentDate();

		long differenceInMillis = currentTime.getTime() - createdAt.getTime();

		if (differenceInMillis < (7 * 24 * 60 * 60 * 1000)) {
			model.addAttribute("errorMessage", "마일리지를 받으시려면 리뷰 작성 후 7일이 지나야 합니다.");
			return "myReview/review";
		}

		if (reviewService.selectReviewById(id)) {
			model.addAttribute("errorMessage", "이미 마일리지를 받으셨습니다.");
			return "myReview/review";
		}

		// 지급할 마일리지
		int amount = 50;

		BalanceHistoryDTO dto = BalanceHistoryDTO.builder().userId(userId).waveChange(0).mileageChange(amount)
				.description("리뷰작성 마일리지 수령").build();

		reviewService.updateWalletByUserId(userId, amount);
		reviewService.updateStatusById(id);
		reviewService.createBalanceHistoryByUserId(dto);

		return "redirect:/review/list";
	}

	@GetMapping("/update/{id}")
	public String showDetail(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal,
			@PathVariable("id") int id, Model model) {

		Review review = reviewService.findDetailById(id);

		if (!reviewService.validation(principal.getUserId(), id)) {
			model.addAttribute("errorMessage", "사용자의 리뷰가 아니거나 유효하지 않은 리뷰입니다.");
			return "myReview/review";
		}

		model.addAttribute("review", review);

		return "myReview/update";
	}

	@PostMapping("/update/{id}")
	public String updateProc(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, Model model,
			@PathVariable(value = "id") int id, @RequestParam(value = "score") Integer score,
			@RequestParam(value = "content") String content) {

		if (!reviewService.validation(principal.getUserId(), id)) {
			model.addAttribute("errorMessage", "사용자의 리뷰가 아니거나 유효하지 않은 리뷰입니다.");
			return "myReview/review";
		}

		// Validate score and content
		if (score == null || score < 1 || score > 10) {
			model.addAttribute("errorMessage", "점수는 1 - 10 사이여야 합니다.");
			return "myReview/update";// Redirect to the review page with the error
		}

		if (content == null || content.trim().isEmpty()) {
			model.addAttribute("errorMessage", "빈 리뷰는 작성할 수 없습니다.");
			return "myReview/update"; // Redirect to the review page with the error
		}

		reviewService.updateById(id, score, content);
		return "redirect:/review/list";
	}

}
