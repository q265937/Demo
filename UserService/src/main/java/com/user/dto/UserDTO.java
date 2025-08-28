package com.user.dto;

import com.user.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
