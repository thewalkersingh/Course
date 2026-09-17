package com.course.repository;
import com.course.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
	// Spring Data JPA automatically implements this method
	// It generates: SELECT * FROM users WHERE username = ?
	Optional<AppUser> findByUsername(String username);
	
	// Optional: Check if username exists
	boolean existsByUsername(String username);
	
	// Optional: Find by email if you add email field
	// Optional<AppUser> findByEmail(String email);
	
}