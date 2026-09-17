package com.course.dataseed;

import com.course.model.Course;
import com.course.model.CourseMaterial;
import com.course.repository.CourseRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

//@Component
public class CourseData {
	private final Faker faker = new Faker(new Locale("en-GB"));
	private final Random random = new Random();
	
	//	@Bean
	CommandLineRunner seedCourse(CourseRepository courseRepository) {
		
		String[] fileTypes = {"PDF", "VIDEO", "AUDIO", "PHOTO", "DOCUMENT", "PRESENTATION"};
		
		return args -> {
			// Seed 5 courses
			for (int i = 0; i < 5; i++) {
				Course course = new Course();
				course.setName(faker.educator().course());
				course.setDescription(faker.lorem().sentence(10));
				course.setInstructor(faker.name().fullName());
				course.setAmount((long) faker.number().numberBetween(1000, 5000));
				
				// Add course materials
				List<CourseMaterial> materials = new ArrayList<>();
				for (int j = 0; j < 15; j++) {
					CourseMaterial material = new CourseMaterial();
					material.setFileType(fileTypes[random.nextInt(fileTypes.length)]);
					material.setDescription(faker.lorem().sentence(5));
					material.setCourse(course);
					materials.add(material);
				}
				course.setCourseMaterial(materials);
				
				// Save course (cascade will save materials and enrollments)
				courseRepository.save(course);
			}
		};
	}
	
}