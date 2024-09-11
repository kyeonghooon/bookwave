package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.Item;

@Mapper
public interface ItemRepository {

	// 페이지 이름으로 해당 페이지에 들어갈 item 찾기
	List<Item> findItemListByPageName(String pageName);
	
	// 아이템 id로 name 받아오기
	String readItem(Integer itemId);
}
