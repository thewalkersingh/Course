package EdTech.Course.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private Long classroom;
	private Character section;
	private Gender gender;
	
	@ManyToOne
	@JoinColumn(name = "school_id")
	private School school;
	
	@ManyToMany
	@JoinTable(name = "student_course",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;
	
}