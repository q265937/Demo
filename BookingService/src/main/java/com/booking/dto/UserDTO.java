package com.booking.dto;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private int id;	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private long phoneNo;
	private String email;
	private LocalDate dob;
	private String gender;
	
	@Enumerated(EnumType.STRING)
	private RoleDTO role;
	

}
