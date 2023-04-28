package com.security.controller.service;

import java.util.List;

import com.security.dto.UserDto;
import com.security.exception.UserNotFoundException;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto,int id) throws UserNotFoundException;
	void deleteUser(int id) throws UserNotFoundException;
	List<UserDto> getAllUser();
	UserDto getUserById(int id) throws UserNotFoundException;
}
