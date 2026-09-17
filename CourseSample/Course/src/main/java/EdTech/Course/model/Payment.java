package EdTech.Course.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private Long studentId;
	private Long courseId;
	private String date;
	private Double amount;
	private PaymentStatus status;
	private String transactionId;
	private String paymentMethod;
	private String description;
	
	public enum PaymentStatus {
		PENDING,
		APPROVED,
		DECLINED,
		
	}
	
}