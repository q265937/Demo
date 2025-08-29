package com.booking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dto.EventDTO;
import com.booking.dto.UserDTO;
import com.booking.entity.Booking;
import com.booking.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	private BookingService bookServ;

	public BookingController(BookingService bookServ) {
		this.bookServ = bookServ;
	}
	
	@PostMapping("/new/{username}")
	public ResponseEntity<Booking> newBooking(@RequestBody Booking booking, @PathVariable String username){
		Booking b = bookServ.newBooking(booking, username);
		return ResponseEntity.ok(b);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Booking>> allBookings(){
		List<Booking> all = bookServ.allBookings();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/findByEvent/{eventName}")
	public ResponseEntity<Booking> findByEventName(@PathVariable String eventName) {
		Booking b = bookServ.findBookingByEventName(eventName);
		return ResponseEntity.ok(b);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Booking> findBookingById(@PathVariable int bookingId){
		Booking b = bookServ.findBooking(bookingId);
		return ResponseEntity.ok(b);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<UserDTO> userData(@PathVariable String username){
		return ResponseEntity.ok(bookServ.findByUsername(username));
	}
	
	
	@GetMapping("/event/findById/{id}")
	public ResponseEntity<EventDTO> eventData(@PathVariable int id){
		return ResponseEntity.ok(bookServ.findById(id));
	}
	
	
	@PutMapping("/event/bookSeat/{id}/{quantity}")
	public ResponseEntity<EventDTO> setWuantity(@PathVariable int id,@PathVariable int quantity){
		return ResponseEntity.ok(bookServ.setQuantity(id, quantity));
	}
	//TODO: controllers for service methods
	
}
