package com.library.bookwave.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.interfaces.EbookRepository;
import com.library.bookwave.repository.interfaces.ItemRepository;
import com.library.bookwave.repository.interfaces.PurchaseRepository;
import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.PurchaseHistory;
import com.library.bookwave.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final ItemRepository itemRepository;
	private final EbookRepository ebookRepository;
	private final HttpSession httpSession;

	/**
	 * 아이템 구매 비지니스 로직 처리
	 */
	@Transactional
	public boolean purchaseItem(PrincipalDTO principal, Integer itemId, Integer waveUsed, Integer mileageUsed, Map<String, Integer> request) {

		int waveBalance = principal.getWave() - waveUsed;
		int mileageBalance = principal.getMileage() - mileageUsed;
		int userId = principal.getUserId();

		// 지갑 업데이트
		try {
			purchaseRepository.updateWallet(userId, waveBalance, mileageBalance);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		// 구매 내역 추가
		try {
			PurchaseHistory purchaseHistory = PurchaseHistory//
				.builder()//
				.userId(userId)//
				.itemId(itemId)//
				.waveUsed(waveUsed)//
				.mileageUsed(mileageUsed)//
				.totalAmount(waveUsed + mileageUsed)
				.build();
			purchaseRepository.createPurchaseHistory(//
					purchaseHistory);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		// 잔액 변동 내역 추가
		try {
			purchaseRepository.createBalanceHistory(BalanceHistory//
				.builder()//
				.userId(userId)//
				.waveChange(-waveUsed)//
				.mileageChange(-mileageUsed)//
				.build());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		// principal에 재화 최신화
		principal.setWave(waveBalance);
		principal.setMileage(mileageBalance);
		httpSession.setAttribute("principal", principal);
		return applyItem(itemId, userId, request);
	}

	private boolean applyItem(Integer itemId, Integer userId, Map<String, Integer> request) {
		String item = itemRepository.readItem(itemId);
		switch (item) {
		case "extend-category":
			return extendCategory(userId);
		case "ebook":
			return ebook(userId, request.get("bookId"));

		default:
			return false;
		}
	}

	private boolean extendCategory(Integer userId) {
		int result = 0;
		try {
			int extend = 5; // TODO DEFINE에서 정의 필요
			int currentLimit = ebookRepository.readEbookCategoryLimit(userId);
			// 테이블에 userId가 없다면 3이 반환
			if (currentLimit == 3) {
				result = ebookRepository.createEbookCategoryLimit(userId);
			} else {
				result = ebookRepository.updateEbookCategoryLimit(userId, extend);
			}
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	private boolean ebook(Integer userId, Integer bookId) {
		int result = 0;
		try {
			result = ebookRepository.createEbook(userId, bookId);
		} catch (Exception e) {
			return false;
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean subscribe(PrincipalDTO principal) {
		int waveBalance = principal.getWave() - Define.SUBSCRIBE_PRICE;
		int mileageBalance = principal.getMileage();
		int userId = principal.getUserId();

		// 지갑 업데이트
		try {
			purchaseRepository.updateWallet(userId, waveBalance, mileageBalance);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		// 구매 내역 추가
		try {
			PurchaseHistory purchaseHistory = PurchaseHistory//
				.builder()//
				.userId(userId)//
				.itemId(3)//
				.waveUsed(Define.SUBSCRIBE_PRICE)//
				.totalAmount(Define.SUBSCRIBE_PRICE)
				.build();
			purchaseRepository.createPurchaseHistory(//
					purchaseHistory);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		// 잔액 변동 내역 추가
		try {
			purchaseRepository.createBalanceHistory(//
					BalanceHistory//
						.builder()//
						.userId(userId)//
						.waveChange(-Define.SUBSCRIBE_PRICE)//
						.build());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		try {
			purchaseRepository.createSubscribe(userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		// principal에 재화 최신화
		principal.setWave(waveBalance);
		principal.setMileage(mileageBalance);
		principal.setSubscribe(true);
		httpSession.setAttribute("principal", principal);
		return true;
	}

}
