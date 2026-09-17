package com.course.repository;

import com.course.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	List<Enrollment> findByCourseId(Long courseId);
	
	List<Enrollment> findAllByYear(Year year);
	
}