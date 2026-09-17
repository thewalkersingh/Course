package com.course.service;
import com.course.model.Enrollment;
import com.course.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;

@RequiredArgsConstructor
@Service
public class EnrollmentService {
	private final EnrollmentRepository enrollmentRepository;
	
	public void createEnrollmentForCourse(Long courseId, Long userId) {
		Enrollment enrollment = new Enrollment();
		enrollment.setCourseId(courseId);
		enrollment.setUserId(userId);
		enrollment.setYear(Year.now());
		enrollmentRepository.save(enrollment);
	}
	
}