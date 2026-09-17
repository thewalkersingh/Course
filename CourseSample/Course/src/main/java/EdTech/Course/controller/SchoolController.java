package EdTech.Course.controller;

import EdTech.Course.dto.ResponseMessage;
import EdTech.Course.dto.SchoolDto;
import EdTech.Course.model.School;
import EdTech.Course.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {
	private final SchoolService schoolService;
	
	public SchoolController(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	
	@GetMapping("/all")
	public List<School> getAllSchools() {
		return schoolService.getAllSchools();
	}
	
	@GetMapping("/{id}")
	public School getSchoolById(@PathVariable Long id) {
		return schoolService.getSchoolById(id);
	}
	
	@PostMapping
	public ResponseMessage createSchool(@RequestBody SchoolDto schoolDto) {
		return schoolService.createSchool(schoolDto);
	}
	
	@PutMapping("/{id}")
	public ResponseMessage updateSchool(@PathVariable Long id, @RequestBody SchoolDto schoolDto) {
		return schoolService.updateSchool(id, schoolDto);
	}
	
	@DeleteMapping("/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseMessage deleteSchool(@PathVariable Long id) {
		return schoolService.deleteSchool(id);
	}
	
}