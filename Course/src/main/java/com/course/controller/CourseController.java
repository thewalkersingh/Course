package com.course.controller;

import com.course.dto.CourseDto;
import com.course.dto.ResponseMessage;
import com.course.model.Course;
import com.course.model.CourseMaterial;
import com.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {
	
	private final CourseService courseService;
	
	public CourseController(CourseService courseService) {this.courseService = courseService;}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Course> getAllCourses() {
		log.info("Getting list of all coursers");
		return courseService.getAllCourses();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Course getCourseById(@PathVariable Long id) {
		return courseService.getCourseById(id);
	}
	
	@GetMapping("/name/")
	@ResponseStatus(HttpStatus.OK)
	public Course getCourseByName(@RequestParam("name") String name) {
		return courseService.getCourseByName(name);
	}
	
	@GetMapping("/courseMaterial/")
	@ResponseStatus(HttpStatus.OK)
	public List<CourseMaterial> getCourseMaterialByCourseId(@RequestParam("id") Long id) {
		return courseService.getCourseMaterialByCourseId(id);
	}
	
	@GetMapping("/instructor/")
	@ResponseStatus(HttpStatus.OK)
	public Course getCourseByInstructor(@RequestParam("instructor") String instructor) {
		return courseService.getCourseByInstructor(instructor);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseMessage createCourse(@RequestBody CourseDto courseDto) {
		courseService.createCourse(courseDto);
		return new ResponseMessage("course Added Successfully");
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseMessage updateCourse(@PathVariable Long id, @RequestBody CourseDto updatedCourseDto) {
		courseService.updateCourse(id, updatedCourseDto);
		return new ResponseMessage("course Updated Successfully");
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseMessage deleteCourse(@PathVariable Long id) {
		courseService.deleteCourse(id);
		return new ResponseMessage("course Deleted Successfully");
	}
	
	@PostMapping("/course/{courseId}/register/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseMessage registerForCourse(@PathVariable Long courseId, @PathVariable Long userId) {
		courseService.createEnrollmentForCourse(courseId, userId);
		return new ResponseMessage("Student Enrolled Successfully");
	}
	
	public ResponseMessage registerForCourseFallback(@PathVariable Long courseId, @PathVariable Long userId) {
		return new ResponseMessage("Services not available");
	}
	
}