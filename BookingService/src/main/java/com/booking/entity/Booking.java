package com.booking.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	private String eventName;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private LocalDateTime eventDateTime;
	private String username;
	private String location;
	private int noOfSeats;
	private String ageRating;
	private int minAge;
	private double price;
	
}
