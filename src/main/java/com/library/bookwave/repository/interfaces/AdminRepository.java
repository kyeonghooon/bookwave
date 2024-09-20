package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.dto.BookListDTO;
import com.library.bookwave.dto.PointDTO;
import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.dto.UserDetailDTO;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.User;

@Mapper
public interface AdminRepository {
	// 관리자 유저 추가
	public int insert(User user);

	// 유저 수
	public int countUser();

	// 구독자 수 조회
	public int countSubscribe();

	// 관리자 유저 목록 조회
	public List<PrincipalDTO> readAllUser();

	// 관리자 대출현황 조회
	public List<Lend> readAllLend();

	// 관리자 대출현황 업데이트
	public int updateLendStatus();

	// 관리자 도서 목록 조회
	public List<BookListDTO> readAllBook();

	// 관리자 유저 상세보기
	public UserDetailDTO readUserById(int userId);

	// wallet 업데이트
	public int updateWallet(PointDTO point);

	// BalanceHistory 추가
	public int createBalanceHisotry(PointDTO point);

}
