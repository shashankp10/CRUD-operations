
package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
		User findById(int id);
}
