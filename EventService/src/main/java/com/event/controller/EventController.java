package com.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.entity.Event;
import com.event.service.EventService;


@RestController
@RequestMapping("/event")
public class EventController {
	private EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<Event> addEvent(@RequestBody Event event){
		Event eve = eventService.newEvent(event);
		return ResponseEntity.ok(eve);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Event>> allEvents(){
		List<Event> eve = eventService.allEvents();
		return ResponseEntity.ok(eve);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Event> findById(@PathVariable int id){
		Event eve = eventService.findbyId(id);
		return ResponseEntity.ok(eve);
	}
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Event> removeEvents(@PathVariable int id){
		Event eve = eventService.removeEvent(id);
		return ResponseEntity.ok(eve);
	}
	
	@PutMapping("/bookSeat/{id}/{quantity}")
	public ResponseEntity<Event> bookSeat(@PathVariable int id, @PathVariable int quantity){
		Event eve = eventService.updateSeats(id, quantity);
		return ResponseEntity.ok(eve);
	}
}
