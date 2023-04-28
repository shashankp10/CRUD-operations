package com.security.dto;

import lombok.Data;

@Data
public class UserDto {

	private int id;
	private String username;
	private String email;
	private String password;
	private String role;
}
