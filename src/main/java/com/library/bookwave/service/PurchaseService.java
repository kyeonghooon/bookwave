package com.library.bookwave.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.interfaces.EbookRepository;
import com.library.bookwave.repository.interfaces.ItemRepository;
import com.library.bookwave.repository.interfaces.PurchaseRepository;
import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.PurchaseHistory;

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
	public boolean purchaseItem(PrincipalDTO principal, Integer itemId, Integer waveUsed, Integer mileageUsed) {

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
			PurchaseHistory purchaseHistory = 
			PurchaseHistory//
			.builder()//
			.userId(userId)//
			.itemId(itemId)//
			.waveUsed(waveUsed)//
			.mileageUsed(mileageUsed)//
			.totalAmount(waveUsed + mileageUsed)
			.build();
			System.out.println(purchaseHistory);
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
		applyItem(itemId, userId);
		return true;
	}

	private void applyItem(Integer itemId, Integer userId) {
		String item = itemRepository.readItem(itemId);
		switch (item) {
		case "extend-category":
			extendCategory(userId);
			break;

		default:
			break;
		}
	}

	private void extendCategory(Integer userId) {
		try {
			int extend = 5; // TODO DEFINE에서 정의 필요
			int currentLimit = ebookRepository.readEbookCategoryLimit(userId);
			// 테이블에 userId가 없다면 3이 반환
			if (currentLimit == 3) {
				ebookRepository.createEbookCategoryLimit(userId);
			} else {
				ebookRepository.updateEbookCategoryLimit(userId, extend);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}