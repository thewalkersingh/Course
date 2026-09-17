package com.course.dataseed;

import com.course.model.Enrollment;
import com.course.repository.EnrollmentRepository;
import com.github.javafaker.Faker;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

//@Component
public class EnrollmentData {
	@Getter
	private final Faker faker = new Faker(new Locale("en-GB"));
	private final Random random = new Random();
	
	//	@Bean
	CommandLineRunner seedEnrollment(EnrollmentRepository enrollmentRepository) {
		return args -> {
			// Add enrollments
			List<Enrollment> enrollments = new ArrayList<>();
			for (int k = 0; k < 20; k++) {
				Enrollment enrollment = new Enrollment();
				enrollment.setUserId(random.nextLong(0, 20));
				enrollment.setCourseId(random.nextLong(0, 20));
				enrollment.setYear(Year.now().minusYears(random.nextInt(0, 5)));
				enrollments.add(enrollment);
			}
			enrollmentRepository.saveAll(enrollments);
		};
	}
	
}