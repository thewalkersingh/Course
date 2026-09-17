package com.course.service;

import com.course.dto.CourseDto;
import com.course.dto.PaymentDto;
import com.course.model.Course;
import com.course.model.CourseMaterial;
import com.course.model.Enrollment;
import com.course.model.Payment;
import com.course.repository.CourseRepository;
import com.course.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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
		for (Enrollment enrollment : courseDto.getEnrollments()) {
			enrollment.setCourse(course);
		}
		course.setCourseMaterial(courseDto.getCourseMaterial());
		course.setEnrollment(courseDto.getEnrollments());
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
			for (Enrollment enrollment : updatedCourseDto.getEnrollments()) {
				enrollment.setCourse(existingCourse);
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
	
	public void createEnrollmentForCourse(Long courseId, Long userId) {
		
		// call to user to find user is available
		String userServiceUrl = "http://user-service/user";
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization",
			"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuIiwiaWF0IjoxNjkyODU1NzUyLCJleHAiOjE2OTI5NDIxNTJ9" + ".Tc" +
				"-tx_sJoqXupsnf0HiLWgNTmKRjM7P6iEYaWu5tr5JTvauh0-wpBOG-7XVy3bvsb4O_--WXSoaZ5PXWUSJIdA");
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(null, header);
		ResponseEntity<Object> response =
			restTemplate.exchange(userServiceUrl + "/" + userId, HttpMethod.GET, requestEntity, Object.class);
		if (response.getBody() == null) throw new RuntimeException("AppUser not found");
		
		Enrollment enrollment = new Enrollment();
		enrollment.setUserId(userId);
		enrollment.setCourse(courseRepository.findById(courseId).orElseThrow());
		enrollmentRepository.save(enrollment);
		
		// creating paymentDto
		String paymentServiceUrl = "http://payment-service/payment";
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setCourseId(courseId);
		paymentDto.setUserId(userId);
		paymentDto.setAmount(enrollment.getCourse().getAmount().doubleValue());
		paymentDto.setDate(LocalDate.now());
		paymentDto.setPaymentMethod(String.valueOf(Payment.PaymentMethod.UPI));
		restTemplate.postForObject(paymentServiceUrl, paymentDto, PaymentDto.class);
	}
	
}