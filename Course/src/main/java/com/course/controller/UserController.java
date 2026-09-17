package com.course.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class UserController {
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER', 'NORMAL')")
	public String userDashboard() {
		return "Welcome to the AppUser Dashboard!";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminDashboard() {
		return "Welcome to the Admin Dashboard!";
	}
	
	@GetMapping("/public")
	public String publicDashboard() {
		return "Welcome to the Public Dashboard!";
	}
	
}