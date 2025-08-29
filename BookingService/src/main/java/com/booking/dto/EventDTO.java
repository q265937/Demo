package com.booking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
	private int eventId;
	private String eventName;
	private String location;
	private int minAge;
	private LocalDateTime eventDate;
	private int noOfSeats;
	private double price;
}
