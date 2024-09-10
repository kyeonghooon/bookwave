package com.library.bookwave.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.interfaces.PurchaseRepository;
import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.PurchaseHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;

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
			purchaseRepository.createPurchaseHistory(//
					PurchaseHistory//
						.builder()//
						.userId(userId)//
						.itemId(itemId)//
						.waveUsed(waveUsed)//
						.mileageUsed(mileageUsed)//
						.build());
		} catch (Exception e) {
			// TODO: handle exception
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
			return false;
		}
		// principal에 재화 최신화
		principal.setWave(waveBalance);
		principal.setMileage(mileageBalance);
		return true;
	}

}
