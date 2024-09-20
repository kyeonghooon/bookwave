package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.BalanceHistoryDTO;
import com.library.bookwave.dto.PaymentHistroyDTO;
import com.library.bookwave.dto.UserInfoDTO;
import com.library.bookwave.repository.model.User;
import com.library.bookwave.repository.model.Wallet;

@Mapper
public interface MyPageRepository {

	public User findUserById(Integer id);

	public UserInfoDTO findUserDetailById(Integer userId);

	public void updateAddress(@Param("zip") String zip, @Param("addr1") String addr1, @Param("addr2") String addr2,
			@Param("userId") Integer userId);

	public void updatePhone(@Param("phone") String phone, @Param("userId") Integer userId);

	public int updateUserByPassword(@Param("password") String password, @Param("id") Integer id);

	public Integer countBalanceHistory(@Param("userId") Integer userId, @Param("historyType") String historyType);

	public List<BalanceHistoryDTO> findBalanceHistory(@Param("userId") Integer userId, @Param("limit") Integer limit,
			@Param("offset") Integer offset, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder,
			@Param("historyType") String historyType);

	public List<PaymentHistroyDTO> findPaymentByUserId(Integer userId);

	public PaymentHistroyDTO findPaymentById(Integer id);

	public void updatePayment(@Param("id") Integer id, @Param("cancelReason") String cancelReason);

	public void updateUserStatus(Integer Id);
	
	public Wallet readAllWalletByUserId (Integer userId);
	
	public void updateWallet(@Param("totalAmount") Integer totalAmount,@Param("userId") Integer userId);
	
	public int updateUserEmailByUserId (@Param("email") String email,@Param("userId") Integer userId);

}
