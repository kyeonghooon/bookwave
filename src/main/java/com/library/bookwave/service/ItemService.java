package com.library.bookwave.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookwave.handler.exception.RedirectException;
import com.library.bookwave.repository.interfaces.ItemRepository;
import com.library.bookwave.repository.model.Item;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	/**
	 *  페이지에서 사용되는 item을 세팅
	 */
	public String findItemsByPageName(String pagename) {
		List<Item> itemList = null;
		try {
			itemList = itemRepository.findItemListByPageName(pagename);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		// 아이템 리스트를 Map구조로 변환
		Map<String, Integer> items = new HashMap<>();
		for (Item item : itemList) {
			items.put(item.getName(), item.getId());
		}
		if (items.isEmpty()) {
			new RedirectException("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String itemsJson = null;
		try {
			itemsJson = objectMapper.writeValueAsString(items);
		} catch (JsonProcessingException e) {
			new RedirectException("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return itemsJson;
	}
	
}
