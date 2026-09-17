package EdTech.Course.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
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
	
	
}