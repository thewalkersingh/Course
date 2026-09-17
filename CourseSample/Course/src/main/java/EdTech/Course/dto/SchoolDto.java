package EdTech.Course.dto;

import EdTech.Course.model.School;
import lombok.Data;

@Data
public class SchoolDto {
	private String name;
	private String city;
	private String state;
	private String area;
	private String subdomain;
	private School.PlanType planType;
	private School.Status status;
	
}