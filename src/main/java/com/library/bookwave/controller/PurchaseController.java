package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.service.PurchaseService;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
	
	private final PurchaseService purchaseService;
	
	@PostMapping("/{itemId}")
	public Map<String, Object> purchaseItem(@SessionAttribute(value = Define.PRINCIPAL, required = false) PrincipalDTO principal, //
			@RequestBody Map<String, Integer> request,//
			@PathVariable(name = "itemId") Integer itemId) {
		// TODO 테스트 코드 변경 예정
		if (principal == null) {
			principal = PrincipalDTO.builder()
					.userId(1)
					.wave(5000)
					.mileage(5000)
					.build();
		}
		Map<String, Object> response = new HashMap<>();
		int wave = request.get("wave");
		int mileage = request.get("mileage");
		if (principal.getWave() < wave) {
			response.put("success", false);
			response.put("message", "wave 부족");
		}
		if (principal.getMileage() < mileage) {
			response.put("success", false);
			response.put("message", "mileage 부족");
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
	
}
