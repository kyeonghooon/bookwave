package com.library.bookwave.service;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.SignInDTO;
import com.library.bookwave.dto.SignUpDTO;
import com.library.bookwave.handler.exception.DataDeliveryException;
import com.library.bookwave.handler.exception.RedirectException;
import com.library.bookwave.repository.interfaces.MemberRepository;
import com.library.bookwave.repository.interfaces.SubscribeRepository;
import com.library.bookwave.repository.interfaces.UserRepository;
import com.library.bookwave.repository.interfaces.WalletRepository;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.UserDetail;
import com.library.bookwave.repository.model.Wallet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final MemberRepository memberRepository;
	private final WalletRepository walletRepository;
	private final SubscribeRepository subscribeRepository;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입 처리
	 * 
	 * @param signUpDTO
	 */
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
			user = userRepository.findById(loginId);
			if (user == null) {
				throw new DataDeliveryException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			System.out.println("registerUser");
			throw new DataDeliveryException("오류로 사용자 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류가 발생했습니다.", HttpStatus.SERVICE_UNAVAILABLE);
		}

		// user_detail_tb에 사용자 상세 정보 저장
		createUserDetail(user.getId(), signUpDTO);
	}

	// user_tb에 사용자 정보 삽입 메서드
	@Transactional
	private void createUser(SignUpDTO signUpDTO) {
		try {
			String hashPwd = passwordEncoder.encode(signUpDTO.getPassword());
			System.out.println("hashPwd : " + hashPwd);
			signUpDTO.setPassword(hashPwd);
			int result = userRepository.createUser(signUpDTO.toUser());
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

	// user_detail_tb에 사용자 상세 정보 삽입 메서드
	private void createUserDetail(Integer id, SignUpDTO signUpDTO) {
		try {
			UserDetail userDetail = signUpDTO.detailUser();
			System.err.println(userDetail);
			int result = userRepository.createUserDetail(id, userDetail);
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

	/**
	 * 로그인 처리
	 * 
	 * @param dto
	 * @return
	 */
	public PrincipalDTO readUser(SignInDTO dto) {

		User userEntity = null;
		PrincipalDTO principalDTO = null;
		try {

			userEntity = userRepository.findById(dto.getLoginId());

			if (userEntity == null) {
				throw new DataDeliveryException("아이디 잘못되었습니다.", HttpStatus.BAD_REQUEST);
			}

			// 암호화 때문에
			boolean passwordMatches = BCrypt.checkpw(dto.getPassword(), userEntity.getPassword());

			if (!passwordMatches) {
				throw new DataDeliveryException("비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
			}

		} catch (DataDeliveryException e) {
			e.printStackTrace();
			throw new DataDeliveryException("잘못된 처리 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
		}

		// principal 세팅
		principalDTO = new PrincipalDTO(userEntity);
		Wallet wallet = walletRepository.findWalletByUserId(principalDTO.getUserId());
		principalDTO.setWave(wallet.getWave());
		principalDTO.setMileage(wallet.getMileage());
		try {
			principalDTO.setSubscribe(subscribeRepository.findSubscribeByUserId(principalDTO.getUserId()) != null);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return principalDTO;
	}

	// ID 중복확인
	public User readUserId(String loginId) {

		return memberRepository.readUserId(loginId);
	}

	/**
	 * 아이디, 비번 찾기
	 */
	// ID 찾기
	public String eFindByEmail(String email) {
		return memberRepository.eFindByEmail(email);
	}

	// 이메일로 ID 전송하는 로직

	// 비번 찾기

	/**
	 * (소셜) socialID 확인
	 * 
	 * @param dto
	 */

	public User searchLoginId(String socialId) {
		return userRepository.findBySocialId(socialId);
	}

}// end of class
