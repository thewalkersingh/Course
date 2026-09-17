package EdTech.Course.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "schools")
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String subdomain;
	public PlanType planType;
	private String city;
	private String state;
	private String area;
	public Status status;
	
	@OneToMany(mappedBy = "school")
	@JsonBackReference
	private List<Student> students;
	
	@OneToMany(mappedBy = "school")
	@JsonBackReference
	private List<Teacher> teachers;
	
	public enum PlanType {
		FREE,
		PREMIUM,
		STANDARD
	}
	
	public enum Status {
		ACTIVE,
		INACTIVE
	}
	
}