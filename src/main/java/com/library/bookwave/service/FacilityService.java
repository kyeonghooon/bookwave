package com.library.bookwave.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.handler.exception.RedirectException;
import com.library.bookwave.repository.interfaces.FacilityRepository;
import com.library.bookwave.repository.model.Computer;
import com.library.bookwave.repository.model.ComputerReservation;
import com.library.bookwave.utils.Define;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityService {

	private final FacilityRepository facilityRepository;
	
	public List<Computer> readAllComputers() {
		List<Computer> computerList = null;
		try {
			computerList = facilityRepository.readAllComputers();
		} catch (DataAccessException e) {
			throw new DataDeliveryException(Define.FAILED_PROCESSING, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException(Define.UNKNOWN, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return computerList;
	}
	
	public List<Integer> getAvailableTimeSlots(String date, int computerId) {
	    List<Integer> availableSlots = new ArrayList<>();

	    // 모든 시간 슬롯 생성 (30분 단위)
	    List<LocalTime> timeSlots = createTimeSlots();

	    // 해당 날짜와 computerId로 예약 정보를 조회
	    List<ComputerReservation> reservations = facilityRepository.findComputerReservationByDateAndComputerId(date, computerId);

	    // 각 시간 슬롯을 예약 정보와 비교하여 사용 가능 여부 확인
	    for (int i = 0; i < timeSlots.size(); i++) {
	        LocalTime slotStart = timeSlots.get(i);
	        LocalTime slotEnd = slotStart.plusMinutes(30);

	        boolean isAvailable = reservations.stream()
	            .noneMatch(reservation -> {
	                LocalTime reservedStart = toLocalTime(reservation.getStartTime());
	                LocalTime reservedEnd = toLocalTime(reservation.getEndTime());
	                return (reservedStart.isBefore(slotEnd) && reservedEnd.isAfter(slotStart)); 
	            });

	        if (isAvailable) {
	            availableSlots.add(i);  // 사용 가능한 슬롯 인덱스 추가
	        }
	    }

	    return availableSlots;
	}

	// Timestamp를 LocalTime으로 변환하는 메서드
	private LocalTime toLocalTime(Timestamp timestamp) {
	    LocalDateTime localDateTime = timestamp.toLocalDateTime();  // Timestamp -> LocalDateTime 변환
	    return localDateTime.toLocalTime();  // LocalDateTime -> LocalTime 변환
	}
	
	// 9:00부터 30분 단위로 18개의 시간 슬롯을 생성
	private List<LocalTime> createTimeSlots() {
	    List<LocalTime> timeSlots = new ArrayList<>();
	    LocalTime startTime = LocalTime.of(9, 0);  // 9:00 시작
	    for (int i = 0; i < 18; i++) {
	        timeSlots.add(startTime.plusMinutes(30 * i));  // 30분 단위로 슬롯 생성
	    }
	    return timeSlots;
	}

}
