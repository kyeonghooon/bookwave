package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.service.PurchaseService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

	private final PurchaseService purchaseService;

	@PostMapping(value = "/{itemId}", consumes = "application/json")
	public Map<String, Object> purchaseItem(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal, //
			@RequestBody Map<String, String> request,//
			@PathVariable(name = "itemId") Integer itemId) {
		System.out.println(request);
		Map<String, Object> response = new HashMap<>();
		int wave = Integer.parseInt(request.get("wave"));
		int mileage = Integer.parseInt(request.get("mileage"));
		if (principal.getWave() < wave) {
			response.put("success", false);
			response.put("message", "wave 부족");
			return response;
		}
		if (principal.getMileage() < mileage) {
			response.put("success", false);
			response.put("message", "mileage 부족");
			return response;
		}
		if (purchaseService.purchaseItem(principal, itemId, wave, mileage, request)) {
			response.put("success", true);
			response.put("message", "구매 성공");
		} else {
			response.put("success", false);
			response.put("message", "알 수 없는 오류");
		}
		return response;
	}

	@GetMapping("/subscribe")
	public Map<String, Object> subscribe(@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		Map<String, Object> response = new HashMap<>();
		if (principal.getWave() < Define.SUBSCRIBE_PRICE) {
			response.put("success", false);
			response.put("message", "wave 부족");
			return response;
		}
		if (purchaseService.subscribe(principal)) {
			response.put("success", true);
			response.put("message", "구매 성공");
		} else {
			response.put("success", false);
			response.put("message", "알 수 없는 오류");
		}
		return response;
	}

	@PostMapping(value = "/{itemId}", consumes = "multipart/form-data")
	public Map<String, Object> purchasePrint(//
			@RequestParam("file") MultipartFile file,//
			@RequestParam("pages") Integer pages,//
			@RequestParam("wave") Integer wave,//
			@RequestParam("mileage") Integer mileage,//
			@PathVariable(name = "itemId") Integer itemId,//
			@SessionAttribute(value = Define.PRINCIPAL) PrincipalDTO principal) {
		Map<String, Object> response = new HashMap<>();

		if (principal.getWave() < wave) {
			response.put("success", false);
			response.put("message", "wave 부족");
			return response;
		}
		if (principal.getMileage() < mileage) {
			response.put("success", false);
			response.put("message", "mileage 부족");
			return response;
		}
		if (purchaseService.purchasePrint(principal, itemId, wave, mileage, file, pages)) {
			response.put("success", true);
			response.put("message", "구매 성공");
		} else {
			response.put("success", false);
			response.put("message", "알 수 없는 오류");
		}
		return response;
	}

}
