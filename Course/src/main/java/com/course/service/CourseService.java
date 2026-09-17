package com.course.service;

import com.course.dto.CourseDto;
import com.course.model.Course;
import com.course.model.CourseMaterial;
import com.course.repository.CourseRepository;
import com.course.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseService {
	private final CourseRepository courseRepository;
	
	private final EnrollmentRepository enrollmentRepository;
	
	private final RestTemplate restTemplate;
	
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	public Course getCourseById(Long id) {
		Optional<Course> courseOptional = courseRepository.findById(id);
		return courseOptional.orElse(null);
	}
	
	public void createCourse(CourseDto courseDto) {
		Course course = new Course();
		course.setAmount(courseDto.getAmount());
		course.setName(courseDto.getName());
		course.setDescription(courseDto.getDescription());
		course.setInstructor(courseDto.getInstructor());
		for (CourseMaterial courseMaterial : courseDto.getCourseMaterial()) {
			courseMaterial.setCourse(course);
		}
		course.setCourseMaterial(courseDto.getCourseMaterial());
		courseRepository.save(course);
	}
	
	public void updateCourse(Long id, CourseDto updatedCourseDto) {
		Course existingCourse = getCourseById(id);
		if (existingCourse != null) {
			existingCourse.setName(updatedCourseDto.getName());
			existingCourse.setDescription(updatedCourseDto.getDescription());
			existingCourse.setInstructor(updatedCourseDto.getInstructor());
			existingCourse.setAmount(updatedCourseDto.getAmount());
			for (CourseMaterial courseMaterial : updatedCourseDto.getCourseMaterial()) {
				courseMaterial.setCourse(existingCourse);
			}
			courseRepository.save(existingCourse);
		} else {
			throw new RuntimeException("course do not exist");
		}
	}
	
	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}
	
	public Course getCourseByName(String name) {
		return courseRepository.findByName(name);
	}
	
	public Course getCourseByInstructor(String instructor) {
		return courseRepository.findByInstructor(instructor);
	}
	
	public List<CourseMaterial> getCourseMaterialByCourseId(Long id) {
		return courseRepository.findById(id).orElseThrow().getCourseMaterial();
	}
	
}