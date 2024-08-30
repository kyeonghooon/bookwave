package com.library.bookwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.SignUpDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.handler.exception.RedirectException;
import com.library.bookwave.repository.interfaces.MemberRepository;
import com.library.bookwave.repository.interfaces.SignUpRepository;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private final SignUpRepository signUpRepository;

	@Autowired
	private final MemberRepository memberRepository;



// 회원 가입 처리	
// 통합 메서드
	@Transactional
	public void registerUser(SignUpDTO signUpDTO) {
		// user_tb에 사용자 정보 저장
		createUser(signUpDTO);
		// 저장된 사용자 정보 조회
		User user = null;
		try {
			// dto에서 loginId 가져오기
			String loginId = signUpDTO.getLoginId();
			// loginId로 User 객체 조회
			user = signUpRepository.findById(loginId);
			if (user == null) {
				throw new DataDeliveryException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			System.out.println("registerUser");
			throw new DataDeliveryException("오류로 사용자 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류가 발생했습니다.", HttpStatus.SERVICE_UNAVAILABLE);
		}

		//  user_detail_tb에 사용자 상세 정보 저장
		createUserDetail(user.getId(), signUpDTO);
	}

	// TODO ID 중복확인
	public User readUserId(String loginId) {

		return memberRepository.readUserId(loginId);
	}

	/**
	 * user_tb에 사용자 정보 삽입 메서드
	 *
	 * @param signUpDTO 사용자 정보가 담긴 DTO
	 */
	private void createUser(SignUpDTO signUpDTO) {
		try {
			int result = signUpRepository.createUser(signUpDTO.toUser());
			if (result != 1) {
				throw new DataDeliveryException("회원가입에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			System.out.println("createUser");
			e.printStackTrace();
			throw new DataDeliveryException("오류로 회원가입에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RedirectException("알 수 없는 오류가 발생했습니다.", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * user_detail_tb에 사용자 상세 정보 삽입 메서드
	 *
	 * @param userId    사용자 ID
	 * @param signUpDTO 사용자 상세 정보가 담긴 DTO
	 */
	private void createUserDetail(Integer id, SignUpDTO signUpDTO) {
		try {
			UserDetail userDetail = signUpDTO.detailUser();
			System.err.println(userDetail);
			int result = signUpRepository.createUserDetail(id, userDetail);
			if (result != 1) {
				throw new DataDeliveryException("사용자 상세 정보 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DataDeliveryException("오류로 사용자 상세 정보 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류가 발생했습니다.", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

}// end of main
