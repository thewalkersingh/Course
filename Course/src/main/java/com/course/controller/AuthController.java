package com.course.controller;

import com.course.dto.LoginDto;
import com.course.dto.RegisterDto;
import com.course.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginDto) {
		if (loginDto == null || loginDto.getUsername() == null || loginDto.getPassword() == null) {
			log.warn("Login request missing or invalid body");
			return "Invalid credentials.";
		}
		log.info("Login request received for username: {}", loginDto.getUsername());
		return authService.login(loginDto);
	}
	
	@PostMapping("/register")
	public String register(@RequestBody(required = false) RegisterDto registerDto) {
		if (registerDto == null || registerDto.getUsername() == null || registerDto.getPassword() == null || registerDto.getRole() == null) {
			log.warn("Registration request missing or invalid body");
			return "Invalid user data.";
		}
		log.info("Registration request received for username: {}", registerDto.getUsername());
		return authService.register(registerDto);
	}
	
	@PostMapping("/delete")
	public String deleteUser(@RequestBody(required = false) String username) {
		if (username == null || username.trim().isEmpty()) {
			log.warn("Delete user request missing or invalid body");
			return "Invalid username.";
		}
		return authService.deleteUser(username);
	}
	// TODO: Test the Get with JWT token
}