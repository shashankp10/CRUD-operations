package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.controller.service.UserService;
import com.security.dto.UserDto;
import com.security.exception.UserNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome!! Shashank";
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
	   
	@GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) throws UserNotFoundException {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") int id) throws UserNotFoundException {
        UserDto updatedUserDto = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUserDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUser();
        return ResponseEntity.ok(userDtos);
    }
}
