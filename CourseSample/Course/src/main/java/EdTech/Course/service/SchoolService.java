package EdTech.Course.service;

import EdTech.Course.dto.ResponseMessage;
import EdTech.Course.dto.SchoolDto;
import EdTech.Course.model.School;
import EdTech.Course.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
	private final SchoolRepository schoolRepository;
	
	public SchoolService(SchoolRepository schoolRepository) {
		this.schoolRepository = schoolRepository;
	}
	
	public List<School> getAllSchools() {
		return schoolRepository.findAll();
	}
	
	public School getSchoolById(Long id) {
		return schoolRepository.findById(id).orElse(null);
	}
	
	public ResponseMessage createSchool(SchoolDto schoolDto) {
		School school = new School();
		school.setName(schoolDto.getName());
		school.setSubdomain(schoolDto.getSubdomain());
		school.setCity(schoolDto.getCity());
		school.setState(schoolDto.getState());
		school.setArea(schoolDto.getArea());
		school.setPlanType(schoolDto.getPlanType());
		school.setStatus(schoolDto.getStatus());
		schoolRepository.save(school);
		return new ResponseMessage("School created successfully with name: " + schoolDto.getName());
	}
	
	public ResponseMessage updateSchool(Long id, SchoolDto schoolDto) {
		School school = schoolRepository.findById(id).orElse(null);
		if (school != null) {
			school.setName(schoolDto.getName());
			school.setSubdomain(schoolDto.getSubdomain());
			school.setCity(schoolDto.getCity());
			school.setState(schoolDto.getState());
			school.setArea(schoolDto.getArea());
			schoolRepository.save(school);
			return new ResponseMessage("School updated successfully with name: " + schoolDto.getName());
		}
		return new ResponseMessage("School not found with id: " + id);
	}
	
	public ResponseMessage deleteSchool(Long id) {
		School school = schoolRepository.findById(id).orElse(null);
		if (school != null) {
			schoolRepository.delete(school);
			return new ResponseMessage("School deleted successfully with id: " + id);
		}
		return new ResponseMessage("School not found with id: " + id);
	}
	
}