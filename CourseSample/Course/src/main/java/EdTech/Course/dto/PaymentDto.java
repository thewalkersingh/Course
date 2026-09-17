package EdTech.Course.dto;

import lombok.Data;

@Data
public class PaymentDto {
	private Long studentId;
	private Long courseId;
	private String date;
	private Double amount;
	private String paymentMethod;
	private String description;
	
}