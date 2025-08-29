package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dto.EventDTO;
import com.booking.dto.UserDTO;
import com.booking.entity.Booking;
import com.booking.interservices.EventClient;
import com.booking.interservices.UserClient;
import com.booking.repository.BookingRepository;

@Service
public class BookingService {
	
	private BookingRepository bookRepo;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private EventClient eventClient;
	
	public BookingService(BookingRepository bookRepo) {
		super();
		this.bookRepo = bookRepo;
	}

	public Booking newBooking(Booking booking, String username) {
		UserDTO userDTO = userClient.findUser(username);
		if(userDTO != null) {
			booking.setUsername(username);
			booking.setPrice(booking.getPrice() * booking.getNoOfSeats());
			bookRepo.save(booking);
			return booking;
		}else {
			throw new RuntimeException("Booking data insufficient");
		}
	}
	
	public List<Booking> allBookings(){
		List<Booking> all = bookRepo.findAll();
		return all;
	}
	
	public Booking findBooking(int bookingId) {
		Booking booking = bookRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Did not find booking for ID " + bookingId));
		return booking;
	}
	
	public UserDTO findByUsername(String username) {
		return userClient.findUser(username);
	}
	
	public EventDTO findById(int id) {
		return eventClient.eventData(id);
	}
	
	//TODO: implement this
	public double getTotal(Booking booking) {
		return 0;
	}
	
	public Booking findBookingByEventName(String eventName) {
		Booking booking = bookRepo.findByEventName(eventName).orElseThrow(() -> new RuntimeException("Did not find booking for event name! " + eventName));
		return booking;
	}
	
	public EventDTO setQuantity(int id, int quantity) {
		EventDTO eventDto = eventClient.eventData(id);
		eventDto.setNoOfSeats(eventDto.getNoOfSeats()-quantity);
		
		return eventDto;
	}
	
	
	
	
	//TODO: filter by eventDateTime, Location, ageRating, > price || < price
	
}
