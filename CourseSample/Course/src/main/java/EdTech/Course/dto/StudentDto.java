package EdTech.Course.dto;

import EdTech.Course.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private Long classroom;
	private Character section;
	private Gender gender;
	private Long schoolId;
	private List<Long> courseIds;
}