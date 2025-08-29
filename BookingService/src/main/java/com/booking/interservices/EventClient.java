package com.booking.interservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.booking.dto.EventDTO;

@FeignClient(name="EventService")
public interface EventClient {
	
	@GetMapping("/event/findById/{id}")
	EventDTO eventData(@PathVariable int id);
	
	@PostMapping("/event/bookSeat/{id}/{quantity}")
	EventDTO updateSeats(@PathVariable int id, @PathVariable int quantity);
	
}
