package com.library.bookwave.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.repository.interfaces.EbookRepository;
import com.library.bookwave.repository.interfaces.FacilityRepository;
import com.library.bookwave.repository.interfaces.ItemRepository;
import com.library.bookwave.repository.interfaces.MyLibraryRepository;
import com.library.bookwave.repository.interfaces.PurchaseRepository;
import com.library.bookwave.repository.model.BalanceHistory;
import com.library.bookwave.repository.model.MyLibrary;
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
	private final FacilityRepository facilityRepository;
	private final MyLibraryRepository myLibraryRepository;

	@Value("${file.upload-dir}")
	private String uploadDir;
	
	/**
	 * 아이템 구매 비지니스 로직 처리
	 */
	@Transactional
	public boolean purchaseItem(PrincipalDTO principal, Integer itemId, Integer waveUsed, Integer mileageUsed, Map<String, String> request) {

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

	private boolean applyItem(Integer itemId, Integer userId, Map<String, String> request) {

		String item = itemRepository.readItem(itemId);
		switch (item) {
		case "extend-category":
			return extendCategory(userId);
		case "ebook":
			return ebook(userId, Integer.parseInt(request.get("bookId")));
		case "computer":
			return computer(userId, request);
		}
		if (item.startsWith("renew")) {
			String day = item.substring(item.length() - 1);
			return renew(userId, day, Integer.parseInt(request.get("bookId")));
		}
		return false;
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
	private boolean computer(Integer userId, Map<String, String> request) {
		int computerId = Integer.parseInt(request.get("computerId"));
		Timestamp startTime = Timestamp.valueOf(request.get("startTime"));
		Timestamp endTime = Timestamp.valueOf(request.get("endTime"));

		// 해당 시간에 예약가능 한지 다시 조회
		if (facilityRepository.countComputerReservationByComputerIdAndTime(userId, computerId, startTime, endTime) > 0) {
			return false;
		}
		try {
			facilityRepository.createComputerReservation(userId, computerId, startTime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	private boolean renew(Integer userId, String day, Integer bookId) {
		try {
			MyLibrary myLibrary = myLibraryRepository.findByUserIdAndBookId(userId, bookId);
			LocalDateTime currentReturnDate = myLibrary.getReturnDate().toLocalDateTime();
			LocalDateTime updatedReturnDate = currentReturnDate.plusDays(Long.parseLong(day));
			if (updatedReturnDate.toLocalDate().isBefore(LocalDate.now())) {
				myLibraryRepository.updateReturnDateAndStatusById(myLibrary.getId(), Timestamp.valueOf(updatedReturnDate), 1);
			} else {
				myLibraryRepository.updateReturnDateAndStatusById(myLibrary.getId(), Timestamp.valueOf(updatedReturnDate), 0);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean purchasePrint(PrincipalDTO principal, Integer itemId, Integer waveUsed, Integer mileageUsed, MultipartFile file, Integer pages) {

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
		return applyPrintRequest(userId, file, pages);
	}

	private boolean applyPrintRequest(Integer userId, MultipartFile file, Integer pages) {
		try {
			String[] fileNames = uploadFile(file);
			facilityRepository.createPrinter(userId, fileNames[0], fileNames[1], pages);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private String[] uploadFile(MultipartFile mFile) {

		if (mFile.getSize() > Define.MAX_FILE_SIZE) {
			throw new DataDeliveryException("파일 크기는 20MB 이상 클 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		
		// 코드 수정
		// File - getAbsolutePath() : 파일 시스템의 절대 경로를 나타냅니다.
		// (리눅스 또는 MacOS)에 맞춰서 절대 경로가 생성을 시킬 수 있다.
		// String saveDirectory = new File(uploadDir).getAbsolutePath();
		String saveDirectory = uploadDir;
		
		File dir = new File(saveDirectory);
		if (!dir.exists()) {
		    dir.mkdirs();  // 디렉토리가 없을 경우 생성
		}
		
		// 파일 이름 생성(중복 이름 예방)
		String uploadFileName = UUID.randomUUID() + "_" + mFile.getOriginalFilename();
		// 파일 전체경로 + 새로생성한 파일명
		String uploadPath = saveDirectory + File.separator + uploadFileName;
		File destination = new File(uploadPath);

		// 반드시 수행
		try {
			mFile.transferTo(destination);
		} catch (IllegalStateException | IOException e) {
			throw new DataDeliveryException("파일 업로드중에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new String[] { mFile.getOriginalFilename(), uploadFileName };
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
