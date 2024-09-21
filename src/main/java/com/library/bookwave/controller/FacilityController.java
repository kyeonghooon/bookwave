package com.library.bookwave.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.bookwave.repository.model.Computer;
import com.library.bookwave.service.FacilityService;
import com.library.bookwave.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("facility")
@RequiredArgsConstructor
public class FacilityController {
	
	private final FacilityService facilityService;
	private final ItemService itemService;
	
	@GetMapping(value={"/", "/computer"})
	public String computerPage(Model model) {
		List<Computer> computerList = facilityService.readAllComputers();
		model.addAttribute("computerList", computerList);
		return "/facility/computer";
	}
	
	@GetMapping("computerReservation/{computerId}")
	public String computerReservationPage(@PathVariable(name = "computerId") Integer computerId,//
			Model model) {
		String itemsJson = itemService.findItemsByPageName("computerReservation");
		model.addAttribute("items", itemsJson);
		model.addAttribute("computerId", computerId);
		return "facility/computerReservation";
	}
	
	@GetMapping("computer-timetable")
	public ResponseEntity<?> getTimeTable(@RequestParam(name = "date") String date,//
			@RequestParam(name = "computerId") Integer computerId) {
		Map<String, Object> response = new HashMap<>();
		List<Integer> availableSlots = facilityService.getAvailableTimeSlots(date, computerId);
		response.put("availableSlots", availableSlots);
		return ResponseEntity.ok(response);
	}
	
}
