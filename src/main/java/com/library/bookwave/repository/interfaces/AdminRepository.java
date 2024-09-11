package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.repository.model.Lend;
import com.library.bookwave.repository.model.User;

@Mapper
public interface AdminRepository {
	public int insert(User user);

	public List<PrincipalDTO> readAllUser();

	public List<Lend> readAllLend();

	public int updateLendStatus();
}
