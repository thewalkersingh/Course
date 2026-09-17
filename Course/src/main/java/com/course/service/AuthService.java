package com.course.service;

import com.course.dto.LoginDto;
import com.course.dto.RegisterDto;
import com.course.model.AppUser;
import com.course.repository.UserRepository;
import com.course.security.CustomUserService;
import com.course.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomUserService customUserService;
	private final JwtUtil jwtUtil;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
		CustomUserService customUserService, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customUserService = customUserService;
		this.jwtUtil = jwtUtil;
	}
	
	public String login(LoginDto loginDto) {
		log.info("Loding login details for: {}", loginDto.getUsername());
		/*Optional<AppUser> userOptional = userRepository.findByUsername(loginDto.getUsername());
		if (userOptional.isPresent()) {
			AppUser user = userOptional.get();
			log.info("Found AppUser: {}", user);
			if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
				return "Login successful!";
			}
		}*/
		UserDetails userDetails = customUserService.loadUserByUsername(loginDto.getUsername());
		if (userDetails.getPassword() != null && passwordEncoder.matches(loginDto.getPassword(),
			userDetails.getPassword())) {
			return jwtUtil.generateToken(loginDto.getUsername());
		}
		throw new IllegalArgumentException("Invalid credentials.");
	}
	
	public String register(RegisterDto registerDto) {
		log.info("Register RegisterDto: {}", registerDto);
		Optional<AppUser> existingUserOptional = userRepository.findByUsername(registerDto.getUsername());
		if (existingUserOptional.isPresent()) {
			throw new IllegalArgumentException("Username already exists.");
		}
		AppUser newUser = new AppUser();
		newUser.setUsername(registerDto.getUsername());
		newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		newUser.setRole(registerDto.getRole());
		newUser.setEnabled(true);
		
		userRepository.save(newUser);
		log.info("New AppUser: {}", newUser);
		return "Registration successful!";
	}
	
	public String deleteUser(String username) {
		log.info("Delete user with username: {}", username);
		Optional<AppUser> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			userRepository.delete(userOptional.get());
			log.info("Deleted AppUser: {}", userOptional.get());
			return "User deleted successfully.";
		}
		throw new IllegalArgumentException("User not found.");
	}
	
}