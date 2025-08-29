package com.booking.interservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.dto.UserDTO;

@FeignClient(name = "UserService")
public interface UserClient {
	
	@GetMapping("/user/find/{username}")
	UserDTO findUser(@PathVariable String username);	
}
