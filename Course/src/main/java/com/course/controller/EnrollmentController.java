package com.course.controller;

import com.course.dto.ResponseMessage;
import com.course.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
	private final EnrollmentService enrollmentService;
	
	@PostMapping("/course/{courseId}/register/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseMessage registerForCourse(@PathVariable Long courseId, @PathVariable Long userId) {
		enrollmentService.createEnrollmentForCourse(courseId, userId);
		return new ResponseMessage("Student Enrolled Successfully");
	}
	
}