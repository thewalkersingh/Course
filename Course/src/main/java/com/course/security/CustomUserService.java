package com.course.security;
import com.course.model.AppUser;
import com.course.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserService implements UserDetailsService {
	private final UserRepository userRepository;
	
	public CustomUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
		// Step 1: Fetch user from database
		AppUser appUser = userRepository.findByUsername(username)
												  .orElseThrow(() -> new UsernameNotFoundException(
													  "User not found with username: " + username
												  ));
		
		// Step 2: Convert AppUser to Spring Security's UserDetails
		// Using the builder pattern
		return User.builder()
					  .username(appUser.getUsername())
					  .password(appUser.getPassword())  // Already hashed from DB
					  .authorities(Collections.singletonList(
						  new SimpleGrantedAuthority("ROLE_" + appUser.getRole())
					  ))
					  .disabled(!appUser.isEnabled())  // Respect the enabled flag
					  .build();
	}
	
}