package com.security.controller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.controller.service.UserService;
import com.security.dto.UserDto;
import com.security.entity.User;
import com.security.exception.UserNotFoundException;
import com.security.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int id) throws UserNotFoundException {
		User user = userRepo.findById(id);
		if (user == null) {
		    throw new UserNotFoundException("User not found with id : " + id);
		}
		else{
			user.setUsername(userDto.getUsername());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());	
		}
		return userToDto(userRepo.save(user));
	}

	@Override
	public void deleteUser(int id) throws UserNotFoundException{
		User user = userRepo.findById(id);
		if (user == null) {
			 throw new UserNotFoundException("User not found with id :" + id);
		}
		else {
			userRepo.delete(user);
		}
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> userList = userRepo.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for(User user : userList) {
			UserDto userDto = userToDto(user);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	@Override
	public UserDto getUserById(int id) throws UserNotFoundException {
		User user = userRepo.findById(id);
		if (user == null) {
		    throw new UserNotFoundException("User not found with id :" + id);
		}
		return userToDto(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setRole("ROLE_USER");
		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setRole(user.getRole());
		return userDto;
	}
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
	
//	private String encodePassword(String password) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.encode(password);
//    }
}
