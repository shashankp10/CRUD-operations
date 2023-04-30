package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.controller.service.UserService;
import com.security.dto.UserDto;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome To Public Profile";
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDto userDto) {    	
	    UserDto createUserDto = this.userService.createUser(userDto);
	    UserDto responseDto = new UserDto();
	    responseDto.setUsername(createUserDto.getUsername());
	    responseDto.setEmail(createUserDto.getEmail());
	    responseDto.setPassword(createUserDto.getPassword());
	    responseDto.setRole(createUserDto.getRole());
	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(responseDto);
	}
}
